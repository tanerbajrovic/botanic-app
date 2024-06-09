package ba.unsa.etf.rma.tanerbajrovic

enum class KlimatskiTip(val opis: String) {

    SREDOZEMNA("Mediteranska klima - suha, topla ljeta i blage zime"),
    TROPSKA("Tropska klima - topla i vlažna tokom cijele godine"),
    SUBTROPSKA("Subtropska klima - blage zime i topla do vruća ljeta"),
    UMJERENA("Umjerena klima - topla ljeta i hladne zime"),
    SUHA("Sušna klima - niske padavine i visoke temperature tokom cijele godine"),
    PLANINSKA("Planinska klima - hladne temperature i kratke sezone rasta");


    companion object {
        fun getClimateType(light: Int, atmosphericHumidity: Int): KlimatskiTip? {
            if (light in 7..9 && atmosphericHumidity in 1..2)
                return SUHA
            else if (light in 6..9 && atmosphericHumidity in 1..5)
                return SREDOZEMNA
            else if (light in 8..10 && atmosphericHumidity in 7..10)
                return TROPSKA
            else if (light in 6..9 && atmosphericHumidity in 5..8)
                return SUBTROPSKA
            else if (light in 4..7 && atmosphericHumidity in 3..7)
                return UMJERENA
            else if (light in 0..5 && atmosphericHumidity in 3..7)
                return PLANINSKA
            return null
        }
    }


}