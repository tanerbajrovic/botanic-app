package ba.unsa.etf.rma.tanerbajrovic

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

sealed class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val plantImage: ImageView = itemView.findViewById(R.id.slikaItem)
    val plantName: TextView = itemView.findViewById(R.id.nazivItem)

    open fun bind(plant: Biljka) {
        plantName.text = plant.naziv
    }

    class BotanicPlantViewHolder(itemView: View) : PlantViewHolder(itemView) {
        val plantFamily: TextView = itemView.findViewById(R.id.porodicaItem)
        val plantClimate: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        val plantSoil: TextView = itemView.findViewById(R.id.zemljisniTipItem)

        override fun bind(plant: Biljka) {
            super.bind(plant)
            plantFamily.text = plant.porodica
            plantSoil.text = plant.zemljisniTipovi[0].toString()
            plantClimate.text = plant.klimatskiTipovi[0].toString()
        }
    }

    class CulinaryPlantViewHolder(itemView: View) : PlantViewHolder(itemView) {
        val plantTasteProfile: TextView = itemView.findViewById(R.id.profilOkusa)
        val plantDishOne: TextView = itemView.findViewById(R.id.jelo1Item)
        val plantDishTwo: TextView = itemView.findViewById(R.id.jelo2Item)
        val plantDishThree: TextView = itemView.findViewById(R.id.jelo3Item)

        override fun bind(plant: Biljka) {
            plantTasteProfile.text = plant.profilOkusa.toString()
//                culinaryHolder.plantDishOne.text = plants[position].jela[0]
//                culinaryHolder.plantDishTwo.text = plants[position].jela[1]
//                culinaryHolder.plantDishThree.text = plants[position].jela[2]
            plantDishOne.text = "RandomOne"
            plantDishTwo.text = "RandomTwo"
            plantDishThree.text = "RandomThree"
        }
    }

    class MedicalPlantViewHolder(itemView: View) : PlantViewHolder(itemView) {
        val plantWarning: TextView = itemView.findViewById(R.id.upozorenjeItem)
        val plantRemedyOne: TextView = itemView.findViewById(R.id.korist1Item)
        val plantRemedyTwo: TextView = itemView.findViewById(R.id.korist2Item)
        val plantRemedyThree: TextView = itemView.findViewById(R.id.korist3Item)

        override fun bind(plant: Biljka) {
            plantWarning.text = plant.medicinskoUpozorenje
//                medicalHolder.plantRemedyOne.text = plants[position].medicinskeKoristi[0].toString()
//                medicalHolder.plantRemedyTwo.text = plants[position].medicinskeKoristi[1].toString()
//                medicalHolder.plantRemedyThree.text = plants[position].medicinskeKoristi[2].toString()
            plantRemedyOne.text = "RandomOne"
            plantRemedyTwo.text = "RandomTwo"
            plantRemedyThree.text = "RandomThree"
        }
    }

}