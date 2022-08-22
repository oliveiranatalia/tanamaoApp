package br.com.zup.tana.ui.cart.view

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
import br.com.zup.tana.databinding.FragmentCartBinding
import br.com.zup.tana.domain.viewstate.Status
import br.com.zup.tana.ui.cart.view.adapter.CartAdapter
import br.com.zup.tana.ui.cart.viewmodel.CartViewModel
import br.com.zup.tana.ui.home.view.MainActivity

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var viewModel: CartViewModel
    private lateinit var factory: CartViewModel.CartModelFactory
    private val adapter: CartAdapter by lazy { CartAdapter(arrayListOf(), this::goToDetail) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        factory = CartViewModel.CartModelFactory()
        viewModel = ViewModelProvider(this,factory).get(CartViewModel::class.java)

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCartList()

        binding.bvCloseOrder.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_cartFragment_to_endFragment)
        }

        viewModel.cartState.observe(viewLifecycleOwner, Observer{
            when(it.status){
                Status.EMPTY -> {
                    binding.ivEmptyListFav.setImageResource(R.drawable.empty_list)
                    binding.ivEmptyListFav.isVisible = true
                    binding.tvEmptyListFav.isVisible = true
                    binding.pbLoading.isVisible = false
                }
                Status.SUCCESS -> {
                    binding.rvCart.adapter = adapter
                    binding.rvCart.layoutManager = LinearLayoutManager(context)
                    adapter.updateList(it.data as MutableList<Dish>)
                    binding.rvCart.isVisible = true
                    binding.pbLoading.isVisible = false
                    totalOrder(it.data)
                }
                Status.LOADING -> {
                    binding.pbLoading.isVisible = true
                    binding.rvCart.isVisible = false
                }
                Status.ERROR -> {
                    Toast.makeText( context,"${it.message}", Toast.LENGTH_LONG).show()
                    binding.pbLoading.isVisible = false
                }
            }
        })
    }

    fun goToDetail(dish: Dish){
        val bundle = bundleOf("DISH_KEY" to dish)
        NavHostFragment.findNavController(this).navigate(R.id.action_cartFragment_to_detailFragment,bundle)
    }

    fun totalOrder(list: List<Dish>){
        var value = 0.0
        for(dish in list){
            value = (value + dish.value * dish.qtd)
        }
        val string = getString(R.string.total)
        binding.bvTotalOrder.text = string + (value.toString())
    }
}