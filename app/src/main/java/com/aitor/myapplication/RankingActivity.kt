package com.aitor.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RankingActivity : AppCompatActivity() {

    companion object {
        var ranking = ArrayList<Jugador>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        val intentos = intent.getIntExtra("intentos", 0)
        val nombre = intent.getStringExtra("nombre") ?: "Anónimo"

        ranking.add(Jugador(nombre, intentos))
        ranking.sortBy { it.intentos }

        val table = findViewById<TableLayout>(R.id.tableRanking)

        val cabecera = TableRow(this)
        val thPos = TextView(this)
        thPos.text = "#"
        thPos.textSize = 18f
        thPos.setPadding(8, 8, 24, 8)
        cabecera.addView(thPos)
        val thNombre = TextView(this)
        thNombre.text = "Nombre"
        thNombre.textSize = 18f
        thNombre.setPadding(8, 8, 24, 8)
        cabecera.addView(thNombre)
        val thIntentos = TextView(this)
        thIntentos.text = "Intentos"
        thIntentos.textSize = 18f
        thIntentos.setPadding(8, 8, 8, 8)
        cabecera.addView(thIntentos)
        table.addView(cabecera)

        for (i in ranking.indices) {
            val fila = TableRow(this)

            val tvPos = TextView(this)
            tvPos.text = "${i + 1}"
            tvPos.textSize = 16f
            tvPos.setPadding(8, 8, 24, 8)
            fila.addView(tvPos)

            val tvNombre = TextView(this)
            tvNombre.text = ranking[i].nombre
            tvNombre.textSize = 16f
            tvNombre.setPadding(8, 8, 24, 8)
            fila.addView(tvNombre)

            val tvIntentos = TextView(this)
            tvIntentos.text = "${ranking[i].intentos}"
            tvIntentos.textSize = 16f
            tvIntentos.setPadding(8, 8, 8, 8)
            fila.addView(tvIntentos)

            table.addView(fila)
        }

        findViewById<Button>(R.id.btnNuevaPartida).setOnClickListener {
            finish()
        }
    }
}
