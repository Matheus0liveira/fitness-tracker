package co.tiagoaguiar.fitnesstracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import co.tiagoaguiar.fitnesstracker.model.Calc
import com.google.android.material.textfield.TextInputLayout

class TmbActivity : AppCompatActivity() {

    private lateinit var lifeStyle: AutoCompleteTextView
    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText
    private lateinit var editAge: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmb)

        editWeight = findViewById(R.id.edit_tmb_weight)
        editHeight = findViewById(R.id.edit_tmb_height)
        editAge = findViewById(R.id.edit_tmb_age)
        val btnSend = findViewById<Button>(R.id.btn_tmb_send)

        lifeStyle = findViewById(R.id.auto_lifestyle)

        val items = resources.getStringArray(R.array.tmb_lifestyle)
        lifeStyle.setText(items.first())
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        lifeStyle.setAdapter(adapter)


        btnSend.setOnClickListener {
            if (!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val weight = editWeight.text.toString().toInt()
            val height = editHeight.text.toString().toInt()
            val age = editAge.text.toString().toInt()

            val result = calculateTmb(weight, height, age)
            val response = tmbRequest(result)

            AlertDialog.Builder(this)
                .setMessage(getString(R.string.tmb_response, response))
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setNegativeButton(R.string.save) { dialog, which ->

                    Thread {
                        val app = (application as App)
                        val dao = app.db.calcDao()
                        dao.insert(
                            Calc(type = "tmb", res = response)
                        )

                        runOnUiThread {
                            openListActivity()
                        }
                    }.start()


                }
                .create()
                .show()

            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }


    private fun calculateTmb(weight: Int, height: Int, age: Int): Double {

        return 66 + (13.8 * weight) + (5 * height) - (6.8 * age)

    }

    private fun tmbRequest(tmb: Double): Double {
        val items = resources.getStringArray(R.array.tmb_lifestyle)

        return when (lifeStyle.text.toString()) {
            items[0] -> tmb * 1.2
            items[1] -> tmb * 1.375
            items[2] -> tmb * 1.55
            items[3] -> tmb * 1.725
            items[4] -> tmb * 1.9
            else -> 0.0
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> {
                finish()
                openListActivity()
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun openListActivity() {
        startActivity(
            Intent(
                this,
                ListCalcActivity::class.java
            ).putExtra("type", "tmb")
        )

    }


    private fun validate(): Boolean {
        return (editWeight.text.toString().isNotEmpty()
                && editHeight.text.toString().isNotEmpty()
                && editAge.text.toString().isNotEmpty()
                && !editWeight.text.toString().startsWith("0")
                && !editHeight.text.toString().startsWith("0")
                && !editAge.text.toString().startsWith("0")
                )
    }
}