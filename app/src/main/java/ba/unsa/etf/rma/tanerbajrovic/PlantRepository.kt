package ba.unsa.etf.rma.tanerbajrovic

import android.content.Context
import android.graphics.Bitmap

class PlantRepository(private val trefleDAO: TrefleDAO, private val context: Context) {

    init {
        trefleDAO.setContext(context)
    }

    suspend fun getImage(plant: Biljka): Bitmap {
        return trefleDAO.getImage(plant)
    }

    suspend fun fixPlantData(plant: Biljka): Biljka {
        return trefleDAO.fixData(plant)
    }

    suspend fun getPlantsWithFlowerColor(flowerColor: String, substring: String): List<Biljka> {
        TODO()
    }


}