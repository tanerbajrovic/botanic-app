package ba.unsa.etf.rma.tanerbajrovic.models

import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BiljkaRepository(
    private val biljkaDAO: BiljkaDAO,
    private val trefleDAO: TrefleDAO
) {

    // Database

    suspend fun saveBiljka(plant: Biljka): Boolean {
        return withContext(Dispatchers.IO) {
            biljkaDAO.saveBiljka(plant)
        }
    }

    suspend fun insertBiljka(plant: Biljka): Long {
        return withContext(Dispatchers.IO) {
            biljkaDAO.insertBiljka(plant)
        }
    }

    suspend fun getBiljka(plantId: Long): Biljka? {
        return withContext(Dispatchers.IO) {
            biljkaDAO.getBiljka(plantId)
        }
    }

    suspend fun getBiljkaByName(name: String): Biljka? {
        return withContext(Dispatchers.IO) {
            biljkaDAO.getBiljkaByName(name)
        }
    }

    suspend fun getAllBiljkas(): List<Biljka> {
        return withContext(Dispatchers.IO) {
            biljkaDAO.getAllBiljkas()
        }
    }

    suspend fun addImage(plantId: Long, bitmap: Bitmap): Boolean {
        return withContext(Dispatchers.IO) {
            biljkaDAO.addImage(plantId, bitmap)
        }
    }

    suspend fun getBitmapByPlantId(plantId: Long): Bitmap? {
        return withContext(Dispatchers.IO) {
            biljkaDAO.getBitmapByBiljkaId(plantId)
        }
    }

    suspend fun clearBiljkas() {
        return withContext(Dispatchers.IO) {
            biljkaDAO.clearBiljkas()
        }
    }

    suspend fun clearBiljkaBitmaps() {
        return withContext(Dispatchers.IO) {
            biljkaDAO.clearBiljkaBitmaps()
        }
    }

//    suspend fun addImage(plantId: Long, bitmap: Bitmap) {
//        val isSuccessful = biljkaDAO.addImage(plantId, bitmap)
//    }

    // API

    suspend fun getImage(plant: Biljka): Bitmap {
        return withContext(Dispatchers.IO) {
            trefleDAO.getImage(plant)
        }
    }
//
//    suspend fun fixPlantData(plant: Biljka): Biljka {
//        return trefleDAO.fixData(plant)
//    }

}