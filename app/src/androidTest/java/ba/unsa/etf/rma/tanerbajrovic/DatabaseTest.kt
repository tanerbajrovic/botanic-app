package ba.unsa.etf.rma.tanerbajrovic

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.unsa.etf.rma.tanerbajrovic.models.Biljka
import ba.unsa.etf.rma.tanerbajrovic.models.BiljkaDAO
import ba.unsa.etf.rma.tanerbajrovic.models.BiljkaDatabase
import ba.unsa.etf.rma.tanerbajrovic.models.KlimatskiTip
import ba.unsa.etf.rma.tanerbajrovic.models.MedicinskaKorist
import ba.unsa.etf.rma.tanerbajrovic.models.ProfilOkusaBiljke
import ba.unsa.etf.rma.tanerbajrovic.models.Zemljiste
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var biljkaDao: BiljkaDAO
    private lateinit var db: BiljkaDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, BiljkaDatabase::class.java).build()
        biljkaDao = db.biljkaDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

//    @Test
//    fun saveBiljka_CheckIfCorrectlyAdded() {
//        return runBlocking {
//            val biljka = Biljka(
//                naziv = "Kaktus Zmajevo Voće",
//                porodica = "Cactaceae",
//                medicinskoUpozorenje = "Može izazvati blagu nelagodu u stomaku ako se konzumira u velikim količinama",
//                medicinskeKoristi = listOf(
//                    MedicinskaKorist.PODRSKAIMUNITETU,
//                    MedicinskaKorist.PODRSKAIMUNITETU,
//                    MedicinskaKorist.REGULACIJAPROBAVE
//                ),
//                profilOkusa = ProfilOkusaBiljke.GORKO,
//                jela = listOf("Smoothie od zmajevog voća", "Salsa od zmajevog voća", "Sorbet od zmajevog voća"),
//                klimatskiTipovi = listOf(
//                    KlimatskiTip.SUHA,
//                    KlimatskiTip.TROPSKA),
//                zemljisniTipovi = listOf(Zemljiste.CRNICA, Zemljiste.SLJUNKOVITO),
//                onlineChecked = false,
//                id = 1L
//            )
//            val result = biljkaDao.saveBiljka(biljka)
//            assertTrue(result)
//            val allPlants = biljkaDao.getAllBiljkas()
//            assertEquals(allPlants.size, 6)
//            assertEquals(allPlants.get(0).naziv, )
//        }
//    }


//    @Test
//    fun getAllBiljkas_CheckIfEmptyAfterClearData() {
//        return runBlocking {
//
//        }
//    }

}