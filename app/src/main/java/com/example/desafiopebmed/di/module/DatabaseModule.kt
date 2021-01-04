package com.example.desafiopebmed.di.module


import android.app.Application
import androidx.room.Room
import com.example.desafiopebmed.source.local.Database
import com.example.desafiopebmed.source.local.ItemListDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    companion object {
        private const val DATA_BASE_NAME = "medbook.db"
    }

    @Singleton
    @Provides
    fun providesRoomDatabase(application: Application): Database = Room
        .databaseBuilder(application, Database::class.java, DATA_BASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providesItemListDao(database: Database): ItemListDao {
        return database.itemListDao()
    }
}