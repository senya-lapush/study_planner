package com.example.studyplanner.db.repository

import androidx.lifecycle.LiveData
import com.example.studyplanner.db.views.TaskView
import com.example.studyplanner.models.SubjectModel
import com.example.studyplanner.models.TaskModel
import com.example.studyplanner.models.relationships.SubjectWithTasksModel

interface AppRepositoryInterface {
    val subjects: LiveData<List<SubjectModel>>
    val subjectsWithTasks: LiveData<List<SubjectWithTasksModel>>
    val tasks: LiveData<List<TaskView>>

    fun getDeadlineSoonTasks(days: Int): LiveData<List<TaskView>>
    fun getDeadlineNotSoonTasks(days: Int): LiveData<List<TaskView>>
    fun getTasksWithSubjectId(subjectId: Int): LiveData<List<TaskModel>>

    suspend fun insertSubject(subjectModel: SubjectModel, onSuccess: () -> Unit)
    suspend fun insertTask(taskModel: TaskModel, onSuccess: () -> Unit)
    suspend fun deleteSubject(subjectModel: SubjectModel, onSuccess: () -> Unit)
    suspend fun deleteTask(taskModel: TaskModel, onSuccess: () -> Unit)
    suspend fun updateTask(taskModel: TaskModel, onSuccess: () -> Unit)
    suspend fun updateSubject(subjectModel: SubjectModel, onSuccess: () -> Unit)
}