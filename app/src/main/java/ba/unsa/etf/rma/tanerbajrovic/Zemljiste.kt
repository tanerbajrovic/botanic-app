package ba.unsa.etf.rma.tanerbajrovic

enum class Zemljiste(val naziv: String) {


    PJESKOVITO("Pjeskovito zemljište"),
    GLINENO("Glinеno zemljište"),
    ILOVACA("Ilovača"),
    CRNICA("Crnica"),
    SLJUNKOVITO("Šljunovito zemljište"),
    KRECNJACKO("Krečnjačko zemljište");

    companion object {
        fun getSoilType(soilTexture: Int): Zemljiste {
            if (soilTexture in 1..2)
                return GLINENO
            else if (soilTexture in 3..4)
                return PJESKOVITO
            else if (soilTexture in 5..6)
                return ILOVACA
            else if (soilTexture in 7..8)
                return CRNICA
            else if (soilTexture == 9)
                return SLJUNKOVITO
            else // soilTexture == 10
                return KRECNJACKO
        }
    }

}