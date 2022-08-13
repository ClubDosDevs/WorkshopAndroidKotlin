package com.example.devnotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.devnotes.databinding.FragmentHomeBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class HomeFragment : Fragment() {

    private val viewModel by activityViewModels<NotesViewModel>()
    private lateinit var adapter: NotesAdapter
    private lateinit var binding: FragmentHomeBinding
    val fbutton: ExtendedFloatingActionButton
        get() = (activity as? MainActivity)?.fbutton!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fbutton.shrink()
        fbutton.isEnabled = true
        fbutton.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_add)
        fbutton.setOnClickListener {
            parentFragmentManager.commit {
                addToBackStack(null)
                replace(R.id.nav_container, NewNotesFragment())
            }
        }

        binding.rvNotes.layoutManager = GridLayoutManager(
            requireContext(), 2, RecyclerView.VERTICAL, false
        )
        adapter = NotesAdapter { note: Note ->
            viewModel.remove(note)
        }
        binding.rvNotes.adapter = adapter

        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.searchNote(text.toString())
        }

        viewModel.notes.observe(viewLifecycleOwner) { notes: List<Note> ->
            adapter.items = notes
        }
    }

    companion object {
        const val TITLE_KEY = "TITLE_KEY"

        fun newInstance(text: String) : HomeFragment {
            val fragment = HomeFragment()
            fragment.arguments = Bundle().apply {
                putString(TITLE_KEY, text)
            }
            return fragment
        }
    }
}