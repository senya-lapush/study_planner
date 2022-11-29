package com.example.studyplanner.db.views

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded
import com.example.studyplanner.db.views.TaskView.Companion.VIEW_NAME
import com.example.studyplanner.models.SubjectModel
import com.example.studyplanner.models.TaskModel
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@DatabaseView("SELECT tasks.id, tasks.subject_id, tasks.name, tasks.description, tasks.deadline, " +
        "subjects.name as subject_name FROM ${SubjectModel.TABLE_NAME} as subjects " +
        "JOIN ${TaskModel.TABLE_NAME} as tasks ON subjects.id = tasks.subject_id", viewName =  VIEW_NAME)
data class TaskView (
    @Embedded
    val task: TaskModel,

    @ColumnInfo(name = "subject_name")
    val subjectName: String,

) : Parcelable {
    companion object {
        const val VIEW_NAME = "tasks_view"
    }
}