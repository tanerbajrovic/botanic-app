package ba.unsa.etf.rma.tanerbajrovic.api.models

import com.google.gson.annotations.SerializedName

data class SpeciesSpecificationsResponse(
    @SerializedName("toxicity") val toxicity: String?
)