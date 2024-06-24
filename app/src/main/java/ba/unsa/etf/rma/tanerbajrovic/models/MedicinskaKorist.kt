package ba.unsa.etf.rma.tanerbajrovic.models

enum class MedicinskaKorist(val opis: String) {

    SMIRENJE("Smirenje - za smirenje i relaksaciju"),
    PROTUUPALNO("Protuupalno - za smanjenje upale"),
    PROTIVBOLOVA("Protivbolova - za smanjenje bolova"),
    REGULACIJAPRITISKA("Regulacija pritiska - za regulaciju visokog/niskog pritiska"),
    REGULACIJAPROBAVE("Regulacija probave"),
    PODRSKAIMUNITETU("Podrška imunitetu");

    companion object {

        fun getMedicalRemedyFromDescription(description: String): MedicinskaKorist? {
            for (value: MedicinskaKorist in MedicinskaKorist.entries) {
                if (value.opis == description) {
                    return value
                }
            }
            return null
        }

    }

}