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
        try {
            val potentialDuplicate = getBiljkaByName(plant.naziv)
            if (potentialDuplicate == null) {
                insertBiljka(plant)
                return true
            }
            return false
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

//    suspend fun fixOfflineBiljka(): Int

    @Transaction
    suspend fun addImage(plantId: Long, bitmap: Bitmap): Boolean {
        try {
            val existingBitmap = getBitmapByBiljkaId(plantId)
            if (existingBitmap == null) {
                val biljkaBitmap = BiljkaBitmap(0, plantId, bitmap)
                insertBiljkaBitmap(biljkaBitmap)
                return true
            }
            return false
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    /**
     * Returns a list of all plants.
     */
    @Query("SELECT * FROM biljka")
    suspend fun getAllBiljkas(): List<Biljka>


    /**
     * Clears contents of both tables.
     */
    @Transaction
    suspend fun clearData() {
        clearBiljkaBitmaps()
        clearBiljkas()
    }

    // My methods

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBiljka(plant: Biljka): Long

    @Query("SELECT * FROM biljka where id = :plantId")
    suspend fun getBiljka(plantId: Long): Biljka?

    @Query("SELECT * FROM biljka WHERE naziv = :name LIMIT 1")
    suspend fun getBiljkaByName(name: String): Biljka?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBiljkaBitmap(biljkaBitmap: BiljkaBitmap): Long

    @Query("SELECT bitmap FROM biljkabitmap WHERE idBiljke = :plantId")
    suspend fun getBitmapByBiljkaId(plantId: Long): Bitmap?

    @Query("DELETE FROM biljka")
    suspend fun clearBiljkas()

    @Query("DELETE FROM biljkabitmap")
    suspend fun clearBiljkaBitmaps()

}