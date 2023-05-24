package com.example.aislepoc.ui.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aislepoc.R
import com.example.aislepoc.data.auto.ProfileResponse
import com.example.aislepoc.databinding.FragmentNotesBinding
import com.example.aislepoc.ui.main.MainActivity
import com.example.aislepoc.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesFragment : Fragment() {
    private var token: String? = null
    private val viewmodel by viewModels<NotesViewModel>()
    private var _binding: FragmentNotesBinding? = null
    private val binding: FragmentNotesBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            token = it.getString("token")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        setupListeners()
        setupObservers()

        token?.let {
            viewmodel.getProfile(it)
        }
    }

    private fun setup() {
        (activity as MainActivity).showBottomNav(true)
    }

    private fun setupListeners() {

    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.profileResponse.collect() {
                when (it) {
                    is NetworkResult.Success -> {
                        handle(it)
                    }

                    is NetworkResult.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }

                    is NetworkResult.Loading -> {}
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun handle(result: NetworkResult<ProfileResponse>) {
        val profiles = result.data?.invites?.profiles
        if (profiles?.isNotEmpty() == true) {
            val photos = profiles[0]?.photos
            if (photos?.isNotEmpty() == true) {
                for (p in photos) {
                    if (p?.selected == true) {
                        Glide.with(requireContext()).load(p.photo).into(binding.ivProfile)
                    }
                }
            }
            binding.tvName.text = String.format(
                "%S, %S",
                profiles[0]?.generalInformation?.firstName,
                profiles[0]?.generalInformation?.age
            )
        }

        val likeProfiles = result.data?.likes?.profiles
        val canSeeProfile = result.data?.likes?.canSeeProfile
        var requestOptions = RequestOptions()
        if(canSeeProfile == false){
            requestOptions = RequestOptions.bitmapTransform(BlurTransformation(25, 3))
        }
        if (likeProfiles?.isNotEmpty() == true) {
            for (i in likeProfiles.indices) {
                val profile = likeProfiles[i]
                when (i) {
                    0 -> {
                        Glide.with(requireContext()).load(profile?.avatar)
                            .apply(requestOptions)
                            .into(binding.layoutProfile1.ivProfile)
                        binding.layoutProfile1.tvName.text = profile?.firstName
                    }
                    1 -> {
                        Glide.with(requireContext()).load(profile?.avatar)
                            .apply(requestOptions)
                            .into(binding.layoutProfile2.ivProfile)
                        binding.layoutProfile2.tvName.text = profile?.firstName
                    }
                }
            }
        }
    }
}