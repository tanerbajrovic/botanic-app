package ba.unsa.etf.rma.tanerbajrovic

import com.google.gson.annotations.SerializedName

data class PlantSearchResponse(
    @SerializedName("data") val plants: List<PlantResponse>
)
