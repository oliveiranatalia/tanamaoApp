package br.com.zup.tana.ui.favorite.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.zup.tana.data.model.Dish
import br.com.zup.tana.databinding.MenuItemBinding
import com.squareup.picasso.Picasso

class FavoritedListAdapter(
    private var favoritedList: List<Dish>,
    private val clickDetail: (dish:Dish) -> Unit
) : RecyclerView.Adapter<FavoritedListAdapter.ViewHolder>(){

    class ViewHolder(val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun showInfo(dish: Dish){
            binding.tvItemTitle.text = dish.name
            binding.tvItemDescription.text = dish.description
            val value = "R$ ${dish.value}"
            binding.tvItemValue.text = value
            Picasso.get().load(dish.urlImageProduct).into(binding.ivItemImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = favoritedList[position]
        holder.showInfo(item)
        holder.binding.cvMenuItem.setOnClickListener{
            clickDetail(item)
        }
    }

    override fun getItemCount() = favoritedList.size

    fun updateList(newList:MutableList<Dish>){
        favoritedList = newList
        notifyDataSetChanged()
    }
}