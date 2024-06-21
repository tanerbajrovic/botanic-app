package ba.unsa.etf.rma.tanerbajrovic

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var plants: RecyclerView
    private lateinit var plantsAdapter: PlantListAdapter
    private lateinit var modeSpinner: Spinner
    private lateinit var modeSpinnerAdapter: ArrayAdapter<String>
    private val states: Array<String> = arrayOf(
        SpinnerState.MEDICAL.description,
        SpinnerState.BOTANIC.description,
        SpinnerState.CULINARY.description)
    private var listOfPlants: MutableList<Biljka> = getPlants()
    private var spinnerState: SpinnerState = SpinnerState.MEDICAL
    private lateinit var quickSearchButton: Button
    private lateinit var quickSearchText: EditText
    private lateinit var colorSpinner: Spinner
    private lateinit var colorSpinnerAdapter: ArrayAdapter<String>
    private val colors: Array<String> = arrayOf(
        "Red",
        "Blue",
        "Yellow",
        "Orange",
        "Purple",
        "Brown",
        "Green")

    companion object {
        private const val NEW_PLANT_REQUEST_CODE = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val resetButton: Button = findViewById(R.id.resetBtn)
        val newPlantButton: Button = findViewById(R.id.novaBiljkaBtn)
        plants = findViewById(R.id.biljkeRV)
        modeSpinner = findViewById(R.id.modSpinner)
        quickSearchText = findViewById(R.id.pretragaET)
        quickSearchButton = findViewById(R.id.brzaPretraga)
        colorSpinner = findViewById(R.id.bojaSPIN)
        configureRecyclerView()
        configureModeSpinner()
        configureColorSpinner()
        resetButton.setOnClickListener {
            modeSpinner.setSelection(0)
            plantsAdapter.resetPlants()
        }
        quickSearchButton.setOnClickListener {
            // Does some stuff
            TODO()
        }
        newPlantButton.setOnClickListener {
            showNewPlantActivity()
        }
    }

    private fun hideQuickSearchViews() {
        makeInvisible(quickSearchText)
        makeInvisible(quickSearchButton)
        makeInvisible(colorSpinner)
    }

    private fun showQuickSearchViews() {
        makeVisible(quickSearchText)
        makeVisible(quickSearchButton)
        makeVisible(colorSpinner)
    }

    private fun makeInvisible(view: View) {
        view.visibility = View.INVISIBLE
    }

    private fun makeVisible(view: View) {
        view.visibility = View.VISIBLE
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
    private fun configureModeSpinner() {
        modeSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, states)
        modeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        modeSpinner.adapter = modeSpinnerAdapter
        attachModeSpinnerOnItemListener()
    }

    private fun configureColorSpinner() {
        colorSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, colors)
        colorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        colorSpinner.adapter = colorSpinnerAdapter
    }

    // Implements on item click logic for changing spinner selection
    private fun attachModeSpinnerOnItemListener() {
        modeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                if (spinnerState == SpinnerState.BOTANIC)
                    showQuickSearchViews()
                else
                    hideQuickSearchViews()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                modeSpinner.setSelection(0)
                spinnerState = SpinnerState.MEDICAL
                hideQuickSearchViews()
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

                var newPlant = Biljka(
                    plantName!!,
                    plantFamily!!,
                    plantWarning!!,
                    medicalRemedies,
                    getTasteProfileFromString(tasteProfile!!)!!,
                    dishes!!.toList(),
                    climateTypes,
                    soilTypes
                )

                // Fixing the incorrect values
//                val scope = CoroutineScope(Job() + Dispatchers.IO)
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        val trefleDAO = TrefleDAO()
                        trefleDAO.setContext(applicationContext)
                        newPlant = trefleDAO.fixData(newPlant)
                        withContext(Dispatchers.Main) {
                            listOfPlants.add(newPlant)
                            plantsAdapter.updatePlants(listOfPlants)
                            modeSpinner.setSelection(0)
                        }
                    }
                }
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