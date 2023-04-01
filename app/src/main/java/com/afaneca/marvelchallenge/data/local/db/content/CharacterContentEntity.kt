package com.afaneca.marvelchallenge.data.local.db.content

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "character_content", primaryKeys = ["name", "character_id"])
class CharacterContentEntity(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "character_id")
    val characterId: Int,
)