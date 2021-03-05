package com.slapstick.doculock.ui

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.slapstick.doculock.R
import com.slapstick.doculock.db.Journal
import com.slapstick.doculock.viewmodel.JournalViewModel
import kotlinx.android.synthetic.main.fragment_update_journal.*
import kotlinx.android.synthetic.main.fragment_update_journal.view.*

class UpdateJournalFragment : Fragment() {

    private val args by navArgs<UpdateJournalFragmentArgs>()
    private lateinit var viewModel: JournalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_journal, container, false)

        viewModel = ViewModelProvider(this).get(JournalViewModel::class.java)

        view.et_titleJournalUpdate.setText(args.currentJournal.title)
        view.et_contentJournalUpdate.setText(args.currentJournal.content)

//        view.btn_updateJournal.setOnClickListener {
//            updateJournal()
//        }

        setHasOptionsMenu(true)

        return view
    }

    private fun updateJournal() {
        val journalTitle = et_titleJournalUpdate.text.toString()
        val journalContent = et_contentJournalUpdate.text.toString()
        val journalColor = spin_colorJournalUpdate.selectedItem.toString()

        if(inputCheck(journalTitle, journalContent)) {
            val updatedJournal = Journal(args.currentJournal.id, journalTitle, journalContent, journalColor)
            viewModel.updateJournal(updatedJournal)
            findNavController().navigate(R.id.action_updateJournalFragment_to_journalFragment)
            Toast.makeText(requireContext(), "Entry Updated Successfully!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(journalTitle: String, journalContent: String): Boolean {
        return !(TextUtils.isEmpty(journalTitle) && TextUtils.isEmpty(journalContent))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_add) {
            updateJournal()
        }
        if(item.itemId == R.id.menu_delete) {
            deleteJournal()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteJournal() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_,_ ->
            viewModel.deleteJournal(args.currentJournal)
            Toast.makeText(requireContext(), "Entry ${args.currentJournal.title} Deleted",
                Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateJournalFragment_to_journalFragment)
        }
        builder.setNegativeButton("No") {_,_ -> }
        builder.setTitle("Delete ${args.currentJournal.title}?")
        builder.setMessage("Are you sure you want to delete ${args.currentJournal.title}")
        builder.create().show()
    }

}