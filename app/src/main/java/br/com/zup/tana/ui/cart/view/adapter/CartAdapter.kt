package br.com.zup.tana.ui.cart.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.zup.tana.data.model.Dish
import br.com.zup.tana.databinding.CartItemBinding
import com.squareup.picasso.Picasso

class CartAdapter(
    private var cartList: List<Dish>,
    private val clickDetail: (dish: Dish) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>(){

    class ViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun showInfo(dish: Dish){
            binding.tvItemTitle.text = dish.name
            val value = (dish.qtd * dish.value).toString()
            binding.tvItemValue.text = value
            val qtd = "${dish.qtd}x"
            binding.tvItemQtd.text = qtd
            Picasso.get().load(dish.urlImageProduct).into(binding.ivItemImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = cartList[position]
        holder.showInfo(menu)
        holder.binding.cvCart.setOnClickListener{
            clickDetail(menu)
        }
    }

    override fun getItemCount() = cartList.size

    fun updateList(newList:MutableList<Dish>){
        cartList = newList
        notifyDataSetChanged()
    }
}