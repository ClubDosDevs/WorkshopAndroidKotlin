package com.example.devnotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.devnotes.databinding.ItemViewNoteBinding

class NotesAdapter(
    private val onDeleteClickListener: (note: Note) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    var items: List<Note> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class NotesViewHolder(val binding: ItemViewNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.title.text = note.title
            binding.body.text = note.body
            binding.btnDelete.setOnClickListener {
                onDeleteClickListener(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemViewNoteBinding.inflate(parent.inflater, parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    val ViewGroup.inflater: LayoutInflater
        get() = LayoutInflater.from(context)
}