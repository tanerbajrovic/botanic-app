package ba.unsa.etf.rma.tanerbajrovic.models

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ba.unsa.etf.rma.tanerbajrovic.viewmodels.BiljkaViewModel

class BiljkaViewModelFactory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BiljkaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BiljkaViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}