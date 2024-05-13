package com.example.palindromictipcalculator

//import android.R
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.slider.Slider
import kotlin.math.abs
import kotlin.math.log10


class MainActivity : AppCompatActivity() {

    fun Int.length() = when(this) {
        0 -> 1
        else -> log10(abs(toDouble())).toInt() + 1
    }

    fun isPalindromic(num: Int): Boolean {
        var num_copy: Int = num
        var n: Int = 0; var k: Int = 0; var rev: Int = 0;
        n = num_copy

        while (num_copy != 0){
            k = num_copy % 10
            rev = (rev * 10) + k
            num_copy /= 10
        }


        return n == rev
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tipPctSlider: Slider = findViewById<Slider>(R.id.tipPctSlider)
        val pctAmt: TextView = findViewById<TextView>(R.id.pctAmt)
        val submitBtn: Button = findViewById<Button>(R.id.submitBtn)
        val preTipAmt: EditText = findViewById<EditText>(R.id.preTipAmt)
        val preTipAmt2: TextView = findViewById<TextView>(R.id.preTipAmt2)
        val tipAmt: TextView = findViewById<TextView>(R.id.tipAmt)
        val totalAmt: TextView = findViewById<TextView>(R.id.totalAmt)
        val newTipPct: TextView = findViewById<TextView>(R.id.newTipPct)


        var tipPct: Int = 0
        var tipTemp: Double = 0.0
        var tip: Double = 0.0
        var totalTemp: Double = 0.0
        var finalTotalTemp: Int = 0
        var finalTotal: Double = 0.0
        var newTipPctVal: Double = 0.0

        tipPctSlider.addOnChangeListener{ slider, value, fromUser ->
            tipPct = tipPctSlider.value.toInt()
            pctAmt.text = "%d%%".format(tipPct)
            if(preTipAmt.text.toString().isEmpty()){
                return@addOnChangeListener
            }

            val preTip: Float = preTipAmt.text.toString().toFloat()
            tipTemp = preTip * tipPct * 0.01
            totalTemp = preTip + tipTemp

            finalTotalTemp = (totalTemp * 100).toInt()
            while (!isPalindromic(finalTotalTemp)) {
                finalTotalTemp += 1
            }
            finalTotal = finalTotalTemp / 100.0
            tip = finalTotal - preTip
            newTipPctVal = 100.0 * tip / preTip

            preTipAmt2.text = "$ %.02f".format(preTip)
            tipAmt.text = "$ %.02f".format(tip)
            totalAmt.text = "$ %.02f".format(finalTotal)
            newTipPct.text = "%.02f%%".format(newTipPctVal)
        }

        submitBtn.setOnClickListener{
            tipPct = tipPctSlider.value.toInt()
            if(preTipAmt.text.toString().isEmpty()){
                return@setOnClickListener
            }

            val preTip: Float = preTipAmt.text.toString().toFloat()
            tipTemp = preTip * tipPct * 0.01
            totalTemp = preTip + tipTemp

            finalTotalTemp = (totalTemp * 100).toInt()
            while (!isPalindromic(finalTotalTemp)) {
                finalTotalTemp += 1
            }
            finalTotal = finalTotalTemp / 100.0
            tip = finalTotal - preTip
            newTipPctVal = 100.0 * tip / preTip

            preTipAmt2.text = "$ %.02f".format(preTip)
            tipAmt.text = "$ %.02f".format(tip)
            totalAmt.text = "$ %.02f".format(finalTotal)
            newTipPct.text = "%.02f%%".format(newTipPctVal)
        }
    }
}