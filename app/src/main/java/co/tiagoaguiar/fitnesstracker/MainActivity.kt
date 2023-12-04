package co.tiagoaguiar.fitnesstracker

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var recyclerViewMain: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.ic_baseline_wb_sunny_24,
                textStringId = R.string.imc,
                color = Color.GREEN
            ),
        )
        mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.ic_baseline_wb_sunny_24,
                textStringId = R.string.tmb,
                color = Color.GREEN
            ),
        )

        val adapter = MainAdapter(mainItems)
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

    private inner class MainAdapter(private val mainItems: List<MainItem>) :
        RecyclerView.Adapter<MainViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        override fun getItemCount(): Int {
            return mainItems.size
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.bind(mainItems[position])

        }
    }

    private class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MainItem) {

            val btn = itemView.findViewById<Button>(R.id.btn_item)
            btn.setText(item.textStringId)

            btn.setOnClickListener {
                Log.i("-==-=--=-=-=-=-=", "IDDDDD: ${item.id}")
            }
        }
    }
}