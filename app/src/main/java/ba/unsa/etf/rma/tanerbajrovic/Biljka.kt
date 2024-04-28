package ba.unsa.etf.rma.tanerbajrovic

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

class Biljka(
    val naziv: String,
    val porodica: String,
    val medicinskoUpozorenje: String,
    val medicinskeKoristi: List<MedicinskaKorist>,
    val profilOkusa: ProfilOkusaBiljke,
    val jela: List<String>,
    val klimatskiTipovi: List<KlimatskiTip>,
    val zemljisniTipovi: List<Zemljiste>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(MedicinskaKorist.CREATOR) ?: emptyList(),
        parcel.readParcelable(ProfilOkusaBiljke::class.java.classLoader)!!,
        parcel.createStringArrayList()!!,
        parcel.createTypedArrayList(KlimatskiTip.CREATOR) ?: emptyList(),
        parcel.createTypedArrayList(Zemljiste.CREATOR) ?: emptyList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(naziv)
        parcel.writeString(porodica)
        parcel.writeString(medicinskoUpozorenje)
        parcel.writeParcelableArray(medicinskeKoristi.toTypedArray(), flags)
        parcel.writeParcelable(profilOkusa, flags)
        parcel.writeStringList(jela)
        parcel.writeParcelableArray(klimatskiTipovi.toTypedArray(), flags)
        parcel.writeParcelableArray(zemljisniTipovi.toTypedArray(), flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Biljka> {
        override fun createFromParcel(parcel: Parcel): Biljka {
            return Biljka(parcel)
        }

        override fun newArray(size: Int): Array<Biljka?> {
            return arrayOfNulls(size)
        }
    }

}

