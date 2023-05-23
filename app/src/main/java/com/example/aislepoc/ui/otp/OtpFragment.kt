package com.example.aislepoc.ui.otp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.aislepoc.R
import com.example.aislepoc.databinding.FragmentOtpBinding
import com.example.aislepoc.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class OtpFragment : Fragment() {

    private val viewmodel by viewModels<OtpViewModel>();
    private var countDownTimer: CountDownTimer? = null
    private var code: String? = null
    private var phone: String? = null
    private var _binding: FragmentOtpBinding? = null
    private val binding: FragmentOtpBinding
        get() {
            return _binding!!
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            code = it.getString("code")
            phone = it.getString("phone")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        setupListeners()
        setupObservers()
        startTimer()
    }

    private fun setup() {
        code?.let {
            phone?.let {
                binding.textPhone.text = String.format("%S %S", code, phone)
            }
        }
    }


    private fun setupListeners() {
        binding.textPhone.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.bttnContinue.setOnClickListener {
            val number = code+phone
            viewmodel.verifyOtp(number, binding.etOtp.text.toString())
        }
    }


    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.verifyotpResponse.collect(){
                when(it){
                    is NetworkResult.Success->{
                        if(it.data?.token!=null) {
                            proceed(it.data.token)
                        } else {
                            Toast.makeText(requireContext(), "Token is null. Please try again later", Toast.LENGTH_LONG).show()
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

    fun proceed(token: String){
        val bundle = Bundle()
        bundle.putString("token", token)
        findNavController().navigate(R.id.action_otpFragment_to_notesFragment)
    }

    fun startTimer(){
        val duration = 60000L
        countDownTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                seconds %= 60
                val time = String.format(
                    Locale.getDefault(),
                    "%02d:%02d",
                    minutes,
                    seconds
                )
                binding.textTimer.text = time
            }
            override fun onFinish() {}
        }
        countDownTimer?.start()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        _binding = null
    }

}