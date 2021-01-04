package com.example.desafiopebmed.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ItemEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(RoomConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun itemListDao(): ItemListDao
}