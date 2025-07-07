package com.example.mybookstore.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybookstore.databinding.SearchFragmentBinding
import com.example.mybookstore.ui.home.BookAdapter
import com.example.mybookstore.viewmodel.HomeViewModel

class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = BookAdapter()
        binding.searchHistoryContainer.visibility = View.GONE // hide static history initially

        binding.searchEditText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = v.text.toString()
                if (query.isNotEmpty()) {
                    viewModel.fetchBooks(query)
                }
                true
            } else {
                false
            }
        }

        binding.searchBoxContainer.setOnClickListener {
            binding.searchHistoryContainer.visibility = View.VISIBLE
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.books.observe(viewLifecycleOwner) { books ->
            // Handle search results (could add a RecyclerView here if you like)
        }
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            // Handle loading UI if needed
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            // Handle error
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
