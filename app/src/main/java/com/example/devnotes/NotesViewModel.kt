package com.example.devnotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotesViewModel : ViewModel() {

    private var currentNotes = listOf<Note>()
    private val _notes: MutableLiveData<List<Note>> = MutableLiveData(emptyList())
    val notes: LiveData<List<Note>> = _notes

    fun remove(note: Note) {
        val newList = _notes.value.orEmpty().toMutableList()
        newList.remove(note)
        _notes.value = newList
        currentNotes = newList
    }

    fun add(vararg notes: Note) {
        val newList = _notes.value.orEmpty().toMutableList()
        newList.addAll(notes)
        _notes.value = newList
        currentNotes = newList
    }

    fun searchNote(key: String) {
        if (key.isNotEmpty() && key.isNotBlank()) {
            _notes.value = currentNotes.filter { it.title.contains(key, ignoreCase = true) || it.body.contains(key, ignoreCase = true) }
        } else {
            _notes.value = currentNotes
        }
    }
}