package ba.unsa.etf.rma.tanerbajrovic.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.util.Base64
import android.util.Log
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
    fun fromBitmap(bitmap: Bitmap): String {
        val resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, 1000, 1000)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val baseEncodedString = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
        return baseEncodedString
    }

    @TypeConverter
    fun toBitmap(encodedString: String): Bitmap {
        val byteArray = Base64.decode(encodedString, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        return bitmap
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