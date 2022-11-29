package com.example.studyplanner.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.studyplanner.db.views.TaskView
import com.example.studyplanner.models.SubjectModel
import com.example.studyplanner.models.TaskModel
import com.example.studyplanner.models.relationships.SubjectWithTasksModel

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSubject(subjectModel: SubjectModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(taskModel: TaskModel)

    @Delete
    suspend fun deleteSubject(subjectModel: SubjectModel)

    @Delete
    suspend fun deleteTask(taskModel: TaskModel)

    @Update
    suspend fun updateSubject(subjectModel: SubjectModel)

    @Update
    suspend fun updateTask(taskModel: TaskModel)

    @Query("SELECT * FROM ${SubjectModel.TABLE_NAME}")
    fun getAllSubjects(): LiveData<List<SubjectModel>>

    @Query("SELECT * FROM ${TaskView.VIEW_NAME}")
    fun getAllTasks(): LiveData<List<TaskView>>

    @Query("SELECT * FROM ${TaskModel.TABLE_NAME} WHERE subject_id = :subjectId")
    fun getTasksWithSubjectId(subjectId: Int): LiveData<List<TaskModel>>

    @Query("SELECT * FROM ${TaskView.VIEW_NAME} WHERE (deadline - strftime('%s', 'now') * 1000) " +
            "<= (:days * 24 * 60 * 60 * 1000) ORDER BY deadline")
    fun getDeadlineSoonTasks(days: Int): LiveData<List<TaskView>>

    @Query("SELECT * FROM ${TaskView.VIEW_NAME} WHERE (deadline - strftime('%s', 'now') * 1000) " +
            "> (:days * 24 * 60 * 60 * 1000) ORDER BY deadline")
    fun getDeadlineNotSoonTasks(days: Int): LiveData<List<TaskView>>

    @Transaction
    @Query("SELECT * FROM ${SubjectModel.TABLE_NAME}")
    fun getAllSubjectsWithTasks(): LiveData<List<SubjectWithTasksModel>>
}