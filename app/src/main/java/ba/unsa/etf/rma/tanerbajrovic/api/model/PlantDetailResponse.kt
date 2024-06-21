package ba.unsa.etf.rma.tanerbajrovic.api.model

import com.google.gson.annotations.SerializedName

data class PlantDetailResponse(
    @SerializedName("data") val data: PlantResponse
)
