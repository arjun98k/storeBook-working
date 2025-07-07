package com.example.mybookstore.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybookstore.databinding.HomeFragmentBinding
import com.example.mybookstore.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var bookAdapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerViews()
        observeViewModel()

        // Fetch example query initially
        viewModel.fetchBooks("android")
    }

    private fun setupRecyclerViews() {
        bookAdapter = BookAdapter()
        binding.recommendedRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recommendedRecycler.adapter = bookAdapter

        binding.newReleasesRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.newReleasesRecycler.adapter = bookAdapter
    }

    private fun observeViewModel() {
        viewModel.books.observe(viewLifecycleOwner) {
            bookAdapter.submitList(it)
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.recommendedTitle.text = if (it) "Loading..." else "Recommended for you"
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding.recommendedTitle.text = it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
