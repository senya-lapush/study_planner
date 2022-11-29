package com.example.studyplanner.db.repository

import androidx.lifecycle.LiveData
import com.example.studyplanner.db.dao.AppDao
import com.example.studyplanner.db.views.TaskView
import com.example.studyplanner.models.SubjectModel
import com.example.studyplanner.models.TaskModel
import com.example.studyplanner.models.relationships.SubjectWithTasksModel

class AppRepository(private val appDao: AppDao) : AppRepositoryInterface {

    override val subjects: LiveData<List<SubjectModel>>
        get() = appDao.getAllSubjects()

    override val subjectsWithTasks: LiveData<List<SubjectWithTasksModel>>
        get() = appDao.getAllSubjectsWithTasks()

    override val tasks: LiveData<List<TaskView>>
        get() = appDao.getAllTasks()

    override fun getDeadlineSoonTasks(days: Int): LiveData<List<TaskView>> {
        return appDao.getDeadlineSoonTasks(days)
    }

    override fun getDeadlineNotSoonTasks(days: Int): LiveData<List<TaskView>> {
        return appDao.getDeadlineNotSoonTasks(days)
    }

    override fun getTasksWithSubjectId(subjectId: Int): LiveData<List<TaskModel>> {
        return appDao.getTasksWithSubjectId(subjectId)
    }

    override suspend fun insertSubject(subjectModel: SubjectModel, onSuccess: () -> Unit) {
        appDao.insertSubject(subjectModel)
        onSuccess()
    }

    override suspend fun insertTask(taskModel: TaskModel, onSuccess: () -> Unit) {
        appDao.insertTask(taskModel)
        onSuccess()
    }

    override suspend fun deleteSubject(subjectModel: SubjectModel, onSuccess: () -> Unit) {
        appDao.deleteSubject(subjectModel)
        onSuccess()
    }

    override suspend fun deleteTask(taskModel: TaskModel, onSuccess: () -> Unit) {
        appDao.deleteTask(taskModel)
        onSuccess()
    }

    override suspend fun updateTask(taskModel: TaskModel, onSuccess: () -> Unit) {
        appDao.updateTask(taskModel)
        onSuccess()
    }

    override suspend fun updateSubject(subjectModel: SubjectModel, onSuccess: () -> Unit) {
        appDao.updateSubject(subjectModel)
        onSuccess()
    }
}