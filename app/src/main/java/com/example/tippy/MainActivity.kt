package com.example.tippy

import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val INITIAL_TIP_PERCENT = 15
class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount: EditText
    private lateinit var tvTipPercentLabel: TextView
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvStatus: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etBaseAmount = findViewById(R.id.etBaseAmount)
        tvTipPercentLabel = findViewById(R.id.tvTipPercentLabel)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tvStatus = findViewById(R.id.tvStatus)

        tvTipPercentLabel.text = "$INITIAL_TIP_PERCENT%"
        seekBarTip.progress = INITIAL_TIP_PERCENT
        tvStatus.text = "Acceptable"

        seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                tvTipPercentLabel.text = "$progress%"

                updateStatusText(progress)
                updateStatusColor(progress)
                calculate()
            }

            private fun updateStatusText(progress: Int) {
                when (progress) {
                    in 0..10 -> tvStatus.text = "Poor"
                    in 11..20 -> tvStatus.text = "Acceptable"
                    in 21..30 -> tvStatus.text = "Excellent"
                }
            }

            private fun updateStatusColor(progress: Int) {
                val color = ArgbEvaluator().evaluate(
                    progress.toFloat() / seekBarTip.max,
                    ContextCompat.getColor(this@MainActivity, R.color.red),
                    ContextCompat.getColor(this@MainActivity, R.color.green)
                ) as Int
                tvStatus.setTextColor(color)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })

        etBaseAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                calculate()
            }
        })
    }

    private fun calculate() {
        if(etBaseAmount.text.toString() != "") {
            val billAmount = etBaseAmount.text.toString().toDouble()
            val progress: Int = seekBarTip.progress
            val tip: Double = (progress * billAmount / 100)
            tvTipAmount.text = "%.2f".format(tip)
            tvTotalAmount.text = "%.2f".format((billAmount + tip))
            return
        }
        tvTipAmount.text = ""
        tvTotalAmount.text = ""
    }

    private fun updateColor() {

    }
}