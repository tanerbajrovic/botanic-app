package ba.unsa.etf.rma.tanerbajrovic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlantListAdapter(private var plants: List<Biljka>)
    : RecyclerView.Adapter<PlantListAdapter.PlantViewHolder>() {

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plantImage: ImageView = itemView.findViewById(R.id.slikaItem)
        val plantName: TextView = itemView.findViewById(R.id.nazivItem)
        val plantFamily: TextView = itemView.findViewById(R.id.porodicaItem)
        val plantClimate: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        val plantSoil: TextView = itemView.findViewById(R.id.zemljisniTipItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.botanic_list_item, parent, false)
        return PlantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return plants.size
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.plantName.text = plants[position].naziv
        holder.plantFamily.text = plants[position].porodica
        holder.plantSoil.text = plants[position].zemljisniTipovi[0].toString()
        holder.plantClimate.text = plants[position].klimatskiTipovi[0].toString()
    }

    fun updatePlants(plants: List<Biljka>) {
        this.plants = plants
        notifyDataSetChanged()
    }

}