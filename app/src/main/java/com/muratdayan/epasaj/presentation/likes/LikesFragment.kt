package com.muratdayan.epasaj.presentation.likes

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.muratdayan.epasaj.databinding.FragmentLikesBinding
import com.muratdayan.epasaj.domain.model.network_models.response_models.ProductModel
import com.muratdayan.epasaj.presentation.adapters.ProductAdapter
import com.muratdayan.epasaj.presentation.base.BaseFragment
import com.muratdayan.epasaj.presentation.base.SharedPrefManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LikesFragment : BaseFragment<FragmentLikesBinding>() {

    private val likesViewModel: LikesViewModel by viewModels()
    private val productlist = mutableListOf<ProductModel>()
    private lateinit var productAdapter: ProductAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        binding.rvLikes.setHasFixedSize(true)
        binding.rvLikes.layoutManager = LinearLayoutManager(requireContext())

        // Adapteri başlat
        productAdapter = ProductAdapter(productlist) { product ->
            val action = LikesFragmentDirections.navigateLikesFragmentToDetailFragment(product.id)
            Navigation.findNavController(requireView()).navigate(action)
        }
        binding.rvLikes.adapter = productAdapter

        // shared'den user id alınması
        val sharedPrefManager = SharedPrefManager(requireContext(), "user_data")
        val userId = sharedPrefManager.getValue("user_id", 0)

        // user id sıfır değilse kullanıcı likeları dbden alınır ve sonra bu product id'leri servisten product verilerini getirir
        if (userId != 0) {
            likesViewModel.getUsersLikesByUserId(userId)
            listenProductDetailState()
        }

        binding.btnClearList.setOnClickListener {
            if (productlist.isEmpty()) {
                Toast.makeText(requireContext(), "Your List Already Empty", Toast.LENGTH_SHORT).show()
            } else {
                likesViewModel.deleteAllLikes()
            }
        }

        lifecycleScope.launch {
            likesViewModel.clearResult.collect { result ->
                if (result) {
                    productlist.clear()
                    productAdapter.notifyDataSetChanged()
                    Toast.makeText(requireContext(), "List cleared", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }

    private fun listenProductDetailState() {
        lifecycleScope.launch {
            likesViewModel.productDetailState.collectLatest { detailState ->
                when {
                    detailState.product != null -> {
                        // Ürün zaten listede varsa eklemeyin
                        if (!productlist.any { it.id == detailState.product.id }) {
                            productlist.add(detailState.product)
                            productAdapter.notifyItemInserted(productlist.size - 1)
                        }
                    }
                    detailState.error?.isNotEmpty() == true -> {
                        Log.d("failure", "failure ${detailState.error}")
                    }
                }
            }
        }
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentLikesBinding {
        return FragmentLikesBinding.inflate(inflater, container, false)
    }
}
