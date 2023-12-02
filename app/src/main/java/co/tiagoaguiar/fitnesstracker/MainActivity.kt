package co.tiagoaguiar.fitnesstracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    lateinit var btnImc: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnImc = findViewById<LinearLayout>(R.id.btn_imc)

        btnImc.setOnClickListener {

            startActivity(Intent(this, ImcActivity::class.java))


        }
    }
}