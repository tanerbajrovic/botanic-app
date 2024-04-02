package ba.unsa.etf.rma.tanerbajrovic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PlantListAdapter(private var plants: List<Biljka>, private var spinnerState: SpinnerState)
    : RecyclerView.Adapter<PlantViewHolder>() {

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
        return plants.size
    }

    // ! Add better error-handling when remedies and dishes are empty.
    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.plantName.text = plants[position].naziv
        holder.bind(plants[position])
    }

    override fun getItemViewType(position: Int): Int {
//        return super.getItemViewType(position)
        return when (spinnerState) {
            SpinnerState.MEDICAL -> R.layout.medical_list_item
            SpinnerState.BOTANIC -> R.layout.botanic_list_item
            SpinnerState.CULINARY -> R.layout.culinary_list_item
        }
    }

    fun changeSpinnerState(newState: SpinnerState) {
        spinnerState = newState
        notifyDataSetChanged()
    }

    // What's a more efficient way of doing this?
    fun updatePlants(plants: List<Biljka>) {
        this.plants = plants
        notifyDataSetChanged()
    }

//    fun resetPlants() {
//
//    }

}