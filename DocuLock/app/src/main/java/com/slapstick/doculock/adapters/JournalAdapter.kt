package com.slapstick.doculock.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.slapstick.doculock.R
import com.slapstick.doculock.db.Journal
import com.slapstick.doculock.ui.JournalFragmentDirections
import kotlinx.android.synthetic.main.journal_item.view.*

class JournalAdapter: RecyclerView.Adapter<JournalAdapter.MyViewHolder>() {

    private var journalList = emptyList<Journal>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.journal_item, parent, false))
    }

    override fun getItemCount(): Int {
        return journalList.size
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        val currentJournal = journalList[position]
        holder.itemView.tv_journalItemTitle.text = currentJournal.title
        holder.itemView.tv_jounralItemContent.text = currentJournal.content

        when(currentJournal.color) {
            "None" -> holder.itemView.view_journalItemColor.setBackgroundResource(R.color.note_color_default)
            "Red" -> holder.itemView.view_journalItemColor.setBackgroundResource(R.color.note_color_red)
            "Yellow" -> holder.itemView.view_journalItemColor.setBackgroundResource(R.color.note_color_yellow)
            "Blue" -> holder.itemView.view_journalItemColor.setBackgroundResource(R.color.note_color_blue)
            "Black" -> holder.itemView.view_journalItemColor.setBackgroundResource(R.color.note_color_black)
        }
        
        holder.itemView.cl_journalItem.setOnClickListener {
            val action = JournalFragmentDirections
                .actionJournalFragmentToUpdateJournalFragment(currentJournal)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(journal: List<Journal>) {
        journalList = journal
        notifyDataSetChanged()
    }

}