package com.afaneca.marvelchallenge.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.afaneca.marvelchallenge.data.local.db.character.CharacterDao
import com.afaneca.marvelchallenge.data.local.db.character.CharacterDbEntity

@Database(entities = [CharacterDbEntity::class], version = 1)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}