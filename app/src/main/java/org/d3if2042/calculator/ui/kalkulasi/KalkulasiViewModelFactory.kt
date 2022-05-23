package org.d3if2042.calculator.ui.kalkulasi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if2042.calculator.db.CalculationDao
import java.lang.IllegalArgumentException

class KalkulasiViewModelFactory(private val db: CalculationDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KalkulasiViewModel::class.java)) {
            return KalkulasiViewModel(db) as T
        }
        throw IllegalArgumentException("KalkulasiViewModel class tidak dikenal")
    }
}