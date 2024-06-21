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
            var plantBitmap: Bitmap = defaultBitmap
            try {
                val latinName = plant.getLatinName()
                val response = RetrofitClient.trefleApiService.searchPlants(latinName)
                if (response.isSuccessful) {
                    Log.d("GetImage", "Response successful.")
                    val responseBody = response.body()
                    plantBitmap = fetchPlantImage(responseBody!!.plants[0].imageURL)
                } else {
                    Log.d("GetImage", "Response failed.")
                }
            } catch (e: Exception) {
                Log.e("GetImage", e.toString())
            }
            return@withContext plantBitmap
        }
    }

    /**
     * Downloads an image passed as URL. In case of any failure, returns `defaultBitmap`.
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
                val latinName = plant.getLatinName()
                val searchResponse = RetrofitClient.trefleApiService.searchPlants(latinName)
                val searchResponseBody = searchResponse.body()
                if (searchResponse.isSuccessful && searchResponseBody != null) {
                    Log.d("FixData", "Search response successful and body not null.")
                    Log.d("FixData", searchResponseBody.toString())
                    if (searchResponseBody.plants.isNotEmpty()) {
                        Log.d("FixData", "Search plants not empty.")
                        val plantId = searchResponseBody.plants[0].id
                        Log.d("FixData", "Plant id $plantId")
                        val plantDetailResponse = RetrofitClient.trefleApiService.getPlantByID(plantId)
                        val plantDetailResponseBody = plantDetailResponse.body()
                        Log.d("FixData", plantDetailResponseBody.toString())
                        if (plantDetailResponse.isSuccessful && plantDetailResponseBody != null) {
                            Log.d("FixData", "Plant response successful and body not null.")
                            Log.d("FixData", plantDetailResponseBody.toString())
                            return@withContext fixPlantData(plant, plantDetailResponseBody.data)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("FixData", e.toString())
            }
            return@withContext plant
        }
    }

    private fun fixPlantData(plant: Biljka, plantResponseBody: PlantResponse): Biljka {

        Log.d("FixPlantData", "Started fixPlantData")

        if (plantResponseBody.mainSpecies != null) {

            // Family
            if (plantResponseBody.mainSpecies.family != null && plant.porodica != plantResponseBody.mainSpecies.family) {
                Log.d("FixPlantData", "Changing the family name")
                plant.porodica = plantResponseBody.mainSpecies.family.toString()
            }

            // Dishes
            if (plantResponseBody.mainSpecies.isEdible != null && plantResponseBody.mainSpecies.isEdible == false) {
                Log.d("FixPlantData", "Removing the dishes and adding \"NOT EDIBLE\"")
                plant.jela = listOf()
                val notEdibleDisclaimer: String = getString(context, R.string.not_edible_disclaimer)
                if (!plant.medicinskoUpozorenje.contains(notEdibleDisclaimer))
                    plant.addMedicalDisclaimer(notEdibleDisclaimer)
            }

            // Medical warning
            if (plantResponseBody.mainSpecies.specifications != null) {

                if (plantResponseBody.mainSpecies.specifications.toxicity != null
                    && plantResponseBody.mainSpecies.specifications.toxicity != "none") {
                    Log.d("FixPlantData", "Adding medical warning")
                    val toxicDisclaimer: String = getString(context, R.string.toxic_disclaimer)
                    if (!plant.medicinskoUpozorenje.contains(toxicDisclaimer))
                        plant.addMedicalDisclaimer(toxicDisclaimer)
                }

            }

            if (plantResponseBody.mainSpecies.growth != null) {

                // Soil
                // TODO: Add missing types
                if (plantResponseBody.mainSpecies.growth.soilTexture != null) {
                    Log.d("FixPlantData", "Fixing soil textures")
                    val expectedSoilTypes: List<Zemljiste> = Zemljiste.getListOfSoilTypes(plantResponseBody.mainSpecies.growth.soilTexture)
                    plant.zemljisniTipovi = expectedSoilTypes
                }

                // Climate types
                // TODO: Add missing types
                if (plantResponseBody.mainSpecies.growth.light != null
                    && plantResponseBody.mainSpecies.growth.atmosphericHumidity != null) {
                    Log.d("FixPlantData", "Fixing climate types")
                    val expectedClimateTypes: List<KlimatskiTip> = KlimatskiTip.getListOfClimateTypes(plantResponseBody.mainSpecies.growth.light,
                        plantResponseBody.mainSpecies.growth.atmosphericHumidity)
                    plant.klimatskiTipovi = expectedClimateTypes
                }

            }

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