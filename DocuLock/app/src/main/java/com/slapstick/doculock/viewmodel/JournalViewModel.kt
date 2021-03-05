package com.slapstick.doculock.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.slapstick.doculock.db.Journal
import com.slapstick.doculock.db.JournalDatabase
import com.slapstick.doculock.db.JournalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JournalViewModel(application: Application): AndroidViewModel(application) {

    val readAllJournals: LiveData<List<Journal>>
    private val repository: JournalRepository

    init {
        val journalDao = JournalDatabase.getDatabase(application).journalDao()
        repository = JournalRepository(journalDao)
        readAllJournals = repository.readAllJournals
    }

    fun addJournal(journal: Journal) {
        viewModelScope.launch(Dispatchers.IO) { repository.addJournal(journal) }
    }

    fun updateJournal(journal: Journal) {
        viewModelScope.launch(Dispatchers.IO) { repository.updateJournal(journal) }
    }

    fun deleteJournal(journal: Journal) {
        viewModelScope.launch(Dispatchers.IO) { repository.deleteJournal(journal) }
    }

    fun deleteAllJournals() {
        viewModelScope.launch(Dispatchers.IO) { repository.deleteAllJournals() }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Journal>>{
        return repository.searchDatabase(searchQuery)
    }

}