package br.com.zup.tana.data.datasource.local

import android.app.Application
import androidx.room.Room
import br.com.zup.tana.data.datasource.local.database.FavoriteDatabase

class AppApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            FavoriteDatabase::class.java, "database-restaurant"
        )
            .fallbackToDestructiveMigration().allowMainThreadQueries()
            .build()
    }

    companion object {
        private lateinit var database: FavoriteDatabase
        fun getdatabase(): FavoriteDatabase {
            return database
        }
    }
}