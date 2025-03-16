package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var currentInput: String = ""
    private var operator: String = ""
    private var firstNumber: Double? = null
    private var secondNumber: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)
    }

    fun onButtonClick(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()

        when (buttonText) {
            "C" -> clear()
            "CE" -> clearEntry()
            "BS" -> backspace()
            "+", "-", "x", "/" -> setOperator(buttonText)
            "=" -> calculateResult()
            "+/-" -> toggleSign()
            else -> appendNumber(buttonText)
        }
    }

    private fun clear() {
        currentInput = ""
        operator = ""
        firstNumber = null
        secondNumber = null
        tvResult.text = "0"
    }

    private fun clearEntry() {
        currentInput = ""
        tvResult.text = "0"
    }

    private fun backspace() {
        currentInput = if (currentInput.isNotEmpty()) currentInput.dropLast(1) else ""
        tvResult.text = currentInput.ifEmpty { "0" }
    }

    private fun setOperator(op: String) {
        if (currentInput.isNotEmpty()) {
            firstNumber = currentInput.toDouble()
            operator = op
            currentInput = ""
        }
    }

    private fun appendNumber(num: String) {
        if (num == "." && currentInput.contains(".")) return
        currentInput += num
        tvResult.text = currentInput
    }

    private fun toggleSign() {
        if (currentInput.isNotEmpty()) {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.substring(1)
            } else {
                "-$currentInput"
            }
            tvResult.text = currentInput
        }
    }

    private fun calculateResult() {
        if (firstNumber != null && currentInput.isNotEmpty()) {
            secondNumber = currentInput.toDouble()
            val result = when (operator) {
                "+" -> firstNumber!! + secondNumber!!
                "-" -> firstNumber!! - secondNumber!!
                "x" -> firstNumber!! * secondNumber!!
                "/" -> if (secondNumber!! != 0.0) firstNumber!! / secondNumber!! else "Error"
                else -> "Error"
            }
            tvResult.text = result.toString()
            currentInput = result.toString()
            firstNumber = null
            secondNumber = null
            operator = ""
        }
    }
}
