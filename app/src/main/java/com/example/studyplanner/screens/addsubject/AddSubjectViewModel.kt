package com.example.studyplanner.screens.addsubject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplanner.REPOSITORY
import com.example.studyplanner.models.SubjectModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddSubjectViewModel : ViewModel() {
    fun insertSubject(subject: SubjectModel, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.insertSubject(subject) {
                onSuccess()
            }
        }
}