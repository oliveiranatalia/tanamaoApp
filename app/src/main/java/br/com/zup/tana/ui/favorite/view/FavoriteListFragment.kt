package br.com.zup.tana.ui.favorite.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.zup.tana.R
import br.com.zup.tana.data.model.Dish
import br.com.zup.tana.databinding.FragmentFavoriteListBinding
import br.com.zup.tana.domain.viewstate.Status
import br.com.zup.tana.ui.favorite.view.adapter.FavoritedListAdapter
import br.com.zup.tana.ui.favorite.viewmodel.FavoriteListViewModel
import br.com.zup.tana.ui.home.view.MainActivity

class FavoriteListFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteListBinding
    private lateinit var viewModel: FavoriteListViewModel
    private lateinit var factory: FavoriteListViewModel.FavoriteListViewModelFactory
    private val adapter: FavoritedListAdapter by lazy { FavoritedListAdapter(arrayListOf(), this::goToDetail) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false)
        factory = FavoriteListViewModel.FavoriteListViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(FavoriteListViewModel::class.java)

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavoritedList()

        viewModel.favState.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.EMPTY -> {
                    binding.ivEmptyListFav.setImageResource(R.drawable.empty_list)
                    binding.ivEmptyListFav.isVisible = true
                    binding.tvEmptyListFav.isVisible = true
                    binding.pbLoading.isVisible = false
                }
                Status.SUCCESS -> {
                    binding.rvFavorite.adapter = adapter
                    binding.rvFavorite.layoutManager = LinearLayoutManager(context)
                    adapter.updateList(it.data as MutableList<Dish>)
                    binding.rvFavorite.isVisible = true
                    binding.pbLoading.isVisible = false
                }
                Status.LOADING -> {
                    binding.pbLoading.isVisible = true
                    binding.rvFavorite.isVisible = false
                }
                Status.ERROR -> {
                    Toast.makeText(context, "${it.message}", Toast.LENGTH_LONG).show()
                    binding.pbLoading.isVisible = false
                }
            }
        })
    }

    fun goToDetail(dish: Dish){
        val bundle = bundleOf("DISH_KEY" to dish)
        NavHostFragment.findNavController(this).navigate(R.id.action_favoriteFragment_to_detailFragment,bundle)
    }
}