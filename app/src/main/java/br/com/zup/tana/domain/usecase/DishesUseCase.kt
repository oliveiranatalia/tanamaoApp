package br.com.zup.tana.domain.usecase

import br.com.zup.tana.data.datasource.local.AppApplication
import br.com.zup.tana.data.datasource.remote.api.MenuRequest
import br.com.zup.tana.data.model.Dish
import br.com.zup.tana.domain.repository.Repository
import br.com.zup.tana.domain.viewstate.ViewState

class DishesUseCase {
    private val dao = AppApplication.getdatabase().favoriteDAO()
    private val repository = Repository(dao)

    suspend fun getMenu(): ViewState<List<Dish>> {
        return try {
            val response = repository.getMenu(MenuRequest("31037721000108"))
            ViewState.success(response)
        } catch (e: Exception) {
            ViewState.error(null, e.message)
        }
    }

    fun updateFavList(dish: Dish): ViewState<Dish> {
        return try {
            if (dish.isFavorite) {
                repository.insertIntoDatabase(dish)
            } else {
                repository.deleteFromDatabase(dish)
            }
            ViewState.success(dish)
        } catch (e: Exception) {
            ViewState.error(null, e.message)
        }
    }

    fun getFavoritedList(): ViewState<List<Dish>> {
        return try {
            val list = repository.getFavoritedList()
            if (list.isEmpty()) {
                ViewState.empty(list)
            } else {
                ViewState.success(list)
            }
        } catch (e: Exception) {
            ViewState.error(null, e.message)
        }
    }

    fun sendToCart(dish: Dish): ViewState<Dish> {
        return try {
            repository.insertToCart(dish)
            repository.updateCartList(dish)
            ViewState.success(dish)
        } catch (e: Exception) {
            ViewState.error(null, e.message)
        }
    }

    fun getCartList(): ViewState<List<Dish>> {
        return try {
            val list = repository.getCartList()
            ViewState.success(list)
        } catch (e: Exception) {
            ViewState.error(null, e.message)
        }
    }
}