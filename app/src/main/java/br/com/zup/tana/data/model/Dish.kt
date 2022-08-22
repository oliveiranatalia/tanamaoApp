package br.com.zup.tana.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "dish")
data class Dish(

    @PrimaryKey(autoGenerate = false)
    @SerializedName("name")
    val name:String,

    @SerializedName("description")
    val description:String,

    @SerializedName("value")
    val value:Double,

    @SerializedName("urlImageProduct")
    val urlImageProduct:String,

    var isFavorite: Boolean = false,

    var qtd:Int = 1
) : Parcelable
