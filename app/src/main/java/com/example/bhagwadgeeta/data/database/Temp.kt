package com.example.bhagwadgeeta.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geeta_database")
data class Temp(@PrimaryKey var name: String)