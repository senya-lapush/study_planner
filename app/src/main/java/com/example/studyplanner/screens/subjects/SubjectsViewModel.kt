package com.example.studyplanner.screens.subjects

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.studyplanner.REPOSITORY
import com.example.studyplanner.models.relationships.SubjectWithTasksModel

class SubjectsViewModel : ViewModel() {
    fun getAllSubjectsWithTasks(): LiveData<List<SubjectWithTasksModel>> {
        return REPOSITORY.subjectsWithTasks
    }
}