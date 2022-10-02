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