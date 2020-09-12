package com.monese.assignment.data.source

import com.monese.assignment.data.Response
import com.monese.assignment.data.model.Launch

interface LaunchesDataSource {

    suspend fun getLaunches(): Response<List<Launch>>
    suspend fun saveLaunch(launch: Launch) {}
    suspend fun deleteLaunches() {}
}