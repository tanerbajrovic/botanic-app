package ba.unsa.etf.rma.tanerbajrovic

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// TODO
// - Indicate when selected in ListView
// - Add/Modify button functionality for dishes
// - Validate selection in TasteProfile

class NovaBiljkaActivity : AppCompatActivity() {

    private var medicalRemediesList: MutableList<String> = mutableListOf()
    private var tasteProfilesList: MutableList<String> = mutableListOf()
    private var climateTypesList: MutableList<String> = mutableListOf()
    private var soilTypesList: MutableList<String> = mutableListOf()
    private var dishesList: MutableList<String> = mutableListOf()

    private lateinit var remediesAdapter: ArrayAdapter<String>
    private lateinit var tasteProfilesAdapter: ArrayAdapter<String>
    private lateinit var soilTypesAdapter: ArrayAdapter<String>
    private lateinit var dishesAdapter: ArrayAdapter<String>
    private lateinit var climateTypesAdapter: ArrayAdapter<String>

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
        configureDishes()
        configureMedicalRemedies()
        configureSoilTypes()
        configureTasteProfiles()
        configureClimateTypes()
        addPlantButton.setOnClickListener {
            processPlantForm()
            // Return to MainActivity
        }
        dishButton.setOnClickListener {
            processDishInput()
            dishesAdapter.notifyDataSetChanged()
        }
    }

    private fun configureMedicalRemedies() {
        for (medicalRemedy: MedicinskaKorist in MedicinskaKorist.entries)
            medicalRemediesList.add(medicalRemedy.opis)
        remediesAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, medicalRemediesList)
        medicalRemedies.adapter = remediesAdapter
    }

    private fun configureTasteProfiles() {
        for (tasteProfile: ProfilOkusaBiljke in ProfilOkusaBiljke.entries)
            tasteProfilesList.add(tasteProfile.opis)
        tasteProfilesAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, tasteProfilesList)
        tasteProfiles.adapter = tasteProfilesAdapter
    }

    private fun configureDishes() {
        dishesAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, dishesList)
        dishes.adapter = dishesAdapter
    }

    private fun configureSoilTypes() {
        for (soilType: Zemljiste in Zemljiste.entries)
            soilTypesList.add(soilType.naziv)
        soilTypesAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, soilTypesList)
        soilTypes.adapter = soilTypesAdapter
    }

    private fun configureClimateTypes() {
        for (climateType: KlimatskiTip in KlimatskiTip.entries)
            climateTypesList.add(climateType.opis)
        climateTypesAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, climateTypesList)
        climateTypes.adapter = climateTypesAdapter
    }

    // Validates and processes the whole form
    private fun processPlantForm() {

        val invalidTextMessage = getString(R.string.invalid_text_input_message)

        if (isInvalidText(plantName)) {
            plantName.error = invalidTextMessage
        }

        if (isInvalidText(plantFamily)) {
            plantFamily.error = invalidTextMessage
        }

        if (isInvalidText(plantWarning)) {
            plantWarning.error = invalidTextMessage
        }

        if (isInvalidDishesList()) {
            val invalidDishesMessage = getString(R.string.invalid_number_of_dishes_message)
            val toast = Toast.makeText(this, invalidDishesMessage, Toast.LENGTH_SHORT)
            toast.show()
        }

    }

    // Validates and processes adding new dishes
    private fun processDishInput() {
        if (isInvalidText(dish)) {
            dish.error = getString(R.string.invalid_text_input_message)
        }
        else if (isInvalidDish(dish)) {
            dish.error = getString(R.string.invalid_dish_duplicate_message)
        }
        else {
            val dishName = dish.text.toString()
            dishesList.add(dishName)
            dish.text.clear()
        }
    }

    private fun isInvalidText(editText: EditText): Boolean {
        return (editText.text.length < 2 || editText.text.length > 20)
    }

    private fun isInvalidDish(dish: EditText): Boolean {
        val dishName = dish.text.toString().uppercase()
        for (currentDish: String in dishesList) {
            if (currentDish.uppercase() == dishName)
                return true
        }
        return false
    }

    private fun isInvalidDishesList(): Boolean {
        return dishesList.isEmpty()
    }

}