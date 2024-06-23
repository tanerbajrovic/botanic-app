package ba.unsa.etf.rma.tanerbajrovic.models

import android.graphics.Bitmap
import ba.unsa.etf.rma.tanerbajrovic.models.Biljka

interface PlantDAO {

    /**
     * Gets image as Bitmap for a given plant.
     */
    suspend fun getImage(plant: Biljka): Bitmap

    /**
     * Fixes incorrect plant's data.
     */
    suspend fun fixData(plant: Biljka): Biljka

    /**
     * Gets all the plants with the given flower color.
     */
    suspend fun getPlantsWithFlowerColor(flowerColor: String, substring: String): List<Biljka>

}