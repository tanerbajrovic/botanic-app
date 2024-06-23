package ba.unsa.etf.rma.tanerbajrovic.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "biljka")
class Biljka(
    @ColumnInfo(name = "naziv") var naziv: String,
    @ColumnInfo(name = "porodica") var porodica: String,
    @ColumnInfo(name = "medicinsko_upozorenje") var medicinskoUpozorenje: String,
    @ColumnInfo(name = "medicinske_koristi") var medicinskeKoristi: List<MedicinskaKorist>,
    @ColumnInfo(name = "profil_okusa") var profilOkusa: ProfilOkusaBiljke,
    @ColumnInfo(name = "jela") var jela: List<String>,
    @ColumnInfo(name = "klimatski_tipovi") var klimatskiTipovi: List<KlimatskiTip>,
    @ColumnInfo(name = "zemljisni_tipovi") var zemljisniTipovi: List<Zemljiste>,
    @ColumnInfo(name = "online_checked") var onlineChecked: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
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

