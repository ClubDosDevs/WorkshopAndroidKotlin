package com.example.devnotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.devnotes.databinding.FragmentNewNotesBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class NewNotesFragment : Fragment() {

    private val viewModel by activityViewModels<NotesViewModel>()
    private lateinit var binding: FragmentNewNotesBinding
    val fbutton: ExtendedFloatingActionButton
        get() = (activity as? MainActivity)?.fbutton!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fbutton.apply {
            icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_save)
            text = context.getString(R.string.action_save)
            isEnabled = false
            setOnClickListener {
                val title = binding.etTitle.text.toString()
                val body = binding.etBody.text.toString()
                viewModel.add(
                    Note(title, body)
                )
                activity?.onBackPressed()
            }
        }
        binding.toolbar2.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.etTitle.doOnTextChanged { _, _, _, count ->
            val bodyText = binding.etBody.text
            val hasBody = bodyText.isNotEmpty() && bodyText.isNotBlank()
            if (count > 0 && hasBody) {
                fbutton.extend()
                fbutton.isEnabled = true
            } else {
                fbutton.shrink()
                fbutton.isEnabled = false
            }
        }

        binding.etBody.doOnTextChanged { _, _, _, count ->
            val bodyTile = binding.etTitle.text
            val hasTitle = bodyTile.isNotEmpty() && bodyTile.isNotBlank()
            if (count > 0 && hasTitle) {
                fbutton.extend()
                fbutton.isEnabled = true
            } else {
                fbutton.shrink()
                fbutton.isEnabled = false
            }
        }
    }
}