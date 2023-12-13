package co.tiagoaguiar.fitnesstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.lang.IllegalStateException

class ListCalcActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)


        val type =
            intent?.extras?.getString("type") ?: throw IllegalStateException("Type not found")


        Thread {
            val app = application as App
            val dao = app.db.calcDao()

            val response = dao.getRegisterByType(type)
            runOnUiThread {

                Log.i("'RESPONSE", "$response")
            }

        }.start()


    }
}