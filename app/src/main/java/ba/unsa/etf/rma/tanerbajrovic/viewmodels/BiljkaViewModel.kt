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
        val biljkaDAO = BiljkaDatabase.getDatabase(application).biljkaDAO()
        trefleDAO.setContext(application.applicationContext)
        repository = BiljkaRepository(biljkaDAO, trefleDAO)
    }

    fun saveBiljka(plant: Biljka) {
        viewModelScope.launch {
            repository.saveBiljka(plant)
        }
    }

    fun insertImage(plantId: Long, bitmap: Bitmap) {
        viewModelScope.launch {
            repository.addImage(plantId, bitmap)
        }
    }

    fun insertBiljka(plant: Biljka) {
        return runBlocking {
            repository.insertBiljka(plant)
        }
    }

    fun getBiljka(plantId: Long): Biljka? {
        return runBlocking {
            repository.getBiljka(plantId)
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

    fun getImage(plant: Biljka): Bitmap {
        val deferredBitmap = viewModelScope.async {
            repository.getImage(plant)
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
            deferredPlant.await()
        }
    }

    fun populateDatabaseWithPlants(plants: List<Biljka>) {
        viewModelScope.launch {
            for (plant in plants)
                insertBiljka(plant)
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
