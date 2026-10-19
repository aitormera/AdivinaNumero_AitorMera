package com.aitor.myapplication
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable
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

        if (savedInstanceState != null) {
            numeroSecreto = savedInstanceState.getInt("numeroSecreto")
            intentos = savedInstanceState.getInt("intentos")
            contadorIntentos.text = "Intentos: $intentos"
            textHistorial.text = savedInstanceState.getString("historial", "")
        } else {
            nuevaPartida()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("numeroSecreto", numeroSecreto)
        outState.putInt("intentos", intentos)
        outState.putString("historial", textHistorial.text.toString())
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
            pedirNombre()
        } else {
            textHistorial.append(mensaje + "\n")
            scrollHistorial.post { scrollHistorial.fullScroll(View.FOCUS_DOWN) }
        }
    }
    private fun pedirNombre() {
        val input = EditText(this)
        input.hint = "Tu nombre"

        AlertDialog.Builder(this)
            .setTitle("¡Has ganado!")
            .setMessage("Introduce tu nombre para el ranking")
            .setView(input)
            .setCancelable(false)
            .setPositiveButton("Aceptar") { _, _ ->
                val nombre = input.text.toString().ifEmpty { "Anónimo" }
                val intent = Intent(this, RankingActivity::class.java)
                intent.putExtra("nombre", nombre)
                intent.putExtra("intentos", intentos)
                startActivity(intent)
                nuevaPartida()
            }
            .show()
    }
}

class Jugador(var nombre: String, var intentos: Int) : Serializable