package ba.unsa.etf.rma.tanerbajrovic

import android.content.Intent
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
    private val states: Array<String> = arrayOf(
        SpinnerState.MEDICAL.description,
        SpinnerState.BOTANIC.description,
        SpinnerState.CULINARY.description)
    private var listOfPlants: List<Biljka> = getPlants()
    private var spinnerState: SpinnerState = SpinnerState.MEDICAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val resetButton: Button = findViewById(R.id.resetBtn)
        val newPlantButton: Button = findViewById(R.id.novaBiljkaBtn)
        plants = findViewById(R.id.biljkeRV)
        spinner = findViewById(R.id.modSpinner)
        configureRecyclerView()
        configureSpinner()
        resetButton.setOnClickListener {
            spinner.setSelection(0)
            plantsAdapter.resetPlants()
        }
        newPlantButton.setOnClickListener {
            openNewPlantActivity()
        }
    }

    // RecyclerView configuration
    private fun configureRecyclerView() {
        plants.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        plantsAdapter = PlantListAdapter(listOf(), spinnerState)
        plants.adapter = plantsAdapter
        plantsAdapter.updatePlants(listOfPlants)
    }

    // Spinner configuration
    private fun configureSpinner() {
        spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, states)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        attachSpinnerOnItemListener()
    }

    // Implements on item click logic for changing spinner selection
    private fun attachSpinnerOnItemListener() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val newItem: String = states[p2]
                spinnerState = when (newItem) {
                    SpinnerState.MEDICAL.description -> {
                        SpinnerState.MEDICAL
                    }
                    SpinnerState.CULINARY.description -> {
                        SpinnerState.CULINARY
                    }
                    SpinnerState.BOTANIC.description -> {
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
    }

    private fun openNewPlantActivity() {
        val intent = Intent(this, NovaBiljkaActivity::class.java)
        startActivity(intent)
    }

}