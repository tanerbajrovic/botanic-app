package ba.unsa.etf.rma.tanerbajrovic.api.model

import com.google.gson.annotations.SerializedName

data class SpeciesSpecificationsResponse(
    @SerializedName("toxicity") val isToxic: Boolean?
)