package ba.unsa.etf.rma.tanerbajrovic

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class NovaBiljkaActivity : AppCompatActivity() {

    private var medicaRemediesList: MutableList<String> = mutableListOf()
    private var tasteProfilesList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_plant)
        populateTasteProfiles()
        populateMedicalRemedies()
        val medicalRemedies: ListView = findViewById(R.id.medicinskaKoristLV)
        val tasteProfiles: ListView = findViewById(R.id.profilOkusaLV)
        val remediesAdapter: ArrayAdapter<String> = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, medicaRemediesList)
        val tasteProfileAdapter: ArrayAdapter<String> = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, tasteProfilesList)
        medicalRemedies.adapter = remediesAdapter
        tasteProfiles.adapter = tasteProfileAdapter
        remediesAdapter.notifyDataSetChanged()
        tasteProfileAdapter.notifyDataSetChanged()
    }

    private fun populateMedicalRemedies() {
        val medicalRemediesRaw: Array<MedicinskaKorist> = MedicinskaKorist.entries.toTypedArray()
        for (medicalRemedy: MedicinskaKorist in medicalRemediesRaw) {
            medicaRemediesList.add(medicalRemedy.opis)
        }
    }

    private fun populateTasteProfiles() {
        val tasteProfilesRaw: Array<ProfilOkusaBiljke> = ProfilOkusaBiljke.entries.toTypedArray()
        for (tasteProfile: ProfilOkusaBiljke in tasteProfilesRaw) {
            tasteProfilesList.add(tasteProfile.opis)
        }
    }

}