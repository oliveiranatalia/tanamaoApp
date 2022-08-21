package br.com.zup.tana.data.datasource.local.dao

import androidx.room.*
import br.com.zup.tana.data.model.Dish

@Dao
interface FavoriteDAO {

    @Query("Select * From dish")
    fun getFavoritedList(): List<Dish>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFavList(item: Dish)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIntoDatabase(item: Dish)

    @Query("DELETE FROM dish WHERE name = :name")
    fun deleteFromDatabase(name: String)

    @Query("Select * From dish")
    fun getCartList(): List<Dish>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCartList(item: Dish)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIntoCart(item: Dish)
}