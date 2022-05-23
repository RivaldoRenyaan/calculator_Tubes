package org.d3if2042.calculator.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CalculationDao {
    @Insert
    fun tambahData(calculation: CalculationEntity)

    @Query("SELECT * FROM calculation")
    fun ambilSemuaData(): LiveData<List<CalculationEntity>>

    @Query("DELETE FROM calculation")
    fun hapusData()
}