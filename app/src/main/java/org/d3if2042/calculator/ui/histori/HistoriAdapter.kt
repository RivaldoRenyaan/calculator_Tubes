package org.d3if2042.calculator.ui.histori

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.d3if2042.calculator.databinding.LayoutItemBinding
import org.d3if2042.calculator.db.CalculationEntity

class HistoriAdapter : RecyclerView.Adapter<HistoriAdapter.HistoriViewModel>() {

    private val data = mutableListOf<CalculationEntity>()

    fun updateData(newData: List<CalculationEntity>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class HistoriViewModel(binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val num1 = binding.number1
        val num2 = binding.number2
        val ops = binding.operator
        val result = binding.result
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoriViewModel {
        val binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoriViewModel(binding)
    }

    override fun onBindViewHolder(holder: HistoriViewModel, position: Int) {
        Log.d("DEBUG_EUY", "posisi data = $position")
        val item = data[position]

        with(holder) {
            num1.text = item.firstNumber.toString()
            num2.text = item.secondNumber.toString()
            ops.text = item.operator
            result.text = item.result.toString()
        }
    }

    override fun getItemCount(): Int {
      Log.d("DEBUG_EUY", data.size.toString())
      return data.size
    }
}