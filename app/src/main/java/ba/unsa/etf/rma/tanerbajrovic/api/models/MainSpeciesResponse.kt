package ba.unsa.etf.rma.tanerbajrovic.api.models

import com.google.gson.annotations.SerializedName

data class MainSpeciesResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("family") val family: String?,
    @SerializedName("edible") val isEdible: Boolean?,
    @SerializedName("flower") val flower: FlowerResponse?,
    @SerializedName("specifications") val specifications: SpeciesSpecificationsResponse?,
    @SerializedName("growth") val growth: GrowthResponse?
)
