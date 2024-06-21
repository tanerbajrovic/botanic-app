package ba.unsa.etf.rma.tanerbajrovic

enum class Zemljiste(val naziv: String) {


    PJESKOVITO("Pjeskovito zemljište"),
    GLINENO("Glinеno zemljište"),
    ILOVACA("Ilovača"),
    CRNICA("Crnica"),
    SLJUNKOVITO("Šljunovito zemljište"),
    KRECNJACKO("Krečnjačko zemljište");

    companion object {
        fun getListOfSoilTypes(soilTexture: Int): List<Zemljiste> {
            val soilTypes: MutableList<Zemljiste> = mutableListOf()
            if (soilTexture in 1..2)
                soilTypes.add(GLINENO)
            if (soilTexture in 3..4)
                soilTypes.add(PJESKOVITO)
            if (soilTexture in 5..6)
                soilTypes.add(ILOVACA)
            if (soilTexture in 7..8)
                soilTypes.add(CRNICA)
            if (soilTexture == 9)
                soilTypes.add(SLJUNKOVITO)
            if (soilTexture == 10)
                soilTypes.add(KRECNJACKO)
            return soilTypes.toList()
        }
    }

}