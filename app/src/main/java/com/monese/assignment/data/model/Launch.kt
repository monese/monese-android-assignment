package com.monese.assignment.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "launches")
data class Launch @JvmOverloads constructor(
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "date") var date: Long = 1L,
    @ColumnInfo(name = "success") var isSuccess: Boolean = false,
    @PrimaryKey @ColumnInfo(name = "entry_id") var id: String = UUID.randomUUID().toString()
)