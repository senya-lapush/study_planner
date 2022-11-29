package com.example.studyplanner.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.studyplanner.models.SubjectModel.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class SubjectModel (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Int = 0,

    @ColumnInfo
    var name: String
) : Parcelable {
    companion object {
        const val TABLE_NAME = "subjects"
    }
}