package com.example.studyplanner.screens.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.studyplanner.REPOSITORY
import com.example.studyplanner.db.AppDatabase
import com.example.studyplanner.db.repository.AppRepository
import com.example.studyplanner.db.views.TaskView
import com.example.studyplanner.models.relationships.SubjectWithTasksModel

class StartViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application

    fun initDatabase() {
        val subjectDao = AppDatabase.getInstance(context).getSubjectDao()
        REPOSITORY = AppRepository(subjectDao)
    }

    fun getAllTasks(): LiveData<List<TaskView>> {
        return REPOSITORY.tasks
    }

    fun getDeadlineSoonTasks(days: Int): LiveData<List<TaskView>> {
        return REPOSITORY.getDeadlineSoonTasks(days)
    }

    fun getDeadlineNotSoonTasks(days: Int): LiveData<List<TaskView>> {
        return REPOSITORY.getDeadlineNotSoonTasks(days)
    }
}