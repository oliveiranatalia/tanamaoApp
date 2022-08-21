package br.com.zup.tana.domain.viewstate

import br.com.zup.tana.R

data class ViewState<T>(val status:Status,val data:T?,val message:String?){

    companion object{
        fun <T> success(data:T?):ViewState<T> = ViewState(Status.SUCCESS,data,null)
        fun <T> error(data:T?,message: String?):ViewState<T> = ViewState(Status.ERROR,data,message)
        fun <T> loading(data:T?):ViewState<T> = ViewState(Status.LOADING,data,null)
        fun <T> empty(data: T?): ViewState<T> = ViewState(Status.EMPTY,data,"${R.string.empty_favlist}")
    }
}
