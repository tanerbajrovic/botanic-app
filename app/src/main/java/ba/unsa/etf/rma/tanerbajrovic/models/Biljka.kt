package ba.unsa.etf.rma.tanerbajrovic.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "biljka")
class Biljka(
    @ColumnInfo(name = "naziv") var naziv: String,
    @ColumnInfo(name = "family") var porodica: String,
    @ColumnInfo(name = "medicinskoUpozorenje") var medicinskoUpozorenje: String,
    @ColumnInfo(name = "medicinskeKoristi") var medicinskeKoristi: List<MedicinskaKorist>,
    @ColumnInfo(name = "profilOkusa") var profilOkusa: ProfilOkusaBiljke,
    @ColumnInfo(name = "jela") var jela: List<String>,
    @ColumnInfo(name = "klimatskiTipovi") var klimatskiTipovi: List<KlimatskiTip>,
    @ColumnInfo(name = "zemljisniTipovi") var zemljisniTipovi: List<Zemljiste>,
    @ColumnInfo(name = "onlineChecked") var onlineChecked: Boolean = false,
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

