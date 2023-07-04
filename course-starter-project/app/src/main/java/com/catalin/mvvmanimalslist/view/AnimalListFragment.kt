package com.catalin.mvvmanimalslist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.catalin.mvvmanimalslist.R
import com.catalin.mvvmanimalslist.api.NetworkResult
import com.catalin.mvvmanimalslist.databinding.FragmentAnimalListBinding
import com.catalin.mvvmanimalslist.model.Animal
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimalListFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel>()
    private val clickCallback: (animal: Animal) -> Unit = {
        val bundle = bundleOf(AnimalDetailFragment.currentAnimal to it)
        view?.findNavController()?.navigate(R.id.action_animalListFragment_to_animalDetailFragment, bundle)
    }
    private var adapter = AnimalListAdapter(arrayListOf(), clickCallback)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAnimalListBinding.inflate(inflater, container, false)
        context ?: return binding.root

//        setupUI(binding)
        setupObservables(binding)

        return binding.root
    }

//    private fun setupUI(binding: FragmentAnimalListBinding) {
//        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
//        binding.recyclerView.run {
//            addItemDecoration(
//                DividerItemDecoration(
//                    binding.recyclerView.context,
//                    (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
//                )
//            )
//        }
//        binding.recyclerView.adapter = adapter
//        binding.buttonFetchAnimals.setOnClickListener {
//            mainViewModel.getAnimals()
//        }
//    }

    private fun setupObservables(binding: FragmentAnimalListBinding) {
        mainViewModel.result.observe(viewLifecycleOwner) { result ->
            binding.listComposeView.setContent {
                ListFragmentComposable(
                    result = result,
                    buttonClick = { mainViewModel.getAnimals() },
                    animalOnClick = clickCallback)
            }
        }
    }
}