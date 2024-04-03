package ba.unsa.etf.rma.tanerbajrovic

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

sealed class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val plantImage: ImageView = itemView.findViewById(R.id.slikaItem)
    val plantName: TextView = itemView.findViewById(R.id.nazivItem)

    open fun bind(plant: Biljka) {
        plantImage.setImageResource(R.mipmap.default_tree)
        plantName.text = plant.naziv
    }

    class BotanicPlantViewHolder(itemView: View) : PlantViewHolder(itemView) {
        private val plantFamily: TextView = itemView.findViewById(R.id.porodicaItem)
        private val plantClimate: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        private val plantSoil: TextView = itemView.findViewById(R.id.zemljisniTipItem)

        override fun bind(plant: Biljka) {
            super.bind(plant)
            plantFamily.text = plant.porodica
            if (plant.zemljisniTipovi.isNotEmpty())
                plantSoil.text = plant.zemljisniTipovi[0].naziv
            if (plant.klimatskiTipovi.isNotEmpty())
                plantClimate.text = plant.klimatskiTipovi[0].opis
        }
    }

    class CulinaryPlantViewHolder(itemView: View) : PlantViewHolder(itemView) {
        private val plantTasteProfile: TextView = itemView.findViewById(R.id.profilOkusaItem)
        private val plantDishOne: TextView = itemView.findViewById(R.id.jelo1Item)
        private val plantDishTwo: TextView = itemView.findViewById(R.id.jelo2Item)
        private val plantDishThree: TextView = itemView.findViewById(R.id.jelo3Item)

        override fun bind(plant: Biljka) {
            super.bind(plant)
            plantTasteProfile.text = plant.profilOkusa.toString()
            if (plant.jela.size >= 3) { // There's a better way to do this, probably.
                plantDishOne.text = plant.jela[0]
                plantDishTwo.text = plant.jela[1]
                plantDishThree.text = plant.jela[2]
            }
            else if (plant.jela.size == 2) {
                plantDishOne.text = plant.jela[0]
                plantDishTwo.text = plant.jela[1]
            }
            else if (plant.jela.size == 1) {
                plantDishOne.text = plant.jela[0]
            }
        }
    }

    class MedicalPlantViewHolder(itemView: View) : PlantViewHolder(itemView) {
        private val plantWarning: TextView = itemView.findViewById(R.id.upozorenjeItem)
        private val plantRemedyOne: TextView = itemView.findViewById(R.id.korist1Item)
        private val plantRemedyTwo: TextView = itemView.findViewById(R.id.korist2Item)
        private val plantRemedyThree: TextView = itemView.findViewById(R.id.korist3Item)

        override fun bind(plant: Biljka) {
            super.bind(plant)
            plantWarning.text = plant.medicinskoUpozorenje
            if (plant.medicinskeKoristi.size >= 3) { // There's a better way to do this, probably.
                plantRemedyOne.text = plant.medicinskeKoristi[0].opis
                plantRemedyTwo.text = plant.medicinskeKoristi[1].opis
                plantRemedyThree.text = plant.medicinskeKoristi[2].opis
            }
            else if (plant.jela.size == 2) {
                plantRemedyOne.text = plant.medicinskeKoristi[0].opis
                plantRemedyTwo.text = plant.medicinskeKoristi[1].opis
            }
            else if (plant.jela.size == 1) {
                plantRemedyOne.text = plant.medicinskeKoristi[0].opis
            }
        }
    }

}