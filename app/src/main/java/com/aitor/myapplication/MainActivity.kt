package com.aitor.myapplication
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var numeroSecreto = 0
    lateinit var editNumero: EditText
    lateinit var button: Button
    lateinit var textHistorial: TextView
    lateinit var scrollHistorial: ScrollView
    lateinit var contadorIntentos : TextView;
    var intentos = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editNumero = findViewById(R.id.editNumero)
        button = findViewById(R.id.button)
        textHistorial = findViewById(R.id.textHistorial)
        scrollHistorial = findViewById(R.id.scrollHistorial)
        contadorIntentos = findViewById(R.id.contador)

        button.setOnClickListener { comprobarNumero() }

        nuevaPartida()

    }

    private fun nuevaPartida() {
        numeroSecreto = Random.nextInt(1, 101)
        textHistorial.text = ""
        intentos = 0
        contadorIntentos.text = "Intentos: " + intentos
    }

    private fun comprobarNumero() {
        val texto = editNumero.text.toString()


        if (texto.isEmpty()) {
            Toast.makeText(this, "Escribe un número", Toast.LENGTH_SHORT).show()
            return
        }


        val num = texto.toInt()

        val mensaje: String
        if (num < numeroSecreto) {
            mensaje = "El número es más grande que " + num
            intentos++
            contadorIntentos.text = "Intentos: " + intentos
        } else if (num > numeroSecreto) {
            mensaje = "El número es más pequeño que " + num
            intentos++
            contadorIntentos.text = "Intentos: " + intentos
        } else {
            mensaje = "¡Correcto! Era el " + num
            intentos++
            contadorIntentos.text = "Intentos: " + intentos
        }

        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        editNumero.setText("")


        if (num == numeroSecreto) {
            nuevaPartida()
        } else {
            textHistorial.append(mensaje + "\n")
            scrollHistorial.post { scrollHistorial.fullScroll(View.FOCUS_DOWN) }
        }
    }
}