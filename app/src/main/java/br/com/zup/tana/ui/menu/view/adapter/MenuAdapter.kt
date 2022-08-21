package br.com.zup.projetofinalzup.ui.menu.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.zup.tana.data.model.Dish
import br.com.zup.tana.databinding.MenuItemBinding
import com.squareup.picasso.Picasso

class MenuAdapter (
    private var menu: List<Dish>,
    private val clickDetail: (dish:Dish) -> Unit
) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>(){

    class ViewHolder(val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun showInfo(dish:Dish){
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
        val item = menu[position]
        holder.showInfo(item)

        holder.binding.cvMenuItem.setOnClickListener{
            clickDetail(item)
        }
    }

    override fun getItemCount() = menu.size

    fun updateList(newList:MutableList<Dish>){
        menu = newList
        notifyDataSetChanged()
    }
}