package co.tiagoaguiar.fitnesstracker

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.model.Calc
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.round

class ListCalcActivity : AppCompatActivity() {

    private lateinit var result: MutableList<Calc>
    private lateinit var adapter: ListCalcAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        result = mutableListOf<Calc>()
        adapter = ListCalcAdapter(result)
        val mainListRv = findViewById<RecyclerView>(R.id.rv_main_list)
        mainListRv.layoutManager = LinearLayoutManager(this)
        mainListRv.adapter = adapter


        val type =
            intent?.extras?.getString("type") ?: throw IllegalStateException("Type not found")


        Thread {
            val app = application as App
            val dao = app.db.calcDao()

            val response = dao.getRegisterByType(type)
            runOnUiThread {

                result.addAll(response)
                adapter.notifyDataSetChanged()
            }

        }.start()


    }


    private inner class ListCalcAdapter(
        private val calcItems: List<Calc>
    ) :
        RecyclerView.Adapter<ListCalcAdapter.MainViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return MainViewHolder(view)
        }

        override fun getItemCount(): Int {
            return calcItems.size
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.bind(calcItems[position], position)

        }

        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            @SuppressLint("SetTextI18n")
            fun bind(item: Calc, position: Int) {

                val textView = itemView as TextView
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))

                textView.text = getString(
                    R.string.list_response, item.res, sdf.format(item.createdAt)
                )


                textView.setOnLongClickListener {
                    AlertDialog.Builder(this@ListCalcActivity)
                        .setMessage(getString(R.string.delete_message))
                        .setPositiveButton(R.string.cancel) { _, _ -> }
                        .setNegativeButton(R.string.delete) { dialog, which ->

                            Thread {
                                val app = (application as App)
                                val dao = app.db.calcDao()
                                val response = dao.delete(item)

                                if (response < 0) return@Thread

                                runOnUiThread {
                                    result.removeAt(position)
                                    adapter.notifyItemRemoved(position)
                                }
                            }.start()


                        }
                        .create()
                        .show()

                    true
                }
            }
        }
    }
}