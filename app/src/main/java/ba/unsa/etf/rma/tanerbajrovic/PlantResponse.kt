package ba.unsa.etf.rma.tanerbajrovic

import com.google.gson.annotations.SerializedName

data class PlantResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("common_name") val commonName: String?,
    @SerializedName("scientific_name") val scientificName: String?,
    @SerializedName("image_url") val imageURL: String?,
    @SerializedName("family") val family: String?,
    @SerializedName("main_species") val mainSpecies: MainSpeciesResponse
)
