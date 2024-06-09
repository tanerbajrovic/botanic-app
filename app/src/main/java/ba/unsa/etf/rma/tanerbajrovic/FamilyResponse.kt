package ba.unsa.etf.rma.tanerbajrovic

import com.google.gson.annotations.SerializedName

data class FamilyResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val latinName: String?
)
