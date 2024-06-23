package ba.unsa.etf.rma.tanerbajrovic.viewmodels

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class BiljkaViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: BiljkaRepository
    private val trefleDAO = TrefleDAO()

    init {
        val biljkaDAO = BiljkaDatabase.getDatabase(application).biljkaDAO()
        repository = BiljkaRepository(biljkaDAO)
//        trefleDAO.setContext(application.applicationContext)
    }

    fun saveBiljka(plant: Biljka) {
        viewModelScope.launch {
            repository.saveBiljka(plant)
        }
    }

    fun insertBiljka(plant: Biljka) {
        return runBlocking {
            repository.insertBiljka(plant)
        }
    }

    fun getAllBiljkas(): List<Biljka> {
        return runBlocking {
            repository.getAllBiljkas()
        }
    }

//
//    fun getImage(plant: Biljka): Bitmap {
//        return runBlocking {
//            withContext(Dispatchers.IO) {
//                repository.getImage(plant)
//            }
//        }
//    }
//
//    fun fixPlantData(plant: Biljka): Biljka {
//        return runBlocking {
//            withContext(Dispatchers.IO) {
//                trefleDAO.fixData(plant)
//            }
//        }
//    }

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

        return Biljka(
            plantName!!,
            plantFamily!!,
            plantWarning!!,
            medicalRemedies,
            getTasteProfileFromString(tasteProfile!!)!!,
            dishes!!.toList(),
            climateTypes,
            soilTypes
        )
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
