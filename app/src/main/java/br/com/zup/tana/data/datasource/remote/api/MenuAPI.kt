package br.com.zup.tana.data.datasource.remote.api

import br.com.zup.tana.data.model.Dish
import retrofit2.http.Body
import retrofit2.http.POST

interface MenuAPI {

    @POST("menu/items")
    suspend fun getMenu(@Body menu: MenuRequest):List<Dish>
}