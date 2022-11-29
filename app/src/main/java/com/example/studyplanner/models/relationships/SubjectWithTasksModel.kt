package com.example.studyplanner.models.relationships

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.example.studyplanner.models.SubjectModel
import com.example.studyplanner.models.TaskModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubjectWithTasksModel (
    @Embedded
    var subject: SubjectModel,

    @Relation(
        parentColumn = "id",
        entityColumn = "subject_id"
    )
    var tasks: List<TaskModel>
) : Parcelable