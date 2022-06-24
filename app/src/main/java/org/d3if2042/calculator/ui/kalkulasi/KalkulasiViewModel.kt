package org.d3if2042.calculator.ui.kalkulasi

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if2042.calculator.MainActivity
import org.d3if2042.calculator.db.CalculationDao
import org.d3if2042.calculator.db.CalculationEntity
import org.d3if2042.calculator.network.UpdateWorker
import java.util.concurrent.TimeUnit

class KalkulasiViewModel(private val db: CalculationDao) : ViewModel() {

    fun calculating(calculator: Calculator): Int {
        with(calculator) {
            return when (ops) {
                "+" -> num1.plus(num2)
                "-" -> num1.minus(num2)
                "x" -> num2.let { num1.times(it) }
                else -> num2.let { num1.div(it) }
            }
        }
    }


    fun tambahData(firstNumber: Int, secondNumber: Int, operator: String, result: Double) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val calculation = CalculationEntity(
                    firstNumber = firstNumber,
                    secondNumber = secondNumber,
                    operator = operator,
                    result = result
                )
                db.tambahData(calculation)
            }
        }
    }

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            MainActivity.CHANNEL_ID,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}