package com.muratdayan.epasaj.presentation.categories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.muratdayan.epasaj.R
import com.muratdayan.epasaj.databinding.FragmentCategoriesBinding
import com.muratdayan.epasaj.databinding.FragmentProductBinding
import com.muratdayan.epasaj.presentation.adapters.CategoriesAdapter
import com.muratdayan.epasaj.presentation.adapters.ProductAdapter
import com.muratdayan.epasaj.presentation.base.BaseFragment
import com.muratdayan.epasaj.presentation.products.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>() {

    private val categoriesViewModel: CategoriesViewModel by viewModels()

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCategoriesBinding {
        return FragmentCategoriesBinding.inflate(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        listenCategoryState()
        return view
    }

    // category State'i dinler ve success ise listeyi adaptera gönderir
    private fun listenCategoryState(){
        lifecycleScope.launch {
            categoriesViewModel.categoryState.collectLatest { categoryState ->
                when {
                    categoryState.categories != null -> {
                        val categoriesAdapter = CategoriesAdapter(categoryState.categories)
                        binding.rvCategories.adapter = categoriesAdapter
                        val layoutManager = LinearLayoutManager(requireContext())
                        binding.rvCategories.layoutManager = layoutManager
                    }
                    categoriesViewModel.categoryState.value.isLoading -> {
                        Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Log.d("failure", "failure ${categoryState.error}")
                    }
                }
            }
        }
    }

}
