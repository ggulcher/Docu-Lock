package com.slapstick.doculock.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.slapstick.doculock.R
import com.slapstick.doculock.adapters.JournalAdapter
import com.slapstick.doculock.viewmodel.JournalViewModel
import kotlinx.android.synthetic.main.fragment_journal.*
import kotlinx.android.synthetic.main.fragment_journal.view.*

class JournalFragment : Fragment(), SearchView.OnQueryTextListener{

    private val viewModel: JournalViewModel by viewModels()
    private val adapter: JournalAdapter by lazy { JournalAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_journal, container, false)

//        val adapter = JournalAdapter()
        val recyclerView = view.rv_journalRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

//        viewModel = ViewModelProvider(this).get(JournalViewModel::class.java)
        viewModel.readAllJournals.observe(viewLifecycleOwner, Observer { journal ->
            adapter.setData(journal)
            tv_empty.visibility = if (journal.isEmpty()) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        })

        view.fab_journalAddButton.setOnClickListener {
            findNavController().navigate(R.id.action_journalFragment_to_addJournalFragment)
        }

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deleteAllJournals()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllJournals() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_,_ ->
            viewModel.deleteAllJournals()
            Toast.makeText(requireContext(), "All Entries Deleted",
                Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("No") {_,_ -> }
        builder.setTitle("Delete all entries?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        viewModel.searchDatabase(searchQuery).observe(this, { list ->
            list.let {
                adapter.setData(it)
            }
        })
    }

}