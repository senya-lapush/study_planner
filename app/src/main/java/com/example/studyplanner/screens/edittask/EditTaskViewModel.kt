package com.example.studyplanner.screens.edittask

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplanner.REPOSITORY
import com.example.studyplanner.models.SubjectModel
import com.example.studyplanner.models.TaskModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditTaskViewModel : ViewModel() {
    fun updateTask(task: TaskModel, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.updateTask(task) {
                onSuccess()
            }
        }
    }

    fun deleteTask(task: TaskModel, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.deleteTask(task) {
                onSuccess()
            }
        }
    }

    fun getSubjects(): LiveData<List<SubjectModel>> {
        return REPOSITORY.subjects
    }
}