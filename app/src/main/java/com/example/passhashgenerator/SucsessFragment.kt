package com.example.passhashgenerator

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.passhashgenerator.databinding.FragmentSucsessBinding
import com.example.passhashgenerator.databinding.FragmentWelcomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
@FragmentScoped
class SucsessFragment : Fragment() {

    @Inject
    @Singleton
    lateinit var vievModelfactory: VievModelFactory

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