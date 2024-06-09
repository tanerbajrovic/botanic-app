package ba.unsa.etf.rma.tanerbajrovic

import com.google.gson.annotations.SerializedName

data class GrowthResponse(
    @SerializedName("light") val light: Int?,
    @SerializedName("atmospheric_humidity") val atmosphericHumidity: Int?,
    @SerializedName("soil_texture") val soilTexture: Int?
)
