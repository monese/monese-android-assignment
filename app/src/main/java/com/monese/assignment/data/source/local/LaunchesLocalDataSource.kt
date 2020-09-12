package com.monese.assignment.data.source.local

import com.monese.assignment.data.Response
import com.monese.assignment.data.Response.Error
import com.monese.assignment.data.Response.Success
import com.monese.assignment.data.model.Launch
import com.monese.assignment.data.source.LaunchesDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LaunchesLocalDataSource internal constructor(
    private val launchesDao: LaunchesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LaunchesDataSource {

    override suspend fun getLaunches(): Response<List<Launch>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(launchesDao.readLaunches())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun saveLaunch(launch: Launch) = withContext(ioDispatcher) {
        launchesDao.createLaunch(launch)
    }

    override suspend fun deleteLaunches() {
        launchesDao.deleteLaunches()
    }
}