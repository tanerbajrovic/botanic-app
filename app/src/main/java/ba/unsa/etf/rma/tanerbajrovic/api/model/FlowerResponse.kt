package ba.unsa.etf.rma.tanerbajrovic.api.model

import com.google.gson.annotations.SerializedName

data class FlowerResponse(
    @SerializedName("color") val color: String?
)
