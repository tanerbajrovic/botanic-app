package ba.unsa.etf.rma.tanerbajrovic.api.model

import com.google.gson.annotations.SerializedName

data class MainSpeciesResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("edible") val isEdible: Boolean?,
    @SerializedName("flower") val flower: FlowerResponse,
    @SerializedName("specifications") val specifications: SpeciesSpecificationsResponse,
    @SerializedName("growth") val growth: GrowthResponse
)
