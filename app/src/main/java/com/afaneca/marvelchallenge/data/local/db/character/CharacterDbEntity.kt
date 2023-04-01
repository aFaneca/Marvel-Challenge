package com.afaneca.marvelchallenge.data.local.db.character

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "characters", primaryKeys = ["id", "search_query"])
class CharacterDbEntity(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String?,
    @ColumnInfo(name = "page")
    val page: Int,
    @ColumnInfo(name = "search_query")
    val searchQuery: String = "",
)