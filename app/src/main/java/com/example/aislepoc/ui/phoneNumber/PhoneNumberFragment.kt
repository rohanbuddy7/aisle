package com.example.aislepoc.ui.phoneNumber

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import com.example.aislepoc.R
import com.example.aislepoc.databinding.FragmentPhoneBinding
import com.example.aislepoc.ui.otp.OtpViewModel
import com.example.aislepoc.utils.Constants
import com.example.aislepoc.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhoneNumberFragment : Fragment() {

    private val viewmodel by viewModels<PhoneViewModel>()
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
        setupObservers()
    }

    private fun setupListeners() {
        binding.bttnContinue.setOnClickListener {
            val phone = binding.etCode.text.toString() + binding.etPhone.text.toString()
            viewmodel.getOtp(phone)
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.phoneResponse.collect(){
                when(it){
                    is NetworkResult.Success->{
                        if(it.data!!.status){
                            proceed()
                        } else {
                            Toast.makeText(requireContext(), "Status is false, Please try again later", Toast.LENGTH_LONG).show()
                        }
                    }
                    is NetworkResult.Error->{
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    is NetworkResult.Loading->{}
                }
            }
        }
    }

    fun proceed(){
        val bundle = Bundle();
        bundle.putString("phone", binding.etPhone.text.toString())
        bundle.putString("code", binding.etCode.text.toString())
        findNavController().navigate(R.id.action_phoneFragment_to_OTPFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}