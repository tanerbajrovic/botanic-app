package ba.unsa.etf.rma.tanerbajrovic

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import ba.unsa.etf.rma.tanerbajrovic.api.RetrofitClient
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrefleDAO : PlantDAO {

    private lateinit var context: Context
    private lateinit var defaultBitmap: Bitmap

    /**
     * Gets image for the first plant in search results according to the latin name.
     */
    override suspend fun getImage(plant: Biljka): Bitmap {
        return withContext(Dispatchers.IO) {
            try {
                val latinName = plant.getLatinName()
                val response = RetrofitClient.trefleApiService.searchPlants(latinName)
                val responseBody = response.body()
                val plantBitmap = fetchPlantImage(responseBody!!.plants[0].imageURL) // Not good!! Need better error-handling.
                return@withContext plantBitmap ?: defaultBitmap
            } catch (e: Exception) {
                return@withContext defaultBitmap
            }
        }
    }

    /**
     * Makes a request to download
     */
    private suspend fun fetchPlantImage(imageURL: String?): Bitmap {
        return withContext(Dispatchers.IO) {
            try {
                val bitmap = Glide.with(context)
                    .asBitmap()
                    .load(imageURL)
                    .submit()
                    .get()
                return@withContext bitmap
            } catch (e: Exception) {
                return@withContext defaultBitmap
            }
        }
    }

    /**
     * Validates data and repairs where needed.
     */
    override suspend fun fixData(plant: Biljka): Biljka {
        // Search for the plant and
        TODO()
    }

    /**
     * Returns plants where the given flower condition is satisfied.
     */
    override suspend fun getPlantsWithFlowerColor(flowerColor: String, substring: String): List<Biljka> {
        TODO()
    }

    /**
     * Sets the `Context` so that we can load the default `Bitmap`.
     * ! Should we be using Glide for this?
     */

    fun setContext(context: Context) {
        this.context = context
        setDefaultBitmap()
    }

    private fun setDefaultBitmap() {
        defaultBitmap = Glide.with(context)
            .asBitmap()
            .load(R.mipmap.default_tree)
            .submit()
            .get()

    }

}