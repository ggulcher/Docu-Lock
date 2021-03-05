package com.slapstick.doculock.db

import androidx.lifecycle.LiveData

class JournalRepository(private val journalDao: JournalDao) {

    val readAllJournals: LiveData<List<Journal>> = journalDao.readAllJournals()

    // Journal Entries
    suspend fun addJournal(journal: Journal) { journalDao.addJournal(journal) }

    suspend fun updateJournal(journal: Journal) { journalDao.updateJournal(journal) }

    suspend fun deleteJournal(journal: Journal) { journalDao.deleteJournal(journal) }

    suspend fun deleteAllJournals() { journalDao.deleteAllJournals() }

    fun searchDatabase(searchQuery: String): LiveData<List<Journal>> {
        return journalDao.searchDatabase(searchQuery)
    }

}