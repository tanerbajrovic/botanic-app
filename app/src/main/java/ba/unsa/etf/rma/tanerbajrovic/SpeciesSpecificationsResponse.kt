package ba.unsa.etf.rma.tanerbajrovic

import com.google.gson.annotations.SerializedName

data class SpeciesSpecificationsResponse(
    @SerializedName("toxicity") val isToxic: Boolean?
)