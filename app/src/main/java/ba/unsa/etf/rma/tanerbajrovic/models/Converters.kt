package ba.unsa.etf.rma.tanerbajrovic.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converters {

    // Helper function
    inline fun <reified T : Enum<T>> fromEnumList(enumList: List<T>): String {
        val delimiter = ","
        val mutableList: MutableList<String> = mutableListOf()
        enumList.forEach {
            mutableList.add(it.name)
        }
        val joinedString: String = TextUtils.join(delimiter, mutableList)
        return joinedString
    }

    // Helper function
    inline fun <reified T : Enum<T>> toEnumList(joinedString: String): List<T> {
        val delimiter = ","
        val splitStringList = TextUtils.split(joinedString, delimiter)
        val mutableList: MutableList<T> = mutableListOf()
        splitStringList.forEach {
            mutableList.add(enumValueOf(it))
        }
        return mutableList.toList()
    }

    @TypeConverter
    fun fromMedicinskaKoristList(enumList: List<MedicinskaKorist>): String {
        return fromEnumList<MedicinskaKorist>(enumList)
    }

    @TypeConverter
    fun toMedicinskaKoristList(joinedString: String): List<MedicinskaKorist> {
        return toEnumList<MedicinskaKorist>(joinedString)
    }

    @TypeConverter
    fun fromKlimatskiTipList(enumList: List<KlimatskiTip>): String {
        return fromEnumList<KlimatskiTip>(enumList)
    }

    @TypeConverter
    fun toKlimatskiTipList(joinedString: String): List<KlimatskiTip> {
        return toEnumList<KlimatskiTip>(joinedString)
    }

    @TypeConverter
    fun fromZemljisteList(enumList: List<Zemljiste>): String {
        return fromEnumList<Zemljiste>(enumList)
    }

    @TypeConverter
    fun toZemljisteList(joinedString: String): List<Zemljiste> {
        return toEnumList<Zemljiste>(joinedString)
    }

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    @TypeConverter
    fun fromDishList(dishList: List<String>): String {
        val delimiter = ";"
        val joinedString: String = TextUtils.join(delimiter, dishList)
        return joinedString
    }

    @TypeConverter
    fun toDishList(dishes: String): List<String> {
        val delimiter = ";"
        val dishList = TextUtils.split(dishes, delimiter)
        return dishList.toList()
    }

    @TypeConverter
    fun toProfilOkusaBiljke(name: String) = ProfilOkusaBiljke.valueOf(name)

    @TypeConverter
    fun fromProfilOkusaBiljke(enum: ProfilOkusaBiljke) = enum.name
    
}