package ba.unsa.etf.rma.tanerbajrovic

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

sealed class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val plantImage: ImageView = itemView.findViewById(R.id.slikaItem)
    private val plantName: TextView = itemView.findViewById(R.id.nazivItem)

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
            plantTasteProfile.text = plant.profilOkusa.opis
            // Display at most three dishes (or display nothing when empty)
            for (i in 0 until minOf(plant.jela.size, 3)) {
                when (i) {
                    1 -> plantDishOne.text = plant.jela[0]
                    2 -> plantDishTwo.text = plant.jela[1]
                    3 -> plantDishThree.text = plant.jela[2]
                }
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
            for (i in 0 until minOf(plant.medicinskeKoristi.size, 3)) {
                when (i) {
                    1 -> plantRemedyOne.text = plant.medicinskeKoristi[0].opis
                    2 -> plantRemedyTwo.text = plant.medicinskeKoristi[1].opis
                    3 -> plantRemedyThree.text = plant.medicinskeKoristi[2].opis
                }
            }
        }
    }

}