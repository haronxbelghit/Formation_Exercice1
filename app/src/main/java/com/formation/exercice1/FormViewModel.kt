package com.formation.exercice1

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FormViewModel : ViewModel() {
    private val _formData = MutableLiveData<FormData>()
    val formData: LiveData<FormData>
        get() = _formData

    fun setFormData(data: FormData) {
        _formData.value = data
    }
}

data class FormData(
    val firstName: String?,
    val lastName: String?,
    val city: String?,
    val country: String?,
    val phone: String?,
    val job: String?,
    val desc: String?,
    val email: String?,
    val profileImageUri: Uri?
)
