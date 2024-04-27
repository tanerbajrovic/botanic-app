package ba.unsa.etf.rma.tanerbajrovic

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class NovaBiljkaActivity : AppCompatActivity() {

    private var medicalRemediesList: MutableList<String> = mutableListOf()
    private var tasteProfilesList: MutableList<String> = mutableListOf()
    private var climateTypesList: MutableList<String> = mutableListOf()
    private var soilTypesList: MutableList<String> = mutableListOf()
    private var dishesList: MutableList<String> = mutableListOf()

    private lateinit var addPlantButton: Button
    private lateinit var plantName: EditText
    private lateinit var plantFamily: EditText
    private lateinit var plantWarning: EditText
    private lateinit var dish: EditText
    private lateinit var dishButton: Button
    private lateinit var dishes: ListView
    private lateinit var medicalRemedies: ListView
    private lateinit var tasteProfiles: ListView
    private lateinit var soilTypes: ListView
    private lateinit var climateTypes: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_plant)
        populateTasteProfiles()
        populateSoilTypes()
        populateClimateTypes()
        populateMedicalRemedies()
        plantName = findViewById(R.id.nazivET)
        plantFamily = findViewById(R.id.porodicaET)
        plantWarning = findViewById(R.id.medicinskoUpozorenjeET)
        dish = findViewById(R.id.jeloET)
        dishButton = findViewById(R.id.dodajJeloBtn)
        dishes = findViewById(R.id.jelaLV)
        medicalRemedies = findViewById(R.id.medicinskaKoristLV)
        tasteProfiles = findViewById(R.id.profilOkusaLV)
        soilTypes = findViewById(R.id.zemljisniTipLV)
        climateTypes = findViewById(R.id.klimatskiTipLV)
        addPlantButton= findViewById(R.id.dodajBiljkuBtn)
        val remediesAdapter: ArrayAdapter<String> = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, medicalRemediesList)
        val tasteProfileAdapter: ArrayAdapter<String> = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, tasteProfilesList)
        val soilTypeAdapter: ArrayAdapter<String> = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, soilTypesList)
        val dishesAdapter: ArrayAdapter<String> = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, dishesList)
        val climateTypesAdapter: ArrayAdapter<String> = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, climateTypesList)
        medicalRemedies.adapter = remediesAdapter
        tasteProfiles.adapter = tasteProfileAdapter
        soilTypes.adapter = soilTypeAdapter
        dishes.adapter = dishesAdapter
        climateTypes.adapter = climateTypesAdapter
        addPlantButton.setOnClickListener {
            checkFormValidation()
        }
        dishButton.setOnClickListener {
            checkDishValidation()
            dishesAdapter.notifyDataSetChanged()
        }
        remediesAdapter.notifyDataSetChanged()
        tasteProfileAdapter.notifyDataSetChanged()
        soilTypeAdapter.notifyDataSetChanged()
        dishesAdapter.notifyDataSetChanged()
        climateTypesAdapter.notifyDataSetChanged()
    }

    private fun populateMedicalRemedies() {
        val medicalRemediesRaw: Array<MedicinskaKorist> = MedicinskaKorist.entries.toTypedArray()
        for (medicalRemedy: MedicinskaKorist in medicalRemediesRaw) {
            medicalRemediesList.add(medicalRemedy.opis)
        }
    }

    private fun populateTasteProfiles() {
        val tasteProfilesRaw: Array<ProfilOkusaBiljke> = ProfilOkusaBiljke.entries.toTypedArray()
        for (tasteProfile: ProfilOkusaBiljke in tasteProfilesRaw) {
            tasteProfilesList.add(tasteProfile.opis)
        }
    }

    private fun populateSoilTypes() {
        val soilTypesRaw: Array<Zemljiste> = Zemljiste.entries.toTypedArray()
        for (soilType: Zemljiste in soilTypesRaw) {
            soilTypesList.add(soilType.naziv)
        }
    }

    private fun populateClimateTypes() {
        val climateTypesRaw: Array<KlimatskiTip> = KlimatskiTip.entries.toTypedArray()
        for (climateType: KlimatskiTip in climateTypesRaw) {
            climateTypesList.add(climateType.opis)
        }
    }

// Try to make this generic
//    private fun populateDetails(list: MutableList<String>, enum: Enum<*>) {
//        val rawData = enum
//
//    }

    // Checks validation of the whole form
    private fun checkFormValidation() {

        val invalidTextMessage= "Neispravan broj karaktera (3 - 19)" // ! Use @string for this.

        // Checking EditTexts
        if (isInvalidText(plantName)) {
            plantName.error = invalidTextMessage
        }

        if (isInvalidText(plantFamily)) {
            plantFamily.error = invalidTextMessage
        }

        if (isInvalidText(plantWarning)) {
            plantWarning.error = invalidTextMessage
        }

    }

    // Checks validity of EditText content
    private fun isInvalidText(editText: EditText): Boolean {
        return (editText.text.length <= 2 || editText.text.length >= 20)
    }

    private fun checkDishValidation() {
        if (isInvalidText(dish)) {
            dish.error = "Neispravan broj karaktera (3 - 19)"
        }
        else {
            val currentDish: String = dish.text.toString()
            if (currentDish in dishesList) {
                dish.error = "Jelo je već u listi"
            }
            else {
                // TODO
                dishesList.add(currentDish)
            }
        }
    }

}