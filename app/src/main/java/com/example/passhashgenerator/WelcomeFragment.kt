package com.example.passhashgenerator

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.passhashgenerator.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding is null")

    private val passvordAllLevelsDifficulty by lazy {
        resources.getStringArray(R.array.pass_diff)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        ArrayAdapter(requireContext(), R.layout.drop_dovn_item, passvordAllLevelsDifficulty).also {
            binding.textAutoComplete.setAdapter(it)
        }


        setHasOptionsMenu(true)

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}