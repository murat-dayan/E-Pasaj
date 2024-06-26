package com.muratdayan.epasaj.presentation.products

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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.muratdayan.epasaj.presentation.base.SharedPrefManager
import com.muratdayan.epasaj.databinding.FragmentProductBinding
import com.muratdayan.epasaj.presentation.adapters.ProductAdapter
import com.muratdayan.epasaj.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : BaseFragment<FragmentProductBinding>() {

    private val productViewModel: ProductViewModel by viewModels()

    // base fragmenttan binding alınması
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentProductBinding {
        return FragmentProductBinding.inflate(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        binding.rvProductFragment.setHasFixedSize(true)
        binding.rvProductFragment.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        listenProductState()

        return view
    }

    // product state success oldugunda verileri adaptera gönderme
    private fun listenProductState(){
        lifecycleScope.launch {
            productViewModel.productState.collectLatest { productState ->
                when {
                    productState.productList != null -> {
                        // adapterin higher order fonksiyonuna gönderilen navigasyon fonksiyonu
                        binding.rvProductFragment.adapter = ProductAdapter(productState.productList.productList) { product ->
                            val action = ProductFragmentDirections.navigateProductFragmentToDetailFragment(productId = product.id)
                            Navigation.findNavController(binding.rvProductFragment).navigate(action)
                        }
                    }
                    productState.error?.isNotEmpty() == true -> {
                        Log.d("failure", "failure ${productState.error}")
                    }
                }
            }
        }
    }
}
