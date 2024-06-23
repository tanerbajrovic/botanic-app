package ba.unsa.etf.rma.tanerbajrovic.api

import ba.unsa.etf.rma.tanerbajrovic.BuildConfig
import ba.unsa.etf.rma.tanerbajrovic.api.models.PlantDetailResponse
import ba.unsa.etf.rma.tanerbajrovic.api.models.PlantSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrefleAPI {

    /**
     * Searches plants according to query `q`.
     * @return `PlantSearchResponse` object containing `List<PlantResponse`
     */
    @GET("plants/search?token=${BuildConfig.TREFLE_API_KEY}&page=1")
    suspend fun searchPlants(@Query("q") query: String): Response<PlantSearchResponse>

    /**
     * Returns a plant with the given `id`.
     */
    @GET("plants/{id}?token=${BuildConfig.TREFLE_API_KEY}")
    suspend fun getPlantByID(@Path("id") id: Long): Response<PlantDetailResponse>

    @GET("plants/?token=${BuildConfig.TREFLE_API_KEY}&filter[flower_color]={color}")
    suspend fun filterPlantsByFlowerColor(@Path("color") flowerColor: String): Response<PlantSearchResponse>

}