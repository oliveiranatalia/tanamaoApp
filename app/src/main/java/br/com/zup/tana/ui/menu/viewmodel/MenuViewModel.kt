package br.com.zup.tana.ui.menu.viewmodel

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

class MenuViewModel(): ViewModel(){
    private val useCase = DishesUseCase()
    val menu = SingleLiveEvent<ViewState<List<Dish>>>()

    fun getMenu(){
        viewModelScope.launch {
            menu.value = ViewState.loading(null)
            try{
                val withContext = withContext(Dispatchers.Default){
                    useCase.getMenu()
                }
                withContext?.let {
                    menu.value = ViewState.success(it.data)
                }
            }catch(e:Exception){
                menu.value = ViewState.error(null,e.message)
            }
        }
    }

    class MenuViewModelFactory(): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MenuViewModel::class.java)){
                return MenuViewModel() as T
            }
            throw IllegalArgumentException("unknown viewmodel class")
        }
    }
}