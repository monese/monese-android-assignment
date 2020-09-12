package com.monese.assignment.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.monese.assignment.data.model.Launch

@Dao
interface LaunchesDao {

    @Query("SELECT * FROM Launches")
    suspend fun readLaunches(): List<Launch>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createLaunch(launch: Launch)

    @Query("DELETE FROM Launches")
    suspend fun deleteLaunches()
}