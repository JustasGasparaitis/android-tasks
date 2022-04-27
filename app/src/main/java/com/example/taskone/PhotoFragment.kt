package com.example.taskone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.taskone.databinding.FragmentPhotoBinding

class PhotoFragment : Fragment(R.layout.fragment_photo) {
    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!

    // When navigating to PhotoFragment, it is required to pass an URL to an image
    private val args by navArgs<PhotoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadImage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadImage() {
        Glide.with(requireContext())
            .load(args.imageUrl)
            .into(binding.imageView)
    }
}