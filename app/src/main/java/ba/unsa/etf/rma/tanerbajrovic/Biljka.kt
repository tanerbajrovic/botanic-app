package ba.unsa.etf.rma.tanerbajrovic

class Biljka(
    val naziv: String,
    val porodica: String,
    val medicinskoUpozorenja: String,
    val medicinskeKoristi: List<MedicinskaKorist>,
    val profilOkusa: ProfilOkusaBiljke,
    val jela: List<String>,
    val klimatskiTipovi: List<KlimatskiTip>,
    val zemljisniTipovi: List<Zemljiste>
)