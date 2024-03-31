package ba.unsa.etf.rma.tanerbajrovic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var plants: RecyclerView
    private lateinit var spinner: Spinner
    private lateinit var adapter: ArrayAdapter<String>
    private val modes: Array<String> = arrayOf("Medical", "Culinary", "Botanic")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val resetButton: Button = findViewById(R.id.resetBtn)
        plants = findViewById(R.id.biljkeRV)
        spinner = findViewById(R.id.modSpinner)
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, modes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        resetButton.setOnClickListener {
            spinner.setSelection(0)
            // Code for RecyclerView adapter
        }
    }

}

// Clicking a list item changes RecyclerView
// -> AdapterView for Spinner
// -> PlantListAdapter