package ba.unsa.etf.rma.tanerbajrovic

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PlantListAdapter(
    private var plants: List<Biljka>,
    private var spinnerState: SpinnerState)
    : RecyclerView.Adapter<PlantViewHolder>() {

    private var filteredPlants: List<Biljka> = plants.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        lateinit var view: View
        return when (viewType) {
            R.layout.medical_list_item -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.medical_list_item, parent, false)
                PlantViewHolder.MedicalPlantViewHolder(view)
            }

            R.layout.botanic_list_item ->  {
                view = LayoutInflater.from(parent.context).inflate(R.layout.botanic_list_item, parent, false)
                PlantViewHolder.BotanicPlantViewHolder(view)
            }

            R.layout.culinary_list_item -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.culinary_list_item, parent, false)
                PlantViewHolder.CulinaryPlantViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return filteredPlants.size
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val item: Biljka = filteredPlants[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { filterPlants(item) }
    }

    override fun getItemViewType(position: Int): Int {
        return when (spinnerState) {
            SpinnerState.MEDICAL -> R.layout.medical_list_item
            SpinnerState.BOTANIC -> R.layout.botanic_list_item
            SpinnerState.CULINARY -> R.layout.culinary_list_item
        }
    }

    fun changeSpinnerState(newState: SpinnerState) {
        if (newState == spinnerState)
            return
        spinnerState = newState
        notifyDataSetChanged()
    }

    fun updatePlants(plants: List<Biljka>) {
        this.plants = plants.toList()
        filteredPlants = plants.toList()
        notifyDataSetChanged()
    }

    private fun filterPlants(plant: Biljka) {
        when (spinnerState) {
            SpinnerState.MEDICAL -> {
                filteredPlants = filteredPlants.filter {
                    it.medicinskeKoristi.intersect(plant.medicinskeKoristi.toSet()).isNotEmpty()
                }
            }
            SpinnerState.BOTANIC -> {
                filteredPlants = filteredPlants.filter {
                    it.profilOkusa == plant.profilOkusa || it.jela.intersect(plant.jela.toSet()).isNotEmpty()
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
        notifyDataSetChanged()
    }

    fun resetPlants() {
        filteredPlants = plants.toList()
        notifyDataSetChanged()
    }

}