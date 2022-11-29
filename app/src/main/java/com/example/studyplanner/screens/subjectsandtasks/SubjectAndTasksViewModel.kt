package com.example.studyplanner.screens.subjectsandtasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplanner.REPOSITORY
import com.example.studyplanner.db.views.TaskView
import com.example.studyplanner.models.SubjectModel
import com.example.studyplanner.models.TaskModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubjectAndTasksViewModel : ViewModel() {
    fun getTasksWithSubjectId(subjectId: Int): LiveData<List<TaskModel>> {
        return REPOSITORY.getTasksWithSubjectId(subjectId)
    }

    fun updateSubject(subject: SubjectModel, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.updateSubject(subject) {
                onSuccess()
            }
        }
    }

    fun deleteSubject(subject: SubjectModel, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.deleteSubject(subject) {
                onSuccess()
            }
        }
    }
}