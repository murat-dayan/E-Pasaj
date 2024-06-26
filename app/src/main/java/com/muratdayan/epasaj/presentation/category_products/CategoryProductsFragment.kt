package com.muratdayan.epasaj.presentation.category_products

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
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.muratdayan.epasaj.R
import com.muratdayan.epasaj.databinding.FragmentCategoryProductsBinding
import com.muratdayan.epasaj.presentation.adapters.ProductAdapter
import com.muratdayan.epasaj.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryProductsFragment : BaseFragment<FragmentCategoryProductsBinding>() {

    private val args: CategoryProductsFragmentArgs by navArgs()
    private val categoryProductsViewModel: CategoryProductsViewModel by viewModels()

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCategoryProductsBinding {
        return FragmentCategoryProductsBinding.inflate(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val categorySlug = args.categorySlug

        binding.rvCategoryProducts.setHasFixedSize(true)
        binding.rvCategoryProducts.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        categorySlug?.let {
            categoryProductsViewModel.getProductsByCategory(categorySlug)
            binding.txtViewCategoryName.text = categorySlug
            listenCategoryProducts()
        }


        binding.imgViewBackIcon.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }



        return view
    }

    private fun listenCategoryProducts(){
        lifecycleScope.launch {
            categoryProductsViewModel.categoryProductState.collectLatest { categoryProductState ->
                when {
                    categoryProductState.productList != null -> {
                        binding.rvCategoryProducts.adapter = ProductAdapter(categoryProductState.productList.productList) { product ->
                            val action = CategoryProductsFragmentDirections.navigateCategoryProductsFragmentToDetailFragment(productId = product.id)
                            Navigation.findNavController(binding.rvCategoryProducts).navigate(action)
                        }
                    }
                    categoryProductState.error?.isNotEmpty() == true -> {
                        Log.d("failure", "failure ${categoryProductState.error}")
                    }
                }
            }
        }
    }

}
