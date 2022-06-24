package org.d3if2042.calculator.ui.tentang

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if2042.calculator.model.TentangAplikasi
import org.d3if2042.calculator.network.ApiServer
import org.d3if2042.calculator.network.ApiStatus
import org.d3if2042.calculator.network.ApiTentang

class TentangViewModel(private val application: Application):ViewModel() {
    private val data = MutableLiveData<TentangAplikasi>()
    private val status = MutableLiveData<ApiStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)

            try {
                data.postValue(ApiTentang.service.getTentang())
                status.postValue(ApiStatus.SUCCESS)

            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)
            }
        }
    }
    fun getData(): LiveData<TentangAplikasi> = data

    fun getStatus(): LiveData<ApiStatus> = status
}