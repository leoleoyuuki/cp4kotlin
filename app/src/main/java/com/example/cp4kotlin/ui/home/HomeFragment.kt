package com.example.cp4kotlin.ui.home
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cp4kotlin.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate o layout deste fragmento
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referências aos elementos do layout
        val etAltura: EditText = view.findViewById(R.id.etAltura)
        val etPeso: EditText = view.findViewById(R.id.etPeso)
        val btnCalcular: Button = view.findViewById(R.id.btnCalcular)
        val tvResultado: TextView = view.findViewById(R.id.tvResultado)

        // Ação do botão de calcular IMC
        btnCalcular.setOnClickListener {
            var alturaStr = etAltura.text.toString()
            var pesoStr = etPeso.text.toString()

            // Substitui vírgula por ponto, se necessário
            alturaStr = alturaStr.replace(",", ".")
            pesoStr = pesoStr.replace(",", ".")

            // Verifica se os campos não estão vazios e se os valores são válidos
            if (alturaStr.isNotEmpty() && pesoStr.isNotEmpty()) {
                try {
                    val alturaCm = alturaStr.toDouble() // Altura em centímetros
                    val peso = pesoStr.toDouble()

                    if (alturaCm > 0 && peso > 0) {
                        // Converte altura de centímetros para metros
                        val altura = alturaCm / 100

                        // Cálculo do IMC
                        val imc = peso / (altura * altura)

                        // Verifica a categoria do IMC
                        val resultado = when {
                            imc < 18.5 -> "Abaixo do peso"
                            imc in 18.5..24.9 -> "Peso normal"
                            imc in 25.0..29.9 -> "Sobrepeso"
                            imc >= 30 -> "Obesidade"
                            else -> "Erro"
                        }

                        // Exibe o resultado no TextView
                        tvResultado.text = String.format("IMC: %.2f - %s", imc, resultado)
                    } else {
                        Toast.makeText(context, "Altura e peso devem ser maiores que zero", Toast.LENGTH_SHORT).show()
                    }

                } catch (e: NumberFormatException) {
                    // Caso ocorra erro na conversão dos valores
                    Toast.makeText(context, "Por favor, insira valores válidos", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}