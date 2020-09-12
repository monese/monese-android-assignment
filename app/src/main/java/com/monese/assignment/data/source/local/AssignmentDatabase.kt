package com.monese.assignment.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.monese.assignment.data.model.Launch

@Database(entities = [Launch::class], version = 1, exportSchema = false)
abstract class AssignmentDatabase : RoomDatabase() {

    abstract fun launchesDao(): LaunchesDao
}