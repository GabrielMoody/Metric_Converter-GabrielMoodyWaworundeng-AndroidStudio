package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.math.abs

class MainActivity : AppCompatActivity()
    , AdapterView.OnItemSelectedListener
{

    private lateinit var spinner: Spinner
    private lateinit var fromSpinner: Spinner
    private lateinit var toSpinner: Spinner
    private lateinit var convertButton: Button
    private lateinit var userInput: EditText
    private lateinit var resultConvert: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.title)

        spinner = findViewById(R.id.metric_chooser_spinner)
        fromSpinner = findViewById(R.id.metric_from_spinner)
        toSpinner = findViewById(R.id.metric_to_spinner)
        convertButton = findViewById(R.id.convert_button)
        userInput = findViewById(R.id.number)
        resultConvert  = findViewById(R.id.result_convert)

        spinner.onItemSelectedListener = this

        val items = resources.getStringArray(R.array.metric_array)

        val spinnerAdapter= object : ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items) {

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView =
                    super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) {
                    view.setTextColor(Color.GRAY)
                }
                return view
            }

        }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        convertButton.setOnClickListener{

            if(userInput.text == null) {
                Toast.makeText(this, "Please input a number to convert", Toast.LENGTH_SHORT).show()
            }

            var result: Double = 0.0

            when(spinner.selectedItem.toString()) {
                "Panjang" -> result = convertUnitLength()
                "Massa" -> result = convertUnitMassa()
                "Waktu" -> result = convertUnitWaktu()
                "Suhu" -> result = convertUnitSuhu()
            }

            resultConvert.text = "Hasil : " + result.toString() + toSpinner.selectedItem.toString()

        }
    }

    private fun convertUnitLength(): Double {
        val units = arrayOf("mm", "cm", "dm", "m", "dam", "hm", "km")
        val num = arrayOf(1, 10, 100, 1000, 10000, 100000, 1000000)
        val numToConv = userInput.text.toString().toDouble()
        val from = fromSpinner.selectedItem.toString()
        val to = toSpinner.selectedItem.toString()

        val a = units.indexOf(from)
        val b = units.indexOf(to)

        if (a-b < 0) {
            return (numToConv / num[abs(a-b)])
        }

        return (numToConv * num[abs(a-b)])
    }

    private fun convertUnitMassa(): Double {
        val units = arrayOf("mg", "cg", "dg", "g", "dag", "hg", "kg")
        val num = arrayOf(1, 10, 100, 1000, 10000, 100000, 1000000)
        val numToConv = userInput.text.toString().toDouble()
        val from = fromSpinner.selectedItem.toString()
        val to = toSpinner.selectedItem.toString()

        val a = units.indexOf(from)
        val b = units.indexOf(to)

        if (a-b < 0) {
            return (numToConv / num[abs(a-b)])
        }

        return (numToConv * num[abs(a-b)])
    }

    private fun convertUnitWaktu(): Double {
        val units = arrayOf("s", "m", "h")
        val num = arrayOf(1, 60, 3600)
        val numToConv = userInput.text.toString().toDouble()
        val from = fromSpinner.selectedItem.toString()
        val to = toSpinner.selectedItem.toString()

        val a = units.indexOf(from)
        val b = units.indexOf(to)

        if (a-b < 0) {
            return (numToConv / num[abs(a-b)])
        }

        return (numToConv * num[abs(a-b)])
    }

    private fun convertUnitSuhu(): Double {
        val numToConv = userInput.text.toString().toDouble()
        val from = fromSpinner.selectedItem.toString()
        val to = toSpinner.selectedItem.toString()

        if(from === "c" && to === "f") {
            return (9 / 5 * numToConv + 32)
        } else if(from === "c" && to === "k") {
            return (numToConv + 273)
        } else if(from === "c" && to === "r") {
            return (4 / 5 * numToConv)
        } else if(from === "f" && to === "c") {
            return (5 / 9 * (numToConv-32))
        } else if(from === "f" && to === "k") {
            return (5 / 9 * (numToConv-32) + 273)
        } else if(from === "f" && to === "r") {
            return (4 / 9 * (numToConv-32))
        } else if(from === "k" && to === "c") {
            return (numToConv - 273)
        } else if(from === "k" && to === "f") {
            return (9 / 5 * (numToConv-273) + 32)
        } else if(from === "k" && to === "r") {
            return (4 / 5 * (numToConv-273))
        } else if(from === "r" && to === "c") {
            return (5 / 4 * numToConv)
        } else if(from === "r" && to === "f") {
            return (9 /4 * numToConv + 32)
        } else if(from === "r" && to === "k") {
            return (5 / 4 * numToConv + 273)
        } else {
            return numToConv
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        fromSpinner.visibility = View.INVISIBLE
        toSpinner.visibility = View.INVISIBLE
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        fromSpinner.visibility = View.VISIBLE
        toSpinner.visibility = View.VISIBLE

        val metric = spinner.selectedItem.toString()
        var metricOpt = R.array.length

        when(metric) {
            "Panjang" -> metricOpt = R.array.length
            "Massa" -> metricOpt = R.array.massa
            "Waktu" -> metricOpt = R.array.waktu
            "Suhu" -> metricOpt = R.array.suhu
        }

        val items = resources.getStringArray(metricOpt)

        val fromSpinnerAdapter= object : ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items) {

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView =
                    super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) {
                    view.setTextColor(Color.GRAY)
                }
                return view
            }
        }

        fromSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromSpinner.adapter = fromSpinnerAdapter

        val toSpinnerAdapter= object : ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items) {

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView =
                    super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) {
                    view.setTextColor(Color.GRAY)
                }
                return view
            }
        }

        toSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        toSpinner.adapter = toSpinnerAdapter
    }
}