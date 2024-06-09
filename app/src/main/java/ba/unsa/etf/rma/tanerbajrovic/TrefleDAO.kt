package ba.unsa.etf.rma.tanerbajrovic

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.core.content.ContextCompat.getString
import ba.unsa.etf.rma.tanerbajrovic.api.RetrofitClient
import ba.unsa.etf.rma.tanerbajrovic.api.model.PlantResponse
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
                return@withContext plantBitmap
            } catch (e: Exception) {
                return@withContext defaultBitmap
            }
        }
    }

    /**
     * Downloads an image passed as URL.
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
     * Validates data and repairs plant's data where needed.
     */
    override suspend fun fixData(plant: Biljka): Biljka {
        return withContext(Dispatchers.IO) {
            try {
                // Obtaining the plant
                val latinName = plant.getLatinName()
                val response = RetrofitClient.trefleApiService.searchPlants(latinName)
                val responseBody = response.body()
                val plantResponse = RetrofitClient.trefleApiService.getPlantByID(responseBody!!.plants[0].id) // Fix this.
                val plantResponseBody = plantResponse.body()
                // Fixing the original plant
                return@withContext fixPlantData(plant, plantResponseBody)
            } catch (e: Exception) {
                Log.e("Invalid data", e.toString())
                return@withContext plant
            }
        }
    }

    private fun fixPlantData(plant: Biljka, plantResponseBody: PlantResponse?): Biljka {

        if (plantResponseBody == null)
            return plant

        // Family
        if (plantResponseBody.family != null && plant.porodica != plantResponseBody.family)
            plant.porodica = plantResponseBody.family.toString()

        // Dishes
        if (plantResponseBody.mainSpecies.isEdible != null && plantResponseBody.mainSpecies.isEdible == false) {
            plant.jela = listOf()
            val notEdible: String = getString(context, R.string.not_edible)
            if (!plant.medicinskoUpozorenje.contains(notEdible)) {
                val sb = StringBuilder()
                sb.append(plant.medicinskoUpozorenje)
                if (sb.isNotEmpty())
                    sb.append(" ")
                sb.append(notEdible)
                plant.medicinskoUpozorenje = sb.toString()
            }
        }

        // Medical warning
        if (plantResponseBody.mainSpecies.specifications.toxicity != null
                && plantResponseBody.mainSpecies.specifications.toxicity != "none") {
            plant.medicinskoUpozorenje
            val toxic: String = getString(context, R.string.toxic)
            if (!plant.medicinskoUpozorenje.contains(toxic)) {
                val sb = StringBuilder()
                sb.append(plant.medicinskoUpozorenje)
                if (sb.isNotEmpty())
                    sb.append(" ")
                sb.append(toxic)
                plant.medicinskoUpozorenje = sb.toString()
            }
        }

        // Soil
        if (plantResponseBody.mainSpecies.growth.soilTexture != null) { // What about adding new types?
            val newSoilTypesList: List<Zemljiste> =
                plant.zemljisniTipovi.filter {
                    it != Zemljiste.getSoilType(plantResponseBody.mainSpecies.growth.soilTexture)
                }
            plant.zemljisniTipovi = newSoilTypesList
        }

        // Climate types
        if (plantResponseBody.mainSpecies.growth.light != null
            && plantResponseBody.mainSpecies.growth.atmosphericHumidity != null) { // What about adding new types?
            val newClimateTypes: List<KlimatskiTip> =
                plant.klimatskiTipovi.filter {
                    it != KlimatskiTip.getClimateType(plantResponseBody.mainSpecies.growth.light,
                        plantResponseBody.mainSpecies.growth.atmosphericHumidity)
                }
            plant.klimatskiTipovi = newClimateTypes
        }
        return plant
    }

    /**
     * Returns plants where the given flower condition is satisfied.
     */
    override suspend fun getPlantsWithFlowerColor(flowerColor: String, substring: String): List<Biljka> {
        TODO()
    }

    /**
     * Sets the `Context` so that we can load the default `Bitmap`.
     */
    fun setContext(context: Context) {
        this.context = context
        setDefaultBitmap()
    }

    /**
     * Sets the defaultBitmap from the app's resources.
     */
    private fun setDefaultBitmap() {
        defaultBitmap = Glide.with(context)
            .asBitmap()
            .load(R.mipmap.default_tree)
            .submit()
            .get()

    }

}