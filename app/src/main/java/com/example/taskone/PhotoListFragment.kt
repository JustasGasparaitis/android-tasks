package com.example.taskone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.taskone.Constants.PHOTO_LIST_SPAN_COUNT
import com.example.taskone.databinding.FragmentPhotoListBinding
import com.google.gson.Gson

class PhotoListFragment : Fragment(R.layout.fragment_photo_list) {
    private var _binding: FragmentPhotoListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        val photoListAdapter = PhotoListAdapter().apply {
            items = getUrlsFromAssets()
            setOnClickListener(
                singleClickListener = { url: String ->
                    // Show enlarged photo in new fragment
                    findNavController().navigate(
                        PhotoListFragmentDirections.actionPhotoListFragmentToPhotoFragment(url)
                    )
                }
            )
        }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, PHOTO_LIST_SPAN_COUNT)
            adapter = photoListAdapter
        }
    }

    private fun getUrlsFromAssets(): List<String> {
        // Our dog_urls.json file is placed in res/raw
        val json = resources.openRawResource(R.raw.dog_urls).bufferedReader().use { it.readText() }
        val gson = Gson()
        return gson.fromJson(
            json,
            DogUrls::class.java
        ).urls
    }
}