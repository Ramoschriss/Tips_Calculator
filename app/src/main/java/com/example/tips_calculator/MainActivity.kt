package com.example.tips_calculator

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tips_calculator.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var percentage: Int = 0
        binding.rbOptionOne.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                percentage = 10
            }
        }
        binding.rbOptionTwo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                percentage = 15
            }
        }
        binding.rbOptionThree.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                percentage = 20
            }
        }

        // Definir cores e itens para o spinner
        val colors = listOf("#FF0000", "#00FF00", "#0000FF", "#FFA500", "#8B4513","#F5DEB3", "#7B68EE", "#8B008B", "#FF1493", "#800000" )
        val items = listOf("1 pessoa", "2 pessoas ", "3 pessoas ", "4 pessoas", "5 pessoas", "6 pessoas", "7 pessoas", "8 pessoas", "9 pessoas", "10 pessoas")

        // Criar o adapter personalizado
        val customAdapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            items
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.text = items[position]
                view.setTextColor(Color.parseColor(colors[position]))
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.text = items[position]
                view.setTextColor(Color.parseColor(colors[position]))
                return view
            }
        }

        // Configurar o adapter personalizado no spinner
        binding.spinnerNumberOfPeople.adapter = customAdapter

        var numOfPeopleSelected = 1 // Inicializar como 1 para evitar divisão por zero

        binding.spinnerNumberOfPeople.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    numOfPeopleSelected = position + 1 // +1 para representar "1 pessoa"
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        binding.btnDone.setOnClickListener {
            val totalTableTemp = binding.tieTotal.text

            if (totalTableTemp?.isEmpty() == true) {
                Snackbar.make(binding.tieTotal, "Preencha todos os campos", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                val totalTable: Float = totalTableTemp.toString().toFloat()
                val totalTemp = totalTable / numOfPeopleSelected
                val tips = totalTemp * percentage / 100
                val totalWithTips = totalTemp + tips
                binding.tvResult.text = "Total with tips: R$%.2f".format(totalWithTips)
            }
        }

        binding.btnClean.setOnClickListener {
            binding.tvResult.text = ""
            binding.tieTotal.setText("")
            binding.rbOptionThree.isChecked = false
            binding.rbOptionOne.isChecked = false
            binding.rbOptionTwo.isChecked = false
        }
    }
}
