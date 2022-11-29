package com.example.studyplanner.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.studyplanner.converters.Converters
import com.example.studyplanner.db.dao.AppDao
import com.example.studyplanner.db.views.TaskView
import com.example.studyplanner.models.SubjectModel
import com.example.studyplanner.models.TaskModel
import com.example.studyplanner.models.relationships.SubjectWithTasksModel

@Database(entities = [SubjectModel::class, TaskModel::class],
            views = [TaskView::class], version = 7)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getSubjectDao(): AppDao

    companion object {
        private var database: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            return if (database == null) {
                database = Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
                database as AppDatabase
            } else {
                database as AppDatabase
            }
        }
    }
}