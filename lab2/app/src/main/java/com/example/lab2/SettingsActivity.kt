package com.example.lab2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.iterator


class SettingsActivity : AppCompatActivity() {
    lateinit var numberFormat: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switch = findViewById<Switch>(R.id.enableSwitch)
        switch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener{ compoundButton, isChecked -> toggleEnable(isChecked) })


        numberFormat = intent.getStringExtra(getString(R.string.numberFormatKey)) ?: "Float"

        when(numberFormat){
            "Float" -> findViewById<RadioButton>(R.id.floatSelect).isChecked = true
            "Integer" -> findViewById<RadioButton>(R.id.integerSelect).isChecked = true
            else -> findViewById<RadioButton>(R.id.floatSelect).isChecked = true
        }

        val applyButton: Button = findViewById(R.id.applyButton)
        applyButton.setOnClickListener {
            applyChanges()
            finish()
        }
    }

    private fun applyChanges() {
        val returnIntent = Intent()
        returnIntent.putExtra(getString(R.string.numberFormatKey), numberFormat)
        setResult(RESULT_OK, returnIntent)
    }


    private fun toggleEnable(checked: Boolean) {
        val radioGroup = findViewById<RadioGroup>(R.id.optionsGroup)
        for(item in radioGroup){
            item.isEnabled = checked
        }
        radioGroup.setOnCheckedChangeListener { radioGroup, id ->
            numberFormat = findViewById<RadioButton>(id).text.toString()
        }
    }
}