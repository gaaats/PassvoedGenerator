package com.example.passhashgenerator.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.passhashgenerator.vievmodel.MainVievModel
import com.example.passhashgenerator.databinding.FragmentSucsessBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

@AndroidEntryPoint
@FragmentScoped
class SucsessFragment : Fragment() {

    private val mainVievModel by activityViewModels<MainVievModel>()

    private var _binding: FragmentSucsessBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentSucsessBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSucsessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("MY_TAG", "vievmodel is${mainVievModel}")
        initTextVievResultObserve()
        initSnackBarObserve()
        binding.btnCopy.setOnClickListener {
            onBtnCopyPressed()
        }
        super.onViewCreated(view, savedInstanceState)
    }


    private fun initSnackBarObserve() {
        mainVievModel.eventForSnackBar.observe(viewLifecycleOwner) {
            val snackBar = Snackbar.make(
                binding.successFragRoot, it.peekContent(), Snackbar.LENGTH_SHORT
            )
            snackBar.setAction("Okay") {}
            snackBar.show()
        }
    }

    private fun initTextVievResultObserve() {
        mainVievModel.textResult.observe(viewLifecycleOwner) {
            binding.tvResultText.text = it
        }
    }

    private fun onBtnCopyPressed() {
        mainVievModel.saveToClipBoard()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}