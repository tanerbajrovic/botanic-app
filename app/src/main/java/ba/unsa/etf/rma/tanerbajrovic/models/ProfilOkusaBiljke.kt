package ba.unsa.etf.rma.tanerbajrovic.models

enum class ProfilOkusaBiljke(val opis: String) {

    MENTA("Mentol - osvježavajući, hladan ukus"),
    CITRUSNI("Citrusni - osvježavajući, aromatičan"),
    SLATKI("Sladak okus"),
    BEZUKUSNO("Obični biljni okus - travnat, zemljast ukus"),
    LJUTO("Ljuto ili papreno"),
    KORIJENASTO("Korenast - drvenast i gorak ukus"),
    AROMATICNO("Začinski - topli i aromatičan ukus"),
    GORKO("Gorak okus");

    companion object {

        fun getTasteProfileFromDescription(description: String): ProfilOkusaBiljke? {
            for (value: ProfilOkusaBiljke in ProfilOkusaBiljke.entries) {
                if (value.opis == description) {
                    return value
                }
            }
            return null
        }

    }

}