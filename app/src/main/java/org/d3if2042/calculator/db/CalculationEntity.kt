package org.d3if2042.calculator.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calculation")
data class CalculationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val firstNumber: Int,
    val secondNumber: Int,
    val operator: String,
    val result: Double
)
