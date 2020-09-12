package com.monese.assignment.data.source.remote

import com.monese.assignment.data.Response
import com.monese.assignment.data.model.Launch
import com.monese.assignment.data.source.LaunchesDataSource
import kotlinx.coroutines.delay

object LaunchesRemoteDataSource : LaunchesDataSource {

    private const val SERVICE_LATENCY_IN_MILLIS = 3000L

    private var launches = LinkedHashMap<String, Launch>(2)

    init {
        addLaunch("Launch 1", 1599137160, true)
        addLaunch("Launch 2", 1222643700, false)
        addLaunch("Launch 3", 1143239500, false)
        addLaunch("Launch 4", 1143239500, true)
        addLaunch("Launch 5", 1143239500, false)
        addLaunch("Launch 6", 1143239500, true)
        addLaunch("Launch 7", 1143239500, false)
    }

    override suspend fun getLaunches(): Response<List<Launch>> {
        val launches = launches.values.toList()
        delay(SERVICE_LATENCY_IN_MILLIS)
        return Response.Success(launches)
    }

    private fun addLaunch(name: String, date: Long, success: Boolean) {
        val launch = Launch(name = name, date = date, isSuccess = success)
        launches[launch.id] = launch
    }
}