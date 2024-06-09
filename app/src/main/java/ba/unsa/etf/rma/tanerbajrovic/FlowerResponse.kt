package ba.unsa.etf.rma.tanerbajrovic

import com.google.gson.annotations.SerializedName

data class FlowerResponse(
    @SerializedName("color") val color: String?
)
