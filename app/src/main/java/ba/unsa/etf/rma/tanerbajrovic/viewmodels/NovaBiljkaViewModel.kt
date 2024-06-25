package ba.unsa.etf.rma.tanerbajrovic.viewmodels

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import ba.unsa.etf.rma.tanerbajrovic.NovaBiljkaActivity

class NovaBiljkaViewModel : ViewModel() {

    val validator: NovaBiljkaViewModel.Validator = Validator()

    inner class Validator {

        fun isValidText(text: String): Boolean {
            return (text.length in 2..40) && text.isNotBlank()
        }

        // TODO: Check if it's an actual Latin name via API.
        fun doesContainLatinName(plantName: String): Boolean {
            return plantName.contains("(") && plantName.contains(")")
                    && plantName.substringAfter("(").substringBefore(")").isNotEmpty()
        }

        fun isValidDish(dishName: String, dishes: List<String>): Boolean {
            val uppercaseDishName = dishName.uppercase()
            for (currentDish: String in dishes) {
                if (currentDish.uppercase() == uppercaseDishName)
                    return false
            }
            return true
        }

        fun isValidDishList(dishes: List<String>): Boolean {
            return dishes.isNotEmpty()
        }

//        fun isValidList(listview: ListView): Boolean {
//            return listview.checkedItemCount > 0
//        }
//
//        fun hasRequiredPermissions(): Boolean {
//            val cameraPermissionStatus: Int = ContextCompat.checkSelfPermission(
//                this@NovaBiljkaActivity,
//                android.Manifest.permission.CAMERA)
//            return cameraPermissionStatus == PackageManager.PERMISSION_GRANTED
//        }

    }

}