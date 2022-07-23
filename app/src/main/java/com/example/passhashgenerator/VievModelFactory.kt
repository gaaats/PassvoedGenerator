package com.example.passhashgenerator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import javax.inject.Inject

class VievModelFactory @Inject constructor() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when (modelClass) {
            MainVievModel::class.java -> {
                MainVievModel() as T
            }
            else -> throw IllegalArgumentException("there is no such ViewModel")
        }
    }
}