package ba.unsa.etf.rma.tanerbajrovic.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ba.unsa.etf.rma.tanerbajrovic.utils.getPlants
import ba.unsa.etf.rma.tanerbajrovic.models.Biljka
import ba.unsa.etf.rma.tanerbajrovic.models.SpinnerState
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var plants: MutableList<Biljka> = mutableListOf()
    var filteredPlants: List<Biljka> = mutableListOf()
    var spinnerState: SpinnerState = SpinnerState.MEDICAL
    val modes: Array<String> = arrayOf(
        SpinnerState.MEDICAL.description,
        SpinnerState.BOTANIC.description,
        SpinnerState.CULINARY.description)
    val colors: Array<String> = arrayOf(
        "Red", "Blue", "Yellow", "Orange",
        "Purple", "Brown", "Green")

    fun filterPlants(plant: Biljka) {
        when (spinnerState) {
            SpinnerState.MEDICAL -> {
                filteredPlants = filteredPlants.filter {
                    it.medicinskeKoristi.intersect(plant.medicinskeKoristi.toSet()).isNotEmpty()
                }
            }
            SpinnerState.BOTANIC -> {
                filteredPlants = filteredPlants.filter {
                    it.profilOkusa == plant.profilOkusa || it.jela.intersect(plant.jela.toSet())
                        .isNotEmpty()
                }
            }
            SpinnerState.CULINARY -> {
                filteredPlants = filteredPlants.filter {
                    it.porodica == plant.porodica
                    &&
                    it.klimatskiTipovi.intersect(plant.klimatskiTipovi.toSet()).isNotEmpty()
                    &&
                    it.zemljisniTipovi.intersect(plant.zemljisniTipovi.toSet()).isNotEmpty()
                }
            }
        }
    }

    fun resetPlants() {
        filteredPlants = plants.toList()
    }

}