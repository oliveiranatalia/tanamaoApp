package br.com.zup.tana.ui.menu.view

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
import br.com.zup.projetofinalzup.ui.menu.view.adapter.MenuAdapter
import br.com.zup.tana.R
import br.com.zup.tana.data.model.Dish
import br.com.zup.tana.databinding.FragmentMenuBinding
import br.com.zup.tana.domain.viewstate.Status
import br.com.zup.tana.ui.menu.viewmodel.MenuViewModel

class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    private lateinit var viewModel: MenuViewModel
    private lateinit var factory:MenuViewModel.MenuViewModelFactory
    private val adapter: MenuAdapter by lazy { MenuAdapter(arrayListOf(), this::goToDetail) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        factory = MenuViewModel.MenuViewModelFactory()
        viewModel = ViewModelProvider(this,factory).get(MenuViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMenu()

        viewModel.menu.observe(viewLifecycleOwner, Observer{
            when(it.status){
                Status.SUCCESS -> {
                    binding.rvMenu.adapter = adapter
                    binding.rvMenu.layoutManager = LinearLayoutManager(context)
                    adapter.updateList(it.data as ArrayList<Dish>)
                    binding.rvMenu.isVisible = true
                    binding.pbLoading.isVisible = false
                }
                Status.LOADING -> {
                    binding.pbLoading.isVisible = true
                    binding.rvMenu.isVisible = false
                }
                Status.ERROR -> {
                    Toast.makeText( context,"${it.message}", Toast.LENGTH_LONG).show()
                    binding.pbLoading.isVisible = false
                }
                else -> {}
            }
        })
    }

    fun goToDetail(dish: Dish){
        val bundle = bundleOf("DISH_KEY" to dish)
        NavHostFragment.findNavController(this).navigate(R.id.action_menuFragment_to_detailFragment,bundle)
    }
}