package com.afaneca.marvelchallenge.data.local.db.character

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters WHERE page = :page AND search_query = :searchQuery")
    suspend fun getCharacters(page: Int, searchQuery: String = ""): List<CharacterDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dataset: List<CharacterDbEntity>): LongArray
}