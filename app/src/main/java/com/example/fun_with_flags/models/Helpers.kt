package com.example.fun_with_flags.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContinentViewModel: ViewModel() {
    private val mutableSelectedItem = MutableLiveData<Int>()
    val continentPosition: LiveData<Int> = mutableSelectedItem

    fun selectItem(pos: Int) {
        mutableSelectedItem.value = pos
        println("El valor se ha actualizado es:")
        println(mutableSelectedItem.value)
    }

    fun getItem(): Int{
        println("El valor actual es:")
        println(mutableSelectedItem.value)
        return mutableSelectedItem.value!!
    }


}