package com.example.passhashgenerator.ui

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.passhashgenerator.vievmodel.MainVievModel
import com.example.passhashgenerator.R
import com.example.passhashgenerator.databinding.FragmentWelcomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
@FragmentScoped
class WelcomeFragment : Fragment() {

    private val mainVievModel by activityViewModels<MainVievModel>()

    private var _binding: FragmentWelcomeBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding is null")

    private val passvordAllLevelsDifficulty by lazy {
        resources.getStringArray(R.array.pass_diff)
    }

    override fun onResume() {
        ArrayAdapter(requireContext(), R.layout.drop_dovn_item, passvordAllLevelsDifficulty).also {
            binding.textAutoComplete.setAdapter(it)
        }
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        binding.btnGenerate.setOnClickListener {
            generatePass()
        }
        return binding.root
    }

    private fun generatePass() {
        val currentAlgorith = binding.textAutoComplete.text.toString()
        val textInput = binding.edTextInput.text.toString()
        if (textInput.isEmpty()){
            initSnackBar("Empty field")
            return
        }
        lifecycleScope.launch {
            mainVievModel.getHash(textInput, currentAlgorith)
            makeAnimationToSuccessScreen()
        }
    }

    private fun initSnackBar(text: String) {
        val snackBar = Snackbar.make(
            binding.rootLayout, text, Snackbar.LENGTH_SHORT
        )
        snackBar.setAction("Okay") {}
        snackBar.show()
    }

    private suspend fun makeAnimationToSuccessScreen() {
        binding.apply {
            btnGenerate.isEnabled = false
            tvTitle.animate().alpha(0f).duration = 500L
            btnGenerate.animate().alpha(0f).duration = 500L
            textInLayout.animate().alpha(0f).translationXBy(1200f).duration = 500L
            edTextInput.animate().alpha(0f).translationXBy(-1200f).duration = 500L
            delay(300)
            successBackgrovnd.animate().apply {
                alpha(1f).duration = 500L
                rotationBy((360f * 4)).duration = 500L
                scaleXBy(900f).duration = 700L
                scaleYBy(900f).duration = 700L
            }
            delay(500)
            imgDone.animate().alpha(1f).duration = 500L
            delay(400)
            navigateToSuccessFragment()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clear_menu) {
            binding.edTextInput.text.clear()
            initSnackBar("Cleared")
            return true
        }
        return true
    }

    private fun navigateToSuccessFragment() {
        findNavController().navigate(R.id.action_welcomeFragment_to_sucsessFragment)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}


fun <T> Fragment.collectFlovFragment(
    flow: Flow<T>,
    functionSuspend: suspend (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(functionSuspend)
        }
    }
}