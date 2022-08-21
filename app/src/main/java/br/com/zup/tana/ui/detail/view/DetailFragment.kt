package br.com.zup.tana.ui.detail.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import br.com.zup.tana.R
import br.com.zup.tana.data.model.Dish
import br.com.zup.tana.databinding.FragmentDetailBinding
import br.com.zup.tana.ui.detail.viewmodel.DetailViewModel
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var factory: DetailViewModel.DetailModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        factory = DetailViewModel.DetailModelFactory()
        viewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataRecovery()
    }

    private fun dataRecovery() {
        val dish = arguments?.getParcelable<Dish>("DISH_KEY")
        dish?.let {
            Picasso.get().load(it.urlImageProduct).into(binding.ivItemDetail)
            binding.tvItemTitle.text = it.name
            binding.tvItemDescription.text = it.description
            val value = "${getString(R.string.item_price)} ${it.value}"
            binding.tvItemPrice.text = value
            updateColor(dish)
        }
        binding.tvCartAdd.setOnClickListener {
            if (dish != null) {
                addItemCart(dish)
            }
        }
        binding.ivFavorite.setOnClickListener {
            if (dish != null) {
                dish.isFavorite = !dish.isFavorite
                viewModel.updateFavoritedList(dish)
                favoriteItem(dish)
                verifyIconFavorite(dish.isFavorite)

            }
        }
    }

    private fun addItemCart(dish: Dish) {
        viewModel.sendItemToCart(dish)
        Toast.makeText(context, R.string.item_add, Toast.LENGTH_SHORT).show()
    }

    private fun updateColor(dish: Dish) {
        var isFavorite = false
        var listFavorite = viewModel.favoriteList
        listFavorite?.forEach {
            if (it.name == dish.name) {
                isFavorite = true
            }
        }
        verifyIconFavorite(isFavorite)
    }

    private fun verifyIconFavorite(isFavorite: Boolean) {
        binding.ivFavorite.setImageDrawable(
            ContextCompat.getDrawable
                (
                binding.root.context,
                if (isFavorite) {
                    R.drawable.fav_icon
                } else {
                    R.drawable.icon_heart
                }
            )
        )
    }

    fun favoriteItem(dish: Dish) {
        if (dish.isFavorite) {
            Toast.makeText(
                context,
                "${dish.name} ${getString(R.string.item_fav)}",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                context,
                "${dish.name} ${getString(R.string.item_disfav)}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}