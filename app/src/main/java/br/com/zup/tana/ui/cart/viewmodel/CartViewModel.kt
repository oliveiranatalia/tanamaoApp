package br.com.zup.tana.ui.cart.viewmodel

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

class CartViewModel():ViewModel() {
    private val useCase = DishesUseCase()
    val cartState = SingleLiveEvent<ViewState<List<Dish>>>()

    fun getCartList(){
        viewModelScope.launch {
            cartState.value = ViewState.loading(null)
            try{
                val response = withContext(Dispatchers.Default){
                    useCase.getCartList()
                }
                cartState.value = response
            }catch(e:Exception){
                cartState.value = ViewState.error(null,e.message)
            }
        }
    }

    class CartModelFactory(): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(CartViewModel::class.java)){
                return CartViewModel() as T
            }
            throw IllegalArgumentException("unknown viewmodel class")
        }
    }
}