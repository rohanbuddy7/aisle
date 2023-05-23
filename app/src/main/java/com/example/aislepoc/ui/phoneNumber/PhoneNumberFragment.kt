package com.example.aislepoc.ui.phoneNumber

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import com.example.aislepoc.R
import com.example.aislepoc.databinding.FragmentPhoneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneNumberFragment : Fragment() {

    private var _binding: FragmentPhoneBinding? = null
    private val binding: FragmentPhoneBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.bttnContinue.setOnClickListener {
            proceed()
        }
    }

    fun proceed(){
        //binding.etCode.text.toString() + " " + binding.etPhone.text.toString()
        val phone = String.format("%s %s",binding.etCode.text.toString(), binding.etPhone.text.toString() )
        val bundle = Bundle();
        bundle.putString("phone", phone)
        findNavController().navigate(R.id.action_phoneFragment_to_OTPFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = PhoneNumberFragment()
    }

}