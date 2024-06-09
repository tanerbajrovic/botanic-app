package ba.unsa.etf.rma.tanerbajrovic.api.model

import com.google.gson.annotations.SerializedName

data class FamilyResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val latinName: String?
)
