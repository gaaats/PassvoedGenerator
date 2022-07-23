package com.example.passhashgenerator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.passhashgenerator.databinding.FragmentSucsessBinding
import com.example.passhashgenerator.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
@Singleton
class SucsessFragment : Fragment() {

    @Inject
    @Singleton
    lateinit var vievModelfactory: VievModelFactory

    private val mainVievModel: MainVievModel by lazy {
        ViewModelProvider(this, vievModelfactory)[MainVievModel::class.java]
    }

    private var _binding: FragmentSucsessBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentSucsessBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sucsess, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("MY_TAG", "onViewCreated:SucsessFragment: ${mainVievModel} ")

        mainVievModel.textResult.observe(viewLifecycleOwner){
            Log.d("MY_TAG", "onViewCreated -- observe")
            Log.d("MY_TAG", "result is$ $it")
            binding.tvResultText.text = it
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}