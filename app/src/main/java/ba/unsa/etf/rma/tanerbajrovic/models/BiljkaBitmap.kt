package ba.unsa.etf.rma.tanerbajrovic.models

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "biljkabitmap",
    foreignKeys = [
        ForeignKey(
            entity = Biljka::class,
            parentColumns = ["id"],
            childColumns = ["idBiljke"],
            onDelete = ForeignKey.CASCADE
        )])
data class BiljkaBitmap(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo("idBiljke") var plantId: Long,
    @ColumnInfo("bitmap") var bitmap: Bitmap
)