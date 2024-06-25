package ba.unsa.etf.rma.tanerbajrovic.adapters

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.tanerbajrovic.R
import ba.unsa.etf.rma.tanerbajrovic.models.Biljka
import ba.unsa.etf.rma.tanerbajrovic.models.BiljkaViewModelFactory
import ba.unsa.etf.rma.tanerbajrovic.models.SpinnerState
import ba.unsa.etf.rma.tanerbajrovic.viewmodels.BiljkaViewModel

class PlantListAdapter(
    private var plants: List<Biljka>,
    private var spinnerState: SpinnerState,
    private val filterCriteria: (Biljka) -> Unit)
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

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val item: Biljka = plants[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { filterCriteria(item) }
    }

    override fun getItemViewType(position: Int): Int {
        return when (spinnerState) {
            SpinnerState.MEDICAL -> R.layout.medical_list_item
            SpinnerState.BOTANIC -> R.layout.botanic_list_item
            SpinnerState.CULINARY -> R.layout.culinary_list_item
        }
    }

    override fun onViewRecycled(holder: PlantViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }

    fun changeSpinnerState(newState: SpinnerState) {
        if (newState == spinnerState)
            return
        spinnerState = newState
        notifyDataSetChanged()
    }

    fun updatePlants(plants: List<Biljka>) {
        this.plants = plants.toList()
        notifyDataSetChanged()
    }

}