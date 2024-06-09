package ba.unsa.etf.rma.tanerbajrovic

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class TreflePlantDAO : PlantDAO {

    private lateinit var context: Context
    private lateinit var defaultBitmap: Bitmap
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + Job())
    private val trefle_api_key: String = "c1haNvSntbc7uC9wF_JYJDThU__-jscC0AO9iy8G17w"

//    interface API {
//        suspend fun
//    }

    /**
     * Matches according to latin name and returns the first such image.
     */
    override suspend fun getImage(plant: Biljka): Biljka {
        TODO()
    }

    override suspend fun fixData(plant: Biljka): Biljka {
        TODO()
    }

    override suspend fun getPlantsWithFlowerColor(flowerColor: String, substring: String): List<Biljka> {
        TODO()
    }

    fun setContext(context: Context) {
        this.context = context;
        setDefaultBitmap()
    }

    private fun setDefaultBitmap() {
        defaultBitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.default_tree)
    }

}