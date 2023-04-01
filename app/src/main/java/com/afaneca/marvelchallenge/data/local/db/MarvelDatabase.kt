package com.afaneca.marvelchallenge.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.afaneca.marvelchallenge.data.local.db.character.CharacterDao
import com.afaneca.marvelchallenge.data.local.db.character.CharacterDbEntity
import com.afaneca.marvelchallenge.data.local.db.content.CharacterContentDao
import com.afaneca.marvelchallenge.data.local.db.content.CharacterContentEntity

@Database(entities = [CharacterDbEntity::class, CharacterContentEntity::class], version = 2)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun characterContentDao(): CharacterContentDao
}