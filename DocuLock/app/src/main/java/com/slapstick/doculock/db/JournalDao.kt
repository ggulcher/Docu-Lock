package com.slapstick.doculock.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addJournal(journal: Journal)

    @Update
    suspend fun updateJournal(journal: Journal)

    @Delete
    suspend fun deleteJournal(journal: Journal)

    @Query("DELETE FROM journal_table")
    suspend fun deleteAllJournals()

    @Query("SELECT * FROM journal_table ORDER BY id ASC")
    fun readAllJournals(): LiveData<List<Journal>>

    @Query("SELECT * FROM journal_table WHERE title LIKE :searchQuery OR content LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<Journal>>

}