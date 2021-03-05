package com.slapstick.doculock.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.slapstick.doculock.R
import com.slapstick.doculock.db.Journal
import com.slapstick.doculock.viewmodel.JournalViewModel
import kotlinx.android.synthetic.main.fragment_add_journal.*

class AddJournalFragment : Fragment() {

    private lateinit var viewModel: JournalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_journal, container, false)

        viewModel = ViewModelProvider(this).get(JournalViewModel::class.java)

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_add) {
            insertDataToDatabase()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDatabase() {
        val journalTitle = et_titleJournal.text.toString()
        val journalContent = et_contentJournal.text.toString()
        val journalColor = spin_colorJournal.selectedItem.toString()

        if(inputCheck(journalTitle, journalContent)) {
            val journal = Journal(0, journalTitle, journalContent, journalColor)
            viewModel.addJournal(journal)
            Toast.makeText(requireContext(), "New Entry Created!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addJournalFragment_to_journalFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_LONG).show()
        }

    }

    private fun inputCheck(journalTitle: String, journalContent: String): Boolean {
        return !(TextUtils.isEmpty(journalTitle) && TextUtils.isEmpty(journalContent))
    }

}