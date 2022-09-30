package com.example.home1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlin.math.pow

class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {
    private var currentOperation: String = " "
    var result: Float = 0f
    private var format : String = "Float"
    var calcHistory = Array<String>(10){""}
    var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById(R.id.operationSpinner)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val op1Button = findViewById<Button>(R.id.firstOperationButton)
                val op2Button = findViewById<Button>(R.id.secondOperationButton)

                when (position) {
                    0 -> {
                        op1Button.text = "+"
                        op2Button.text = "-"
                    }

                    1 -> {
                        op1Button.text = "*"
                        op2Button.text = "/"
                    }

                    2 -> {
                        op1Button.text = "^"
                        op2Button.text = "v"
                    }

                    else -> {
                        op1Button.text = "+"
                        op2Button.text = "-"
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val calcButton : Button = findViewById(R.id.calcButton)
        calcButton.setOnClickListener (this)
        calcButton.setOnLongClickListener (this)
        val fab = findViewById<FloatingActionButton>(R.id.clearButton)
        fab.setOnClickListener {
            clearCalculator()
        }

        val clearAll = findViewById<Button>(R.id.clearHistoryB)
        clearAll.setOnClickListener{
            clearHistory()
        }
    }

    private fun clearCalculator() {
        currentOperation = " "
        result = 0f
        updateOperation()
        updateResult(getString(R.string.result))
        findViewById<EditText>(R.id.firstNumber).text.clear()
        findViewById<EditText>(R.id.secondNumber).text.clear()

        Snackbar.make(
            findViewById(R.id.mainContainer),
            getString(R.string.clear_msg),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    fun selectOperation(view: View) {
        currentOperation = (view as Button).text.toString()

        updateOperation()
    }

    private fun updateOperation(){
        val operationText = findViewById<TextView>(R.id.operatorSymbol)
        operationText.text = currentOperation
    }

    override fun onClick(p0: View?) {
        val firstNum = (findViewById<EditText>(R.id.firstNumber).text).toString().toFloatOrNull() ?: 0f
        val secondNum = (findViewById<EditText>(R.id.secondNumber).text).toString().toFloatOrNull() ?: 0f

        val resultStr: String = getResult(firstNum, secondNum)

        updateResult(resultStr)
        updateHistory(resultStr)
    }

    override fun onLongClick(p0: View?): Boolean {
        val firstNum = (findViewById<EditText>(R.id.firstNumber).text).toString().toFloatOrNull() ?: 0f
        val secondNum = (findViewById<EditText>(R.id.secondNumber).text).toString().toFloatOrNull() ?: 0f

        val resultStr: String = getResult(firstNum, secondNum, updateResult = true)

        updateResult(resultStr)
        updateHistory(resultStr)
        return true
    }

    private fun getResult(firstNum: Float, secondNum: Float, updateResult: Boolean = false): String {
        val prevResult = if (updateResult) {
            result
        } else {
            if (currentOperation.equals("/") || currentOperation.equals("*") || currentOperation.equals("^") || currentOperation.equals("v")) 1f else 0f
        }

        result = when (currentOperation) {
            "+" -> prevResult + firstNum + secondNum
            "-" -> {
                if(prevResult != 0f){
                    prevResult - (firstNum - secondNum)
                } else {
                    firstNum - secondNum
                }
            }
            "*" -> prevResult * firstNum * secondNum
            "/" -> {
                if (secondNum != 0f){
                    prevResult * ( firstNum / secondNum )
                }
                else{
                    return "Can't / by 0"
                }
            }
            "^" -> (prevResult * firstNum).pow(secondNum)
            "v" -> (prevResult * firstNum).pow(1 / secondNum)
            else -> 0f
        }

        return "${getString(R.string.result)} $result"
    }

    private fun updateResult(resultStr: String) {
        val resultTextView = findViewById<TextView>(R.id.resultTxtView)
        resultTextView.text = resultStr
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settingsItem -> startSettingsActivity()
            R.id.shareItem -> shareResult()
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startSettingsActivity() {
        val intent: Intent = Intent(this, SettingsActivity::class.java)
        intent.putExtra(getString(R.string.numberFormatKey), format)
        launchSettingsActivity.launch(intent)
    }

    private fun shareResult() {
        val shareIntent: Intent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, getExpressionResult())
        if(shareIntent.resolveActivity(packageManager) != null){
            startActivity(shareIntent)
        }
    }

    private val launchSettingsActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK) {
            format = result.data?.getStringExtra(getString(R.string.numberFormatKey)) ?: "Float"
            Snackbar.make(
                findViewById(R.id.mainContainer),
                format,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }


    private fun getExpressionResult(): String? {
        val firstNum = (findViewById<EditText>(R.id.firstNumber).text).toString().toFloatOrNull() ?: 0f
        val secondNum = (findViewById<EditText>(R.id.secondNumber).text).toString().toFloatOrNull() ?: 0f
        val equation = "${firstNum} $currentOperation ${secondNum} = "
        return when (currentOperation) {
            "+" -> "$equation ${firstNum + secondNum}"
            "-" -> "$equation ${firstNum - secondNum}"
            "*" -> "$equation ${firstNum * secondNum}"
            "/" -> "$equation ${firstNum / secondNum}"
            "^" -> "$equation ${firstNum} ^ ${secondNum}"
            "v" -> "$equation ${secondNum} v ${firstNum}"
            else -> "error"
        }
    }

    private fun clearHistory() {
        calcHistory = Array<String>(10){""}
        updateHistory("")
    }
    private fun updateHistory(resultStr: String){
        if(counter > 10){
            counter = 9
        }
        for(i in counter downTo 1) {
            calcHistory[i] = calcHistory[i-1]
        }
        calcHistory[0] = resultStr
        counter += 1
        val hisList = findViewById<ListView>(R.id.hisList)

        val arrayAdapter:ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1,calcHistory
        )
        hisList.adapter=arrayAdapter
        // adapter przed dostep do danych z listy bedzie tworzyc view, czyli wyswietlac je
    }
}