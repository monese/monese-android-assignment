package com.monese.assignment.data.repository

import com.monese.assignment.data.Response
import com.monese.assignment.data.model.Launch

interface LaunchesRepository {
    suspend fun getLaunches(forceUpdate: Boolean = false): Response<List<Launch>>
}