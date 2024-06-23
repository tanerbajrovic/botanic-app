package ba.unsa.etf.rma.tanerbajrovic.models

enum class KlimatskiTip(val opis: String) {

    SREDOZEMNA("Mediteranska klima - suha, topla ljeta i blage zime"),
    TROPSKA("Tropska klima - topla i vlažna tokom cijele godine"),
    SUBTROPSKA("Subtropska klima - blage zime i topla do vruća ljeta"),
    UMJERENA("Umjerena klima - topla ljeta i hladne zime"),
    SUHA("Sušna klima - niske padavine i visoke temperature tokom cijele godine"),
    PLANINSKA("Planinska klima - hladne temperature i kratke sezone rasta");

    companion object {
        fun getListOfClimateTypes(light: Int, atmosphericHumidity: Int): List<KlimatskiTip> {
            val climateTypes: MutableList<KlimatskiTip> = mutableListOf()
            if (light in 7..9 && atmosphericHumidity in 1..2)
                climateTypes.add(SUHA)
            if (light in 6..9 && atmosphericHumidity in 1..5)
                climateTypes.add(SREDOZEMNA)
            if (light in 8..10 && atmosphericHumidity in 7..10)
                climateTypes.add(TROPSKA)
            if (light in 6..9 && atmosphericHumidity in 5..8)
                climateTypes.add(SUBTROPSKA)
            if (light in 4..7 && atmosphericHumidity in 3..7)
                climateTypes.add(UMJERENA)
            if (light in 0..5 && atmosphericHumidity in 3..7)
                climateTypes.add(PLANINSKA)
            return climateTypes.toList()
        }
    }

}