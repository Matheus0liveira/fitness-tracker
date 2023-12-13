package co.tiagoaguiar.fitnesstracker

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.model.Calc
import org.w3c.dom.Text
import java.lang.IllegalStateException
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.Optional
import kotlin.math.round
import kotlin.math.roundToInt

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

                val adapter = MainAdapter(response)

                val mainListRv = findViewById<RecyclerView>(R.id.rv_main_list)

                mainListRv.adapter = adapter
                mainListRv.layoutManager = LinearLayoutManager(this)
            }

        }.start()


    }


    private inner class MainAdapter(
        private val calcItems: List<Calc>
    ) :
        RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return MainViewHolder(view)
        }

        override fun getItemCount(): Int {
            return calcItems.size
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.bind(calcItems[position])

        }

        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            @SuppressLint("SetTextI18n")
            fun bind(item: Calc) {

                val textItem = itemView as TextView

                textItem.text =
                    "${SimpleDateFormat("dd/MM/yyyy").format(item.createdAt)} - ${item.type.uppercase()}: ${
                        round(
                            item.res
                        )
                    }"
            }
        }
    }
}