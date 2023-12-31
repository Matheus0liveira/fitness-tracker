package co.tiagoaguiar.fitnesstracker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), OnItemClickListener {

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
                color = Color.DKGRAY
            ),
        )
        mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.baseline_remove_red_eye_24,
                textStringId = R.string.tmb,
                color = Color.YELLOW
            ),
        )

        val adapter = MainAdapter(mainItems) { id ->
            when (id) {
                1 -> {
                    startActivity(Intent(this@MainActivity, ImcActivity::class.java))
                }

                2 -> {
                    startActivity(Intent(this@MainActivity, TmbActivity::class.java))
                }


            }
        }


        recyclerViewMain = findViewById(R.id.rv_main)
        recyclerViewMain.adapter = adapter
        recyclerViewMain.layoutManager = GridLayoutManager(this, 2)

    }


    private inner class MainAdapter(
        private val mainItems: List<MainItem>,
        private val onItemClickListener: (id: Int) -> Unit
    ) :
        RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
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

        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem) {

                val itemContainerImc = itemView.findViewById<LinearLayout>(R.id.item_container_imc)
                val itemImgIcon = itemView.findViewById<ImageView>(R.id.item_img_icon)
                val itemTxtName = itemView.findViewById<TextView>(R.id.item_txt_name)

                itemTxtName.setText(item.textStringId)
                itemImgIcon.setImageResource(item.drawableId)
                itemContainerImc.setBackgroundColor(item.color)

                itemContainerImc.setOnClickListener {
                    onItemClickListener.invoke(item.id)
                }

            }
        }
    }


}