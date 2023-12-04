package co.tiagoaguiar.fitnesstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var recyclerViewMain: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MainAdapter()
        recyclerViewMain = findViewById(R.id.rv_main)
        recyclerViewMain.adapter = adapter
        recyclerViewMain.layoutManager = LinearLayoutManager(this)

//        btnImc = findViewById<LinearLayout>(R.id.btn_imc)
//
//        btnImc.setOnClickListener {
//
//            startActivity(Intent(this, ImcActivity::class.java))
//
//
//        }
    }

    private inner class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        override fun getItemCount(): Int {
            return 15
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        }
    }

    private class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}