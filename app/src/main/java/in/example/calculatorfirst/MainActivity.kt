package `in`.example.calculatorfirst

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var calcInput: TextView? = null

    var lastNum : Boolean = false
    var lastDeci : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calcInput = findViewById(R.id.calcinput)
    }

    fun onDig(view: View){
        calcInput?.append((view as Button).text)
        lastNum = true
        lastDeci = false
    }

    fun onClear(view: View){
        calcInput?.text = ""
    }

    fun onDeci(view: View){
        if(lastNum and !lastDeci){
            calcInput?.append(".")
            lastNum = false
            lastDeci = true
        }
    }

    fun onPerator(view: View){
        calcInput?.text?.let{
            if(lastNum and !isOperatorAdded(it.toString())){
                calcInput?.append((view as Button).text)
                lastNum = false
                lastDeci = false
            }
        }

    }

    fun onEqual(view: View){
        if(lastNum){
            var prefix = ""
            var input = calcInput?.text.toString()
            try{
                if(input.startsWith("-")){
                    prefix = "-"
                    input = input.substring(1)
                }

                if(input.contains("-")){
                    val splitval = input.split("-")
                    var first = splitval[0]
                    val second = splitval[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    calcInput?.text = removeZeroAfter((first.toDouble() - second.toDouble()).toString())
                }

                if(input.contains("+")){
                    val splitval = input.split("+")
                    var first = splitval[0]
                    val second = splitval[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    calcInput?.text = removeZeroAfter(((first.toDouble()) + (second.toDouble())).toString())
                }

                if(input.contains("*")){
                    val splitval = input.split("*")
                    var first = splitval[0]
                    val second = splitval[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    calcInput?.text = removeZeroAfter((first.toDouble() * second.toDouble()).toString())
                }

                if(input.contains("/")){
                    val splitval = input.split("/")
                    var first = splitval[0]
                    val second = splitval[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    calcInput?.text = removeZeroAfter((first.toDouble() / second.toDouble()).toString())
                }


            }catch(e: ArithmeticException){
                e.printStackTrace()
            }

        }
    }

    private fun isOperatorAdded(value: String): Boolean{
        return if(value.startsWith("-") or value.startsWith("+")){
            false
        }else{
            value.contains("/") or
            value.contains("+") or
            value.contains("*") or
            value.contains("-")
        }
    }

    private fun removeZeroAfter(result: String): String{
        var value = result
        if(result.contains(".0"))
                value = result.substring(0, result.length - 2)
        return value
    }
}