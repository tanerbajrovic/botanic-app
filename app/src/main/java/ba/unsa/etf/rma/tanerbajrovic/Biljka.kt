package ba.unsa.etf.rma.tanerbajrovic

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

    fun addMedicalDisclaimer(disclaimerMessage: String) {
        val sb = StringBuilder()
        sb.append(this.medicinskoUpozorenje)
        if (sb.isNotEmpty())
            sb.append(" ")
        sb.append(disclaimerMessage)
        this.medicinskoUpozorenje = sb.toString()
    }

}

