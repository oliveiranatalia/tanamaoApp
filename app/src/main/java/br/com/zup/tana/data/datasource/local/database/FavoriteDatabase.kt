package br.com.zup.tana.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.zup.tana.data.datasource.local.dao.FavoriteDAO
import br.com.zup.tana.data.model.Dish

@Database(entities = [Dish::class],version = 2)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDAO(): FavoriteDAO
}