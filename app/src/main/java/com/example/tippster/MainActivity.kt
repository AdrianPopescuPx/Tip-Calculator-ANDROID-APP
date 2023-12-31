package com.example.tippster

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
private const val TAG = "MainActivity"
private const val initialTipPercent = 15;
class MainActivity : AppCompatActivity() {

    private lateinit var etBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipPercent: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTipDescription : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBarTip = findViewById(R.id.seekBarAmount)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTipPercent = findViewById(R.id.TipP)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tvTipDescription = findViewById(R.id.tvTipDesctiption)
        seekBarTip.progress = initialTipPercent
        tvTipPercent.text = "$initialTipPercent%"

        seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG, "onprogressChanged $p1")
                tvTipPercent.text = "$p1%";
                computeTipAndTotal()
                uptadeTipDescription(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        etBaseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                computeTipAndTotal()
            }

        })
    }

    private fun computeTipAndTotal() {
        // 1. Getting the value of the bill and the tip percentage
        if (etBaseAmount.text.isEmpty()) {
            tvTipAmount.text = ""
            tvTotalAmount.text = ""
            return
        }

        val baseAmount = etBaseAmount.text.toString().toDouble()
        val tipPercentage = seekBarTip.progress

        // 2. Computing the tip with the base bill
        val tipAmount = baseAmount * tipPercentage / 100
        val totalAmount = baseAmount + tipAmount

        // 3. Updating the UI
        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTotalAmount.text = "%.2f".format(totalAmount)
    }

    private fun uptadeTipDescription(tipPercentage: Int) {
        var tipDescription = ""
        if (tipPercentage >= 0 && tipPercentage <= 9) {
            tipDescription = "Poor"
        }   else if (tipPercentage > 9 && tipPercentage < 15) {
            tipDescription = "Acceptable"
        }   else if (tipPercentage > 14 && tipPercentage < 20) {
            tipDescription = "Good"
        }   else if (tipPercentage > 20 && tipPercentage < 25) {
            tipDescription = "Great"
        }   else tipDescription = "Fantastic"
        tvTipDescription.text = tipDescription
    }
}