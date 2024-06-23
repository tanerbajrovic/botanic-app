package ba.unsa.etf.rma.tanerbajrovic.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Biljka::class, BiljkaBitmap::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BiljkaDatabase : RoomDatabase() {

    abstract fun biljkaDAO(): BiljkaDAO

    companion object {

        private var INSTANCE: BiljkaDatabase? = null

        fun getDatabase(context: Context): BiljkaDatabase {
            if (INSTANCE == null) {
                synchronized(BiljkaDatabase::class) {
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): BiljkaDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                BiljkaDatabase::class.java,
                "biljke-db"
            ).build()
        }

    }

}