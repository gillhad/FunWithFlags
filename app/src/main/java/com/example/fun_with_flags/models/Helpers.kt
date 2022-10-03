package com.example.fun_with_flags.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContinentViewModel: ViewModel() {
    private val mutableSelectedItem = MutableLiveData<Int>()
    val continentPosition: LiveData<Int> = mutableSelectedItem

    fun selectItem(pos: Int) {
        mutableSelectedItem.value = pos
    }

}

class CheckMenuModeViewModel: ViewModel(){
    private val mutableSelectionItem = MutableLiveData<String>()
    val checkMode: LiveData<String> = mutableSelectionItem

    fun selectiItem(type: String){
        mutableSelectionItem.value = type
    }

}