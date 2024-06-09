package ba.unsa.etf.rma.tanerbajrovic.api.model

import com.google.gson.annotations.SerializedName

data class PlantSearchResponse(
    @SerializedName("data") val plants: List<PlantResponse>
)
