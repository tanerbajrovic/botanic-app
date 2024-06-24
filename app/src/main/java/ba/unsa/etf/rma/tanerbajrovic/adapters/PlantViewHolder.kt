package ba.unsa.etf.rma.tanerbajrovic.adapters

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.tanerbajrovic.R
import ba.unsa.etf.rma.tanerbajrovic.models.TrefleDAO
import ba.unsa.etf.rma.tanerbajrovic.models.Biljka
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val plantImage: ImageView = itemView.findViewById(R.id.slikaItem)
    private val plantName: TextView = itemView.findViewById(R.id.nazivItem)
    private lateinit var scope: CoroutineScope

    open fun bind(plant: Biljka) {
        plantImage.setImageResource(R.mipmap.default_tree)
        plantName.text = plant.naziv
        scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        scope.launch {
            try {
                val trefleDAO = TrefleDAO()
                trefleDAO.setContext(itemView.context.applicationContext)
                val bitmap: Bitmap = trefleDAO.getImage(plant)
                withContext(Dispatchers.Main) {
                    plantImage.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                Log.e("ExceptionError", e.toString())
            }
        }
    }

    fun clear() {
        scope.cancel()
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
            else
                plantSoil.text = ""
            if (plant.klimatskiTipovi.isNotEmpty())
                plantClimate.text = plant.klimatskiTipovi[0].opis
            else
                plantClimate.text = ""
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
            if (plant.jela.isEmpty()) {
                plantDishOne.text = ""
                plantDishTwo.text = ""
                plantDishThree.text = ""
            }
            for (i in 0 until minOf(plant.jela.size, 3)) {
                when (i) {
                    0 -> plantDishOne.text = plant.jela[0]
                    1 -> plantDishTwo.text = plant.jela[1]
                    2 -> plantDishThree.text = plant.jela[2]
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
            if (plant.medicinskeKoristi.isEmpty()) {
                plantRemedyOne.text = ""
                plantRemedyTwo.text = ""
                plantRemedyThree.text = ""
            }
            // Display at most three remedies (or display nothing when empty)
            for (i in 0 until minOf(plant.medicinskeKoristi.size, 3)) {
                when (i) {
                    0 -> plantRemedyOne.text = plant.medicinskeKoristi[0].opis
                    1 -> plantRemedyTwo.text = plant.medicinskeKoristi[1].opis
                    2 -> plantRemedyThree.text = plant.medicinskeKoristi[2].opis
                }
            }
        }
    }

}