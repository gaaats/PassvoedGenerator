package com.example.passhashgenerator

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.passhashgenerator.databinding.FragmentWelcomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeFragment : Fragment() {

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
            lifecycleScope.launch {
                makeAnimation()
            }
        }

        return binding.root
    }

    private suspend fun makeAnimation() {
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