package org.d3if2042.calculator.ui.kalkulasi

import android.os.Bundle
import android.view.*
//import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if2042.calculator.R
import org.d3if2042.calculator.databinding.KalkulasiFragmentBinding
import org.d3if2042.calculator.db.CalculationDb

class KalkulasiFragment : Fragment() {

    private val viewModel: KalkulasiViewModel by lazy {
        val db = CalculationDb.getInstance(requireContext())
        val factory = KalkulasiViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[KalkulasiViewModel::class.java]
    }

    private lateinit var binding: KalkulasiFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = KalkulasiFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.scheduleUpdater(requireActivity().application)
        getNumbersNOps()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_tentang -> {
                findNavController().navigate(
                    R.id.action_kalkulasiFragment_to_tentangFragment
                )
                true
            }
            R.id.menu_histori -> {
                findNavController().navigate(
                    R.id.action_kalkulasiFragment_to_historiFragment
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getNumbersNOps() {
        val textInputNumber = binding.textInputNumber
        val textResult = binding.textResult
        val button1 = binding.button1
        val button2 = binding.button2
        val button3 = binding.button3
        val button4 = binding.button4
        val button5 = binding.button5
        val button6 = binding.button6
        val button7 = binding.button7
        val button8 = binding.button8
        val button9 = binding.button9
        val button0 = binding.button0
        val buttonTambah = binding.buttonTambah
        val buttonKurang = binding.buttonKurang
        val buttonKali = binding.buttonKali
        val buttonBagi = binding.buttonBagi
        val buttonSamaDengan = binding.buttonSamaDengan
        val buttonReset = binding.buttonReset
        val buttonBackspace = binding.buttonHapus

//        S T A T E
        var isResultNumber = false
        var inputNumber = "0"

        textResult.text = inputNumber

        val calculator = Calculator()

        fun reset() {
            calculator.resetCalculator()

            inputNumber = "0"
            isResultNumber = false
            textInputNumber.text = ""
            textResult.text = "0"
        }

        fun backspace() {
            when {
                isResultNumber -> textInputNumber.text = ""
                else -> if (!inputNumber.contains(calculator.ops)) {
                    inputNumber = (if (inputNumber.length > 1) inputNumber.dropLast(1) else "0")
                    textResult.text = inputNumber
                }
            }
        }

        fun setNumberBtnFun(num: Int) {
            if (isResultNumber) reset()
            if (inputNumber.length > 8) return
            if (inputNumber == "0") inputNumber = num.toString() else inputNumber += num.toString()

            textResult.text = inputNumber
        }

        fun setOpsBtnFun(ops: String) {
            if (isResultNumber) {
                calculator.resetCalculator()
                isResultNumber = false
            }
            if (calculator.ops != "null") {
                inputNumber = calculator.num1.toString()
            }

            calculator.num1 = inputNumber.toInt()
            calculator.ops = ops

            inputNumber += " ${calculator.ops}"
            textInputNumber.text = inputNumber

            inputNumber = "0"
        }

        fun setResultBtnFun() {
            if (inputNumber == "0" || inputNumber != "0") {
                if (calculator.num1 == 0 && calculator.ops == "null") {
                    Toast.makeText(requireContext(), "Masukan angka dan operator", Toast.LENGTH_SHORT).show()
                    return
                }
            }

            calculator.num2 = inputNumber.toInt()

            inputNumber = "${calculator.num1} ${calculator.ops} ${calculator.num2}"
            textInputNumber.text = inputNumber

            val result = viewModel.calculating(calculator)
            textResult.text = result.toString()
            inputNumber = result.toString()

            isResultNumber = true

            viewModel.tambahData(calculator.num1, calculator.num2, calculator.ops, result.toDouble())
        }

        buttonBackspace.setOnClickListener { backspace() }
        buttonReset.setOnClickListener { reset() }

        button0.setOnClickListener { setNumberBtnFun(0) }
        button1.setOnClickListener { setNumberBtnFun(1) }
        button2.setOnClickListener { setNumberBtnFun(2) }
        button3.setOnClickListener { setNumberBtnFun(3) }
        button4.setOnClickListener { setNumberBtnFun(4) }
        button5.setOnClickListener { setNumberBtnFun(5) }
        button6.setOnClickListener { setNumberBtnFun(6) }
        button7.setOnClickListener { setNumberBtnFun(7) }
        button8.setOnClickListener { setNumberBtnFun(8) }
        button9.setOnClickListener { setNumberBtnFun(9) }

        buttonTambah.setOnClickListener { setOpsBtnFun("+") }
        buttonKurang.setOnClickListener { setOpsBtnFun("-") }
        buttonKali.setOnClickListener { setOpsBtnFun("x") }
        buttonBagi.setOnClickListener { setOpsBtnFun("/") }

        buttonSamaDengan.setOnClickListener { setResultBtnFun() }
    }
}

class Calculator(var num1: Int = 0, var num2: Int = 0, var ops: String = "null") {
    fun resetCalculator() { num1 = 0; num2 = 0; ops = "null" }
}