package org.d3if2042.calculator.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if2042.calculator.db.CalculationDao

class HistoriViewModel(private val db: CalculationDao) : ViewModel() {
    private val data = db.ambilSemuaData()
    fun getData() = data

    fun hapusData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.hapusData()
            }
        }
    }
}