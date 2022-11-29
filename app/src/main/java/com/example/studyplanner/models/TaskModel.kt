package com.example.studyplanner.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.studyplanner.models.TaskModel.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = TABLE_NAME, foreignKeys = [ForeignKey(
        entity = SubjectModel::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("subject_id"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class TaskModel (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "subject_id")
    var subjectId: Int,

    @ColumnInfo
    var name: String,

    @ColumnInfo
    var description: String?,

    @ColumnInfo
    var deadline: Date
) : Parcelable {
    companion object {
        const val TABLE_NAME = "tasks"
    }
}