package ba.unsa.etf.rma.tanerbajrovic.api.models

import com.google.gson.annotations.SerializedName

data class GrowthResponse(
    @SerializedName("light") val light: Int?,
    @SerializedName("atmospheric_humidity") val atmosphericHumidity: Int?,
    @SerializedName("soil_texture") val soilTexture: Int?
)
