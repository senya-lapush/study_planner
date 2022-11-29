package com.example.studyplanner.screens.addtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplanner.REPOSITORY
import com.example.studyplanner.models.SubjectModel
import com.example.studyplanner.models.TaskModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTaskViewModel : ViewModel() {
    fun insertTask(task: TaskModel, OnSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.insertTask(task) {
                OnSuccess()
            }
        }

    fun getSubjects(): LiveData<List<SubjectModel>> {
        return REPOSITORY.subjects
    }
}