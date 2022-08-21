package br.com.zup.tana.ui.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.zup.tana.data.model.Dish
import br.com.zup.tana.domain.singleliveevent.SingleLiveEvent
import br.com.zup.tana.domain.usecase.DishesUseCase
import br.com.zup.tana.domain.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteListViewModel(): ViewModel() {
    private val useCase = DishesUseCase()
    val favState = SingleLiveEvent<ViewState<List<Dish>>>()

    fun getFavoritedList() {
        viewModelScope.launch {
            favState.value = ViewState.loading(null)
            try {
                val response = withContext(Dispatchers.Default) {
                    useCase.getFavoritedList()
                }
                favState.value = response
            } catch (e: Exception) {
                favState.value = ViewState.error(null, e.message)
            }
        }
    }

    class FavoriteListViewModelFactory(): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(FavoriteListViewModel::class.java)){
                return FavoriteListViewModel() as T
            }
            throw IllegalArgumentException("unknown viewmodel class")
        }
    }
}