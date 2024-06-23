package ba.unsa.etf.rma.tanerbajrovic

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.tanerbajrovic.models.MedicinskaKorist
import ba.unsa.etf.rma.tanerbajrovic.adapters.PlantListAdapter
import ba.unsa.etf.rma.tanerbajrovic.models.Biljka
import ba.unsa.etf.rma.tanerbajrovic.models.KlimatskiTip
import ba.unsa.etf.rma.tanerbajrovic.models.ProfilOkusaBiljke
import ba.unsa.etf.rma.tanerbajrovic.models.SpinnerState
import ba.unsa.etf.rma.tanerbajrovic.models.TrefleDAO
import ba.unsa.etf.rma.tanerbajrovic.models.Zemljiste
import ba.unsa.etf.rma.tanerbajrovic.viewmodels.BiljkaViewModel
import ba.unsa.etf.rma.tanerbajrovic.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var plants: RecyclerView
    private lateinit var plantsAdapter: PlantListAdapter
    private lateinit var modeSpinner: Spinner
    private lateinit var modeSpinnerAdapter: ArrayAdapter<String>
    private lateinit var quickSearchButton: Button
    private lateinit var quickSearchText: EditText
    private lateinit var colorSpinner: Spinner
    private lateinit var colorSpinnerAdapter: ArrayAdapter<String>
    private lateinit var mainViewModel: MainViewModel
    private lateinit var biljkaViewModel: BiljkaViewModel

    companion object {
        private const val NEW_PLANT_REQUEST_CODE = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        biljkaViewModel = ViewModelProvider(this).get(BiljkaViewModel::class.java)
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
            mainViewModel.resetPlants()
            plantsAdapter.updatePlants(mainViewModel.filteredPlants)
            Log.d("FilteredPlants", mainViewModel.filteredPlants.toString())
        }
        quickSearchButton.setOnClickListener {
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

    // Configures RecyclerView with plants
    private fun configureRecyclerView() {
        plants.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        plantsAdapter = PlantListAdapter(mainViewModel.plants, mainViewModel.spinnerState) {
            mainViewModel.filterPlants(it)
            plantsAdapter.updatePlants(mainViewModel.filteredPlants)
            Log.d("FilteredPlants", mainViewModel.filteredPlants.toString())
        }
        plants.adapter = plantsAdapter
        plantsAdapter.updatePlants(mainViewModel.filteredPlants)
    }

    // Configures mode selection spinner
    private fun configureModeSpinner() {
        modeSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mainViewModel.modes)
        modeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        modeSpinner.adapter = modeSpinnerAdapter
        attachModeSpinnerOnItemListener()
    }

    // Configures color spinner
    private fun configureColorSpinner() {
        colorSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mainViewModel.colors)
        colorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        colorSpinner.adapter = colorSpinnerAdapter
    }

    // Implements on item click logic for changing spinner selection
    private fun attachModeSpinnerOnItemListener() {
        modeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val newItem: String = mainViewModel.modes[p2]
                mainViewModel.spinnerState = when (newItem) {
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
                plantsAdapter.changeSpinnerState(mainViewModel.spinnerState)
                if (mainViewModel.spinnerState == SpinnerState.BOTANIC)
                    showQuickSearchViews()
                else
                    hideQuickSearchViews()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                modeSpinner.setSelection(0)
                mainViewModel.spinnerState = SpinnerState.MEDICAL
                plantsAdapter.changeSpinnerState(mainViewModel.spinnerState)
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
                // Fixing the incorrect values
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        val trefleDAO = TrefleDAO()
                        val newPlant = biljkaViewModel.getPlantFromIntentData(data)
                        val fixedNewPlant = trefleDAO.fixData(newPlant)
                        biljkaViewModel.insertBiljka(fixedNewPlant)
                        mainViewModel.plants.add(fixedNewPlant)
                        mainViewModel.spinnerState = SpinnerState.MEDICAL
                       withContext(Dispatchers.Main) {
                           plantsAdapter.updatePlants(mainViewModel.plants)
                           plantsAdapter.changeSpinnerState(mainViewModel.spinnerState)
                           modeSpinner.setSelection(0)
                       }
                    }
                }
            }
        }
    }

}