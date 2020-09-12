package com.monese.assignment.data.repository

import com.monese.assignment.data.Response
import com.monese.assignment.data.Response.Error
import com.monese.assignment.data.Response.Success
import com.monese.assignment.data.model.Launch
import com.monese.assignment.data.source.LaunchesDataSource

class LaunchesRepositoryImpl(
    private val remoteDataSource: LaunchesDataSource,
    private val localDataSource: LaunchesDataSource
) : LaunchesRepository {

    override suspend fun getLaunches(forceUpdate: Boolean): Response<List<Launch>> {
        if (forceUpdate) {
            try {
                updateLaunchesFromRemoteDataSource()
            } catch (ex: Exception) {
                return Error(ex)
            }
        }

        return localDataSource.getLaunches()
    }

    private suspend fun updateLaunchesFromRemoteDataSource() {
        val remoteLaunches = remoteDataSource.getLaunches()

        if (remoteLaunches is Success) {
            localDataSource.deleteLaunches()
            remoteLaunches.data.forEach { launch ->
                localDataSource.saveLaunch(launch)
            }
        } else if (remoteLaunches is Error) {
            throw remoteLaunches.exception
        }
    }
}