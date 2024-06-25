package ba.unsa.etf.rma.tanerbajrovic.viewmodels

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ba.unsa.etf.rma.tanerbajrovic.models.Biljka
import ba.unsa.etf.rma.tanerbajrovic.models.BiljkaDatabase
import ba.unsa.etf.rma.tanerbajrovic.models.BiljkaRepository
import ba.unsa.etf.rma.tanerbajrovic.models.KlimatskiTip
import ba.unsa.etf.rma.tanerbajrovic.models.MedicinskaKorist
import ba.unsa.etf.rma.tanerbajrovic.models.ProfilOkusaBiljke
import ba.unsa.etf.rma.tanerbajrovic.models.TrefleDAO
import ba.unsa.etf.rma.tanerbajrovic.models.Zemljiste
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BiljkaViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: BiljkaRepository
    private val trefleDAO = TrefleDAO()

    init {
        val biljkaDAO = BiljkaDatabase.getDatabase(application).biljkaDao()
        trefleDAO.setContext(application.applicationContext)
        repository = BiljkaRepository(biljkaDAO, trefleDAO)
    }

    fun saveBiljka(plant: Biljka): Boolean {
        val deferredBoolean = viewModelScope.async {
            repository.saveBiljka(plant)
        }
        return runBlocking {
            deferredBoolean.await()
        }
    }

    fun addImage(plantId: Long, bitmap: Bitmap) : Boolean {
        val deferredBoolean = viewModelScope.async {
            repository.addImage(plantId, bitmap)
        }
        return runBlocking {
            deferredBoolean.await()
        }
    }

    fun getAllBiljkas(): List<Biljka> {
        val deferredList = viewModelScope.async {
            repository.getAllBiljkas()
        }
        return runBlocking {
            deferredList.await()
        }
    }

    fun getBiljkaByName(name: String): Biljka? {
        val deferredPlant = viewModelScope.async {
            repository.getBiljkaByName(name)
        }
        return runBlocking {
            deferredPlant.await()
        }
    }

    fun getBiljka(plantId: Long): Biljka? {
        val deferredPlant = viewModelScope.async {
            repository.getBiljka(plantId)
        }
        return runBlocking {
            deferredPlant.await()
        }
    }

    // Firstly check the DB, then go to API.
    fun getImage(plant: Biljka): Bitmap {
        val deferredBitmap = viewModelScope.async {
            val actualPlant = repository.getBiljkaByName(plant.naziv)
            var bitmap = repository.getBitmapByPlantId(actualPlant!!.id)
            if (bitmap == null) {
                bitmap = repository.getImage(plant)
                repository.addImage(actualPlant.id, bitmap)
            }
            return@async bitmap
        }
        return runBlocking {
            deferredBitmap.await()
        }
    }

    fun fixPlantData(plant: Biljka): Biljka {
        val deferredPlant = viewModelScope.async {
            trefleDAO.fixData(plant)
        }
        return runBlocking {
            val actualPlant = deferredPlant.await()
            actualPlant.onlineChecked = true
            return@runBlocking actualPlant
        }
    }

    fun getPlantFromIntentData(data: Intent): Biljka {

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
            medicalRemedies.add(MedicinskaKorist.getMedicalRemedyFromDescription(remedy)!!)
        }

        val climateTypes: MutableList<KlimatskiTip> = mutableListOf()
        for (climate: String in plantClimateTypes!!) {
            climateTypes.add(KlimatskiTip.getClimateTypeFromDescription(climate)!!)
        }

        val soilTypes: MutableList<Zemljiste> = mutableListOf()
        for (soil: String in plantSoilTypes!!) {
            soilTypes.add(Zemljiste.getSoilTypeFromDescription(soil)!!)
        }

        return Biljka(
            plantName!!,
            plantFamily!!,
            plantWarning!!,
            medicalRemedies,
            ProfilOkusaBiljke.getTasteProfileFromDescription(tasteProfile!!)!!,
            dishes!!.toList(),
            climateTypes,
            soilTypes
        )

    }

}
