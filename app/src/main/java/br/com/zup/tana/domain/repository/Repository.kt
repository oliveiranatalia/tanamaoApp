package br.com.zup.tana.domain.repository

import br.com.zup.tana.data.datasource.local.dao.FavoriteDAO
import br.com.zup.tana.data.datasource.remote.api.MenuRequest
import br.com.zup.tana.data.datasource.remote.retrofit.RetrofitService
import br.com.zup.tana.data.model.Dish

class Repository(private val dao: FavoriteDAO){

    suspend fun getMenu(menu:MenuRequest):List<Dish> = RetrofitService.getMenuAPI().getMenu(menu)

    fun insertIntoDatabase(dish:Dish) = dao.insertIntoDatabase(dish)

    fun deleteFromDatabase(dish: Dish) = dao.deleteFromDatabase(dish.name)

    fun getFavoritedList():List<Dish> = dao.getFavoritedList()

    fun insertToCart(dish: Dish) = dao.insertIntoCart(dish)

    fun updateCartList(dish: Dish)= dao.updateCartList(dish)

    fun getCartList():List<Dish> = dao.getCartList()
}