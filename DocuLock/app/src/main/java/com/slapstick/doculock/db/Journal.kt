package com.slapstick.doculock.db

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
@Entity(tableName = "journal_table")
class Journal(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val content: String,
    val color: String
): Parcelable