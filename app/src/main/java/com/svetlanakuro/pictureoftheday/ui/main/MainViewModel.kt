package com.svetlanakuro.pictureoftheday.ui.main

import android.view.MenuItem
import androidx.lifecycle.*
import com.svetlanakuro.pictureoftheday.domain.*

class MainViewModel : ViewModel() {

    val openScreenLiveData: LiveData<Event<Screens>> = MutableLiveData()

    fun bottomNavigationViewItemSelected(menuItem: MenuItem) {
        openScreenLiveData.postValue(Event(Screens.getById(menuItem.itemId)))
    }

    fun bottomNavigationViewItemReselected(menuItem: MenuItem) {
        //TODO("Not yet implemented")
    }
}

private fun <T> LiveData<T>.postValue(value: T) {
    (this as MutableLiveData<T>).postValue(value)
}