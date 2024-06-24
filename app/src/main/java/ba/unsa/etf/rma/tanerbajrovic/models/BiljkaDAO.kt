package ba.unsa.etf.rma.tanerbajrovic.models

import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface BiljkaDAO {

    // Required methods

    @Transaction
    suspend fun saveBiljka(plant: Biljka): Boolean {
        val id = insertBiljka(plant)
        return id != -1L
    }

//    suspend fun fixOfflineBiljka(): Int

    @Transaction
    suspend fun addImage(plantId: Long, bitmap: Bitmap) {
        val biljkaBitmap = BiljkaBitmap(bitmap, plantId)
        insertBiljkaBitmap(biljkaBitmap)
    }

    @Query("SELECT * FROM biljka")
    suspend fun getAllBiljkas(): List<Biljka>

//    @Transaction
//    suspend fun clearData() {
//        clearBiljkaBitmaps()
//        clearBiljkas()
//    }

    // My methods

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBiljka(plant: Biljka): Long

    @Query("SELECT * FROM biljka where id = :plantId")
    suspend fun getBiljka(plantId: Long): Biljka?

    @Query("SELECT * FROM biljka WHERE naziv = :name")
    suspend fun getBiljkaByName(name: String): Biljka?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBiljkaBitmap(biljkaBitmap: BiljkaBitmap)

//    @Transaction
//    suspend fun getBiljkaBitmap(plantId: Long): BiljkaBitmap

    @Query("DELETE FROM biljka")
    suspend fun clearBiljkas()

    @Query("DELETE FROM biljkabitmap")
    suspend fun clearBiljkaBitmaps()

}