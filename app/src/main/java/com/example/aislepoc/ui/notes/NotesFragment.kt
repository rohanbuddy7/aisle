package com.example.aislepoc.ui.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.aislepoc.R
import com.example.aislepoc.ui.main.MainActivity
import com.example.aislepoc.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesFragment : Fragment() {
    private var token: String? = null
    private val viewmodel by viewModels<NotesViewModel>()

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
        return inflater.inflate(R.layout.fragment_notes, container, false)
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
            viewmodel.profileResponse.collect(){
                when(it){
                    is NetworkResult.Success->{
                        print(it.data?.invites)
                    }
                    is NetworkResult.Error->{
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    is NetworkResult.Loading->{}
                }
            }
        }
    }
}