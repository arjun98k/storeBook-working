package com.example.mybookstore.ui.bookdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.mybookstore.databinding.FragmentBookDetailBinding

class BookDetailFragment : Fragment() {

    private var _binding: FragmentBookDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val title = arguments?.getString("title") ?: "No title"
        val author = arguments?.getString("author") ?: "Unknown author"
        val description = arguments?.getString("description") ?: "No description"
        val thumbnail = arguments?.getString("thumbnail")

        binding.titleText.text = title
        binding.authorText.text = author
        binding.descriptionText.text = description

        Glide.with(requireContext())
            .load(thumbnail)
            .into(binding.bookImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
