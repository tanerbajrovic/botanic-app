package ba.unsa.etf.rma.tanerbajrovic.models

import android.graphics.Bitmap

class BiljkaRepository(
    private val biljkaDAO: BiljkaDAO
) {

    // Database

    suspend fun saveBiljka(plant: Biljka): Boolean {
        return biljkaDAO.saveBiljka(plant)
    }

    suspend fun insertBiljka(plant: Biljka) {
        biljkaDAO.insertBiljka(plant)
    }

    suspend fun getAllBiljkas(): List<Biljka> {
        return biljkaDAO.getAllBiljkas()
    }

    suspend fun clearBiljkas() {
        biljkaDAO.clearBiljkas()
    }

    suspend fun clearBiljkaBitmaps() {
        biljkaDAO.clearBiljkaBitmaps()
    }

//    suspend fun addImage(plantId: Long, bitmap: Bitmap) {
//        val isSuccessful = biljkaDAO.addImage(plantId, bitmap)
//    }

    // API

//    suspend fun getImage(plant: Biljka): Bitmap {
//        return trefleDAO.getImage(plant)
//    }
//
//    suspend fun fixPlantData(plant: Biljka): Biljka {
//        return trefleDAO.fixData(plant)
//    }

}