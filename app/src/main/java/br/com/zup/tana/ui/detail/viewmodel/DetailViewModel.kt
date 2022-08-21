package br.com.zup.tana.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.zup.tana.R
import br.com.zup.tana.data.datasource.local.AppApplication
import br.com.zup.tana.data.model.Dish
import br.com.zup.tana.domain.repository.Repository
import br.com.zup.tana.domain.singleliveevent.SingleLiveEvent
import br.com.zup.tana.domain.usecase.DishesUseCase
import br.com.zup.tana.domain.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel : ViewModel() {
    private val useCase = DishesUseCase()
    val listState = SingleLiveEvent<ViewState<List<Dish>?>>()
    val favorite = SingleLiveEvent<ViewState<Dish>>()
    val cart = SingleLiveEvent<ViewState<Dish>>()
    val favoriteList = getFavoritedList()


    fun getFavoritedList(): List<Dish>? {
        val dao = AppApplication.getdatabase().favoriteDAO()
        val repository = Repository(dao)
        try {
            return repository.getFavoritedList()
        } catch (e: Exception) {
            return null
        }
    }

    fun updateFavoritedList(dish: Dish) {
        viewModelScope.launch {
            try {
                val withContext = withContext(Dispatchers.Default) {
                    useCase.updateFavList(dish)
                }
                favorite.value = withContext
            } catch (e: Exception) {
                listState.value = ViewState.error(null, "${R.string.fav_error}")
            }
        }
    }

    fun sendItemToCart(dish: Dish) {
        viewModelScope.launch {
            try {
                val withContext = withContext(Dispatchers.Default) {
                    useCase.sendToCart(dish)
                }
                cart.value = withContext
            } catch (e: Exception) {
                listState.value = ViewState.error(null, "${R.string.cart_error}")
            }
        }
    }

    class DetailModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel() as T
            }
            throw IllegalArgumentException("unknown viewmodel class")
        }
    }
}