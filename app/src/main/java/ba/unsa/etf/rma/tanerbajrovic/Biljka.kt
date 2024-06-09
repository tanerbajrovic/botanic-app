package ba.unsa.etf.rma.tanerbajrovic

import android.util.Log
import com.google.gson.annotations.SerializedName

class Biljka(
    var naziv: String,
    var porodica: String,
    var medicinskoUpozorenje: String,
    var medicinskeKoristi: List<MedicinskaKorist>,
    var profilOkusa: ProfilOkusaBiljke,
    var jela: List<String>,
    var klimatskiTipovi: List<KlimatskiTip>,
    var zemljisniTipovi: List<Zemljiste>
) {

    /**
     * Returns the Latin name in parenthesis.
     */
    fun getLatinName(): String {
        return naziv.substringAfter("(").substringBefore(")")
    }

}

