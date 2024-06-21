package ba.unsa.etf.rma.tanerbajrovic

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.SparseBooleanArray
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

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
    private lateinit var addDishButton: Button
    private lateinit var dishes: ListView
    private lateinit var medicalRemedies: ListView
    private lateinit var tasteProfiles: ListView
    private lateinit var soilTypes: ListView
    private lateinit var climateTypes: ListView

    private var previousDish: String? = null
    private var isInEditMode: Boolean = false
    private val validator: Validator = Validator()

    inner class Validator {

        fun isValidText(editText: EditText): Boolean {
            return (editText.text.length in 2..40)
        }

        // TODO: Check if it's an actual Latin name via API.
        fun doesContainLatinName(editText: EditText): Boolean {
            val text: String = editText.text.toString()
            return text.contains("(") && text.contains(")")
                    && text.substringAfter("(").substringBefore(")").isNotEmpty()
        }

        fun isValidDish(dish: EditText): Boolean {
            val dishName = dish.text.toString().uppercase()
            for (currentDish: String? in dishesList) {
                if (currentDish!!.uppercase() == dishName)
                    return false
            }
            return true
        }

        fun isValidDishList(): Boolean {
            return dishesList.isNotEmpty()
        }

        fun isValidList(listview: ListView): Boolean {
            return listview.checkedItemCount > 0
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
        addDishButton = findViewById(R.id.dodajJeloBtn)
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
        }
        addDishButton.setOnClickListener {
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

        var isInvalid = false
        val invalidTextMessage = getString(R.string.invalid_text_input_message)
        val invalidLatinNameMessage = getString(R.string.invalid_latin_plant_name)

        if (!validator.isValidText(plantName)) {
            plantName.error = invalidTextMessage
            isInvalid = true
        } else {
            plantName.error = null
        }

        if (!validator.doesContainLatinName(plantName)) {
            if (plantName.error == null)
                plantName.error = invalidLatinNameMessage
            isInvalid = true
            // Need to improve validation here. Use API.
        }

        if (!validator.isValidText(plantFamily)) {
            plantFamily.error = invalidTextMessage
            isInvalid = true
        } else {
            plantFamily.error = null
        }

        if (!validator.isValidText(plantWarning)) {
            plantWarning.error = invalidTextMessage
            isInvalid = true
        } else {
            plantWarning.error = null
        }

        if (!validator.isValidDishList()) {
            val invalidDishesMessage = getString(R.string.invalid_number_of_dishes_message)
            val toast = Toast.makeText(this, invalidDishesMessage, Toast.LENGTH_SHORT)
            toast.show()
            isInvalid = true
        }

        // Additional validation stuff

        if (!validator.isValidList(medicalRemedies)) {
            val invalidMedicalRemediesMessage = getString(R.string.insufficient_number_of_remedies)
            val toast = Toast.makeText(this, invalidMedicalRemediesMessage, Toast.LENGTH_SHORT)
            toast.show()
            isInvalid = true
        }

        if (!validator.isValidList(climateTypes)) {
            val invalidClimateTypesMessage = getString(R.string.insufficient_number_of_climate)
            val toast = Toast.makeText(this, invalidClimateTypesMessage, Toast.LENGTH_SHORT)
            toast.show()
            isInvalid = true
        }

        if (!validator.isValidList(soilTypes)) {
            val invalidSoilTypesMessage = getString(R.string.insufficient_number_of_soils)
            val toast = Toast.makeText(this, invalidSoilTypesMessage, Toast.LENGTH_SHORT)
            toast.show()
            isInvalid = true
        }

        if (!validator.isValidList(tasteProfiles)) {
            val invalidTasteProfilesMessage = getString(R.string.insufficient_number_of_taste_profile)
            val toast = Toast.makeText(this, invalidTasteProfilesMessage, Toast.LENGTH_SHORT)
            toast.show()
            isInvalid = true
        }

        if (!isInvalid) {

            val plantMedicalRemedies: MutableList<String> = mutableListOf()
            val checkedRemedies: SparseBooleanArray = medicalRemedies.checkedItemPositions
            for (i in 0 until medicalRemedies.adapter.count) {
                if (checkedRemedies.get(i)) {
                     plantMedicalRemedies.add(medicalRemediesList[i])
                }
            }

            val plantClimateTypes: MutableList<String> = mutableListOf()
            val checkedClimateTypes: SparseBooleanArray = climateTypes.checkedItemPositions
            for (i in 0 until climateTypes.adapter.count) {
                if (checkedClimateTypes.get(i)) {
                    plantClimateTypes.add(climateTypesList[i])
                }
            }

            val plantSoilTypes: MutableList<String> = mutableListOf()
            val checkedSoilTypes: SparseBooleanArray = soilTypes.checkedItemPositions
            for (i in 0 until soilTypes.adapter.count) {
                if (checkedSoilTypes.get(i)) {
                    plantSoilTypes.add(soilTypesList[i])
                }
            }

            val plantTasteProfile: String = tasteProfilesList[tasteProfiles.checkedItemPosition]

            val intent = Intent()
            intent.putStringArrayListExtra("medical_remedies", ArrayList(plantMedicalRemedies))
            intent.putStringArrayListExtra("soil_types", ArrayList(plantSoilTypes))
            intent.putStringArrayListExtra("climate_types", ArrayList(plantClimateTypes))
            intent.putStringArrayListExtra("dishes", ArrayList(dishesList))
            intent.putExtra("taste_profile", plantTasteProfile)
            intent.putExtra("name", plantName.text.toString())
            intent.putExtra("family", plantFamily.text.toString())
            intent.putExtra("medical_warning", plantWarning.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    /**
     * Validates and processes adding new dishes
      */
    private fun processDishInput() {
        if (isInEditMode) {
            if (dish.text.isEmpty()) {
                dishesList.remove(previousDish)
                addDishButton.text = getString(R.string.add_dish)
                dish.text.clear()
                isInEditMode = false
                previousDish = null
            }
            else if (validator.isValidText(dish)) {
                for (i in dishesList.indices) {
                    if (dishesList[i] == previousDish) {
                        dishesList[i] = dish.text.toString()
                        break
                    }
                }
                addDishButton.text = getString(R.string.add_dish)
                dish.text.clear()
                isInEditMode = false
                previousDish = null
            }
            else {
                dish.error = getString(R.string.invalid_text_input_message)
            }
        }
        else if (!validator.isValidText(dish)) {
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

    /**
     * Configure ListView and adapter
     */
    private fun configureMedicalRemedies() {
        for (medicalRemedy: MedicinskaKorist in MedicinskaKorist.entries)
            medicalRemediesList.add(medicalRemedy.opis)
        remediesAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_multiple_choice, medicalRemediesList)
        medicalRemedies.adapter = remediesAdapter
    }

    /**
     * Configure ListView and adapter
     */
    private fun configureTasteProfiles() {
        for (tasteProfile: ProfilOkusaBiljke in ProfilOkusaBiljke.entries)
            tasteProfilesList.add(tasteProfile.opis)
        tasteProfiles.choiceMode = ListView.CHOICE_MODE_SINGLE
        tasteProfilesAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_single_choice, tasteProfilesList)
        tasteProfiles.adapter = tasteProfilesAdapter
    }

    /**
     * Configure ListView and adapter
     */
    private fun configureDishes() {
        dishesAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, dishesList)
        dishes.adapter = dishesAdapter
        dishes.onItemClickListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                isInEditMode = true
                val currentDish: String = dishesList[p2]
                val editable: Editable = Editable.Factory.getInstance().newEditable(currentDish)
                dish.text = editable
                addDishButton.text = getText(R.string.edit_dish)
                previousDish = currentDish
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                isInEditMode = false
                previousDish = null
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                onItemSelected(p0, p1, p2, p3)
            }
        }
    }

    /**
     * Configure ListView and adapter
     */
    private fun configureSoilTypes() {
        for (soilType: Zemljiste in Zemljiste.entries)
            soilTypesList.add(soilType.naziv)
        soilTypesAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_multiple_choice, soilTypesList)
        soilTypes.adapter = soilTypesAdapter
    }

    /**
     * Configure ListView and adapter
     */
    private fun configureClimateTypes() {
        for (climateType: KlimatskiTip in KlimatskiTip.entries)
            climateTypesList.add(climateType.opis)
        climateTypesAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_multiple_choice, climateTypesList)
        climateTypes.adapter = climateTypesAdapter
    }

}