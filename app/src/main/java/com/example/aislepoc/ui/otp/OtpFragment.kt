package com.example.aislepoc.ui.otp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aislepoc.R
import com.example.aislepoc.databinding.FragmentOtpBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class OtpFragment : Fragment() {
    private var countDownTimer: CountDownTimer? = null
    private var phone: String? = null
    private var _binding: FragmentOtpBinding? = null
    private val binding: FragmentOtpBinding
        get() {
            return _binding!!
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
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
        startTimer()
    }

    private fun setup() {
        phone?.let {
            binding.textPhone.text = it
        }
    }


    private fun setupListeners() {
        binding.textPhone.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.bttnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_otpFragment_to_notesFragment)
        }
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

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.let {
            it.cancel()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = OtpFragment()
    }
}