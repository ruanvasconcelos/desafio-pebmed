package com.example.desafiopebmed.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface ItemListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<ItemEntity>)

    @Query("SELECT * FROM medicalList")
    fun getAll(): Single<List<ItemEntity>>

    @Query("DELETE FROM medicalList")
    fun deleteAll()
}