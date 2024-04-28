package ba.unsa.etf.rma.tanerbajrovic

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

// TODO
// - Indicate when selected in ListView
// - Add/Modify button functionality for dishes
// - Validate selection in TasteProfile

// TODO Improvements:
// - Use one configuration method for every ListView and Adapter

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
    private lateinit var capturePlantButton: Button
    private lateinit var plantImage: ImageView
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

    private val validator: Validator = Validator()

    inner class Validator {

        fun isValidText(editText: EditText): Boolean {
            return (editText.text.length in 2..20)
        }

        fun isValidDish(dish: EditText): Boolean {
            val dishName = dish.text.toString().uppercase()
            for (currentDish: String in dishesList) {
                if (currentDish.uppercase() == dishName)
                    return false
            }
            return true
        }

        fun isValidDishList(): Boolean {
            return dishesList.isNotEmpty()
        }

        fun hasRequiredPermissions(): Boolean {
            val cameraPermissionStatus: Int = ContextCompat.checkSelfPermission(
                this@NovaBiljkaActivity,
                android.Manifest.permission.CAMERA)
            return cameraPermissionStatus == PackageManager.PERMISSION_GRANTED
        }


    }

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_plant)
        plantName = findViewById(R.id.nazivET)
        plantFamily = findViewById(R.id.porodicaET)
        plantWarning = findViewById(R.id.medicinskoUpozorenjeET)
        plantImage = findViewById(R.id.slikaIV)
        capturePlantButton = findViewById(R.id.uslikajBiljkuBtn)
        dish = findViewById(R.id.jeloET)
        dishButton = findViewById(R.id.dodajJeloBtn)
        dishes = findViewById(R.id.jelaLV)
        medicalRemedies = findViewById(R.id.medicinskaKoristLV)
        tasteProfiles = findViewById(R.id.profilOkusaLV)
        soilTypes = findViewById(R.id.zemljisniTipLV)
        climateTypes = findViewById(R.id.klimatskiTipLV)
        addPlantButton = findViewById(R.id.dodajBiljkuBtn)
        configureDishes()
        configureMedicalRemedies()
        configureSoilTypes()
        configureTasteProfiles()
        configureClimateTypes()
        addPlantButton.setOnClickListener {
            processPlantForm()
            // Create Plant object and return to MainActivity
        }
        dishButton.setOnClickListener {
            processDishInput()
            dishesAdapter.notifyDataSetChanged()
        }
        capturePlantButton.setOnClickListener {
            // Checking permissions
            if (!validator.hasRequiredPermissions()) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
            else {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }
            else {
                val errorMessage = getString(R.string.insufficient_camera_permission_message)
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Assert that data is not null (!!)
            val imageThumbnail: Bitmap = data!!.extras!!.get("data") as Bitmap
            plantImage.setImageBitmap(imageThumbnail)
        }
    }

    /**
     * Validates and processes the whole form
     */
    private fun processPlantForm() {

        val invalidTextMessage = getString(R.string.invalid_text_input_message)

        if (!validator.isValidText(plantName)) {
            plantName.error = invalidTextMessage
        }

        if (!validator.isValidText(plantFamily)) {
            plantFamily.error = invalidTextMessage
        }

        if (!validator.isValidText(plantWarning)) {
            plantWarning.error = invalidTextMessage
        }

        if (!validator.isValidDishList()) {
            val invalidDishesMessage = getString(R.string.invalid_number_of_dishes_message)
            val toast = Toast.makeText(this, invalidDishesMessage, Toast.LENGTH_SHORT)
            toast.show()
        }

    }

    /**
     * Validates and processes adding new dishes
      */
    private fun processDishInput() {
        if (!validator.isValidText(dish)) {
            dish.error = getString(R.string.invalid_text_input_message)
        }
        else if (!validator.isValidDish(dish)) {
            dish.error = getString(R.string.invalid_dish_duplicate_message)
        }
        else {
            val dishName = dish.text.toString()
            dishesList.add(dishName)
            dish.text.clear()
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

}