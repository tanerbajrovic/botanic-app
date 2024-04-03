package ba.unsa.etf.rma.tanerbajrovic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var plants: RecyclerView
    private lateinit var plantsAdapter: PlantListAdapter
    private lateinit var spinner: Spinner
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private val states: Array<String> = arrayOf("Medical", "Culinary", "Botanic")
    private var listOfPlants: List<Biljka> = getPlants()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var spinnerState: SpinnerState = SpinnerState.BOTANIC
        setContentView(R.layout.activity_main)
        val resetButton: Button = findViewById(R.id.resetBtn)
        plants = findViewById(R.id.biljkeRV)
        plants.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        plantsAdapter = PlantListAdapter(listOf(), spinnerState) {
            item -> plantsAdapter.filterPlants(item)
        }
        plants.adapter = plantsAdapter
        plantsAdapter.updatePlants(listOfPlants)
        spinner = findViewById(R.id.modSpinner)
        spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, states)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val newItem: String = states[p2]
                spinnerState = when (newItem) {
                    states[0] -> {
                        SpinnerState.MEDICAL
                    }
                    states[1] -> {
                        SpinnerState.CULINARY
                    }
                    states[2] -> {
                        SpinnerState.BOTANIC
                    }
                    else -> {
                        throw Exception("Invalid state")
                    }
                }
                plantsAdapter.changeSpinnerState(spinnerState)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                spinner.setSelection(0)
                spinnerState = SpinnerState.MEDICAL
            }
        }
        resetButton.setOnClickListener {
            spinner.setSelection(0)
            plantsAdapter.resetPlants()
        }
    }

}