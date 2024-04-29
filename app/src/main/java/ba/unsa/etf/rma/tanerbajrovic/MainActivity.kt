package ba.unsa.etf.rma.tanerbajrovic

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
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
    private var listOfPlants: MutableList<Biljka> = getPlants()
    private var spinnerState: SpinnerState = SpinnerState.MEDICAL

    companion object {
        private const val NEW_PLANT_REQUEST_CODE = 3
    }

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
            showNewPlantActivity()
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

    private fun showNewPlantActivity() {
        val intent = Intent(this, NovaBiljkaActivity::class.java)
        startActivityForResult(intent, NEW_PLANT_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_PLANT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val plantName: String? = data.extras?.getString("name")
                val plantFamily: String? = data.extras?.getString("family")
                val plantWarning: String? = data.extras?.getString("medical_warning")
                val dishes: ArrayList<String>? = data.extras?.getStringArrayList("dishes")
                val plantRemedies: ArrayList<String>? = data.extras?.getStringArrayList("medical_remedies")
                val plantClimateTypes: ArrayList<String>? = data.extras?.getStringArrayList("climate_types")
                val plantSoilTypes: ArrayList<String>? = data.extras?.getStringArrayList("soil_types")
                val tasteProfile: String? = data.extras?.getString("taste_profile")

                // Converting Strings to respective Enums
                val medicalRemedies: MutableList<MedicinskaKorist> = mutableListOf()
                for (remedy: String in plantRemedies!!) {
                    MedicinskaKorist.entries.find {
                        it.opis == remedy
                    }.let { medicalRemedies.add(it!!) }
                }

                val climateTypes: MutableList<KlimatskiTip> = mutableListOf()
                for (climate: String in plantClimateTypes!!) {
                    KlimatskiTip.entries.find {
                        it.opis == climate
                    }.let { climateTypes.add(it!!) }
                }

                val soilTypes: MutableList<Zemljiste> = mutableListOf()
                for (soil: String in plantSoilTypes!!) {
                    Zemljiste.entries.find {
                        it.naziv == soil
                    }.let { soilTypes.add(it!!) }
                }

                val newPlant = Biljka(
                    plantName!!,
                    plantFamily!!,
                    plantWarning!!,
                    medicalRemedies,
                    getTasteProfileFromString(tasteProfile!!)!!,
                    dishes!!.toList(),
                    climateTypes,
                    soilTypes
                )
                listOfPlants.add(newPlant)
                plantsAdapter.updatePlants(listOfPlants)
                spinner.setSelection(0)
            }
        }
    }

    private fun getTasteProfileFromString(description: String): ProfilOkusaBiljke? {
        for (value: ProfilOkusaBiljke in ProfilOkusaBiljke.entries) {
            if (value.opis == description) {
                return value
            }
        }
        return null
    }

}