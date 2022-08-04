package com.example.contactlist.di

import android.content.Context
import androidx.room.Room
import com.example.contactlist.data.database.ContactDatabase
import com.example.contactlist.data.repository.ContactRepository
import com.example.contactlist.data.repository.ContactRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContactDatabase(@ApplicationContext app: Context): ContactDatabase {
        return Room.databaseBuilder(
            app,
            ContactDatabase::class.java,
            ContactDatabase.name
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideContactRepository(db: ContactDatabase): ContactRepository {
        return ContactRepositoryImpl(db.dao)
    }
}