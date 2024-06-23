package ba.unsa.etf.rma.tanerbajrovic.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface BiljkaDAO {

    @Transaction
    suspend fun saveBiljka(plant: Biljka): Boolean {
        val id = insertBiljka(plant)
        return id != -1L
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBiljka(plant: Biljka): Long

//    suspend fun fixOfflineBiljka(): Int

    /**
     * Add image to the DB.
     */
//    @Insert
//    suspend fun addImage(plantId: Long, bitmap: Bitmap): Boolean

    /**
     * Return a list of plants from the DB.
     */
    @Query("SELECT * FROM biljka")
    suspend fun getAllBiljkas(): List<Biljka>

    /**
     * Delete all plants and images from the DB.
     */
//    @Transaction
//    suspend fun clearData() {
//        clearBiljkaBitmaps()
//        clearBiljkas()
//    }

    @Query("DELETE FROM biljka")
    suspend fun clearBiljkas()

    @Query("DELETE FROM biljkabitmap")
    suspend fun clearBiljkaBitmaps()

}