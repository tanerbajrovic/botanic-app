package ba.unsa.etf.rma.tanerbajrovic

import android.content.Context
import android.graphics.Bitmap
import androidx.core.database.getStringOrNull
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.unsa.etf.rma.tanerbajrovic.models.Biljka
import ba.unsa.etf.rma.tanerbajrovic.models.BiljkaDAO
import ba.unsa.etf.rma.tanerbajrovic.models.BiljkaDatabase
import ba.unsa.etf.rma.tanerbajrovic.models.KlimatskiTip
import ba.unsa.etf.rma.tanerbajrovic.models.MedicinskaKorist
import ba.unsa.etf.rma.tanerbajrovic.models.ProfilOkusaBiljke
import ba.unsa.etf.rma.tanerbajrovic.models.Zemljiste
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers.greaterThan
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

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

    private fun getSamplePlant(): Biljka {
        val biljka = Biljka(
            naziv = "Kaktus Zmajevo Voće",
            porodica = "Cactaceae",
            medicinskoUpozorenje = "Može izazvati blagu nelagodu u stomaku ako se konzumira u velikim količinama",
            medicinskeKoristi = listOf(
                MedicinskaKorist.PODRSKAIMUNITETU,
                MedicinskaKorist.PODRSKAIMUNITETU,
                MedicinskaKorist.REGULACIJAPROBAVE
            ),
            profilOkusa = ProfilOkusaBiljke.GORKO,
            jela = listOf("Smoothie od zmajevog voća", "Salsa od zmajevog voća", "Sorbet od zmajevog voća"),
            klimatskiTipovi = listOf(
                KlimatskiTip.SUHA,
                KlimatskiTip.TROPSKA),
            zemljisniTipovi = listOf(Zemljiste.CRNICA, Zemljiste.SLJUNKOVITO),
            onlineChecked = false)
        return biljka
    }

    @Test
    fun saveBiljka_CheckIfCorrectlyAdded() {
        return runBlocking {
            val biljka = getSamplePlant()
            val result = biljkaDao.saveBiljka(biljka)
            assertThat(result, equalTo(true))
            val allPlants = biljkaDao.getAllBiljkas()
            assertThat(allPlants.size, `is`(1)) // Five are already preloaded.
            val plant = allPlants[0]
            assertThat(plant.naziv, containsString("Zmajevo Voće"))
            assertThat(plant.porodica, equalTo("Cactaceae"))
            assertThat(plant.medicinskoUpozorenje, containsString("nelagodu u stomaku"))
            assertThat(plant.jela.size, `is`(3))
            assertThat(plant.onlineChecked, equalTo(false))
        }
    }


    @Test
    fun getAllBiljkas_CheckIfEmptyAfterClearData() {
        return runBlocking {
            // Adding plants
            val biljka = getSamplePlant()
            biljkaDao.saveBiljka(biljka)
            assertThat(biljkaDao.getAllBiljkas().size, greaterThan(0))
            biljkaDao.clearData()
            val plantList = biljkaDao.getAllBiljkas()
            assertThat(plantList.size, `is`(0))
        }
    }

}