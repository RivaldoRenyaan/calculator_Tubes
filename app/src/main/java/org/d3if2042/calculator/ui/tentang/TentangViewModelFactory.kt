package org.d3if2042.calculator.ui.tentang

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TentangViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TentangViewModel::class.java)) {
            return TentangViewModel(application) as T
        }
        throw IllegalArgumentException("KalkulasiViewModel class tidak dikenal")
    }
}