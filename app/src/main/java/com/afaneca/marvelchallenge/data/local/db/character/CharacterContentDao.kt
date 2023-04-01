package com.afaneca.marvelchallenge.data.local.db.character

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterContentDao {

    @Query("SELECT * FROM character_content WHERE type = :type AND character_id = :characterId")
    suspend fun getCharacterContent(characterId: Int, type: String): List<CharacterContentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dataset: List<CharacterContentEntity>): LongArray
}