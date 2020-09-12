package com.monese.assignment.di

import android.content.Context
import androidx.room.Room
import com.monese.assignment.data.repository.LaunchesRepository
import com.monese.assignment.data.repository.LaunchesRepositoryImpl
import com.monese.assignment.data.source.LaunchesDataSource
import com.monese.assignment.data.source.local.AssignmentDatabase
import com.monese.assignment.data.source.local.LaunchesLocalDataSource
import com.monese.assignment.data.source.remote.LaunchesRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LaunchesRemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LaunchesLocalDataSource

    @Singleton
    @LaunchesRemoteDataSource
    @Provides
    fun provideLaunchesRemoteDataSource(): LaunchesDataSource {
        return LaunchesRemoteDataSource
    }

    @Singleton
    @LaunchesLocalDataSource
    @Provides
    fun provideLaunchesLocalDataSource(
        database: AssignmentDatabase,
        ioDispatcher: CoroutineDispatcher
    ): LaunchesDataSource {
        return LaunchesLocalDataSource(
            database.launchesDao(), ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AssignmentDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AssignmentDatabase::class.java,
            "Assignment.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}

@Module
@InstallIn(ApplicationComponent::class)
object LaunchesRepositoryModule {

    @Singleton
    @Provides
    fun provideLaunchesRepository(
        @AppModule.LaunchesRemoteDataSource remoteLaunchesDataSource: LaunchesDataSource,
        @AppModule.LaunchesLocalDataSource localLaunchesDataSource: LaunchesDataSource
    ): LaunchesRepository {
        return LaunchesRepositoryImpl(
            remoteLaunchesDataSource, localLaunchesDataSource
        )
    }
}