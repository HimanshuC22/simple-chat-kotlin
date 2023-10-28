package com.example.simplechat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonProperty
import java.sql.Date
import java.sql.Timestamp

@Entity(tableName = "user")
data class User(
    @JsonProperty("id")
    @PrimaryKey
    val id: String,
    @JsonProperty("firstName")
    @ColumnInfo("first_name")
    val firstName: String?,
    @JsonProperty("lastName")
    @ColumnInfo(name = "last_name")
    val lastName: String?,
    @JsonProperty("email")
    @ColumnInfo(name = "email")
    val email: String?,
    @JsonProperty("createdAt")
    @ColumnInfo("created_at")
    val createdAt: Date?,
    @JsonProperty("modifiedAt")
    @ColumnInfo("modified_at")
    val modifiedAt: Date?,
)
