package com.muratdayan.epasaj.presentation.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.muratdayan.epasaj.R
import com.muratdayan.epasaj.presentation.base.SharedPrefManager
import com.muratdayan.epasaj.databinding.FragmentDetailBinding
import com.muratdayan.epasaj.domain.model.database_models.UserLikesModel
import com.muratdayan.epasaj.domain.model.network_models.request_models.CartRequestModel
import com.muratdayan.epasaj.domain.model.network_models.request_models.ProductRequestModel
import com.muratdayan.epasaj.presentation.SharedViewModel
import com.muratdayan.epasaj.presentation.base.BaseFragment
import com.muratdayan.epasaj.presentation.bottom_sheets.ProductFeaturesBottomSheetFragment
import com.muratdayan.epasaj.presentation.bottom_sheets.ProductReviewsBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val args: DetailFragmentArgs by navArgs()
    private val detailViewModel: DetailViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productId = args.productId
        val sharedPrefManager = SharedPrefManager(requireContext(), "user_data")
        val userId = sharedPrefManager.getValue("user_id", 0)

        binding.imgViewBackIcon.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }

        if (productId != 0) {
            detailViewModel.getProductById(productId)
            detailViewModel.getUsersLikesByUserId(userId)

            // likes durumunu kontrol eder like ise tıklanınca unlike yapar unlike ise like yapar
            binding.imgViewLikesIcon.setOnClickListener {
                val isLiked = detailViewModel.dbLikesState.value.likes?.any { it.productId == productId } ?: false
                if (isLiked) {
                    val id = detailViewModel.dbLikesState.value.likes?.find { it.productId == productId }?.id
                    id?.let {
                        detailViewModel.deleteLikes(UserLikesModel(id = id, userId = userId, productId = productId))
                        binding.imgViewLikesIcon.setImageResource(R.drawable.ic_outline_like)
                    }
                } else {
                    // User wants to like
                    detailViewModel.insertLikes(UserLikesModel(userId = userId, productId = productId))
                    binding.imgViewLikesIcon.setImageResource(R.drawable.ic_like)
                }
            }
        }

        listenDetailState(productId)
        listenDbLikeState(productId)

        // bottomsheet modal
        binding.txtViewShowDetails.setOnClickListener {
            val bottomSheetFeatures = ProductFeaturesBottomSheetFragment()
            bottomSheetFeatures.show(childFragmentManager, "ProductFeaturesBottomSheet")
        }

        binding.txtViewShowReviews.setOnClickListener {
            val bottomSheetReviews = ProductReviewsBottomSheetFragment()
            bottomSheetReviews.show(childFragmentManager, "ProductReviewsBottomSheet")
        }

        // service'e add to cart işlemi yapar
        binding.btnAddToCart.setOnClickListener {
            Log.d("detailFragment", "Button clicked") // Use Log.d for debugging
            Log.d("detailFragment", "UserId: $userId")
            Log.d("detailFragment", "ProductId: $productId")
            val productRequestModel = ProductRequestModel(productId, 3)
            val cartRequestModel = CartRequestModel(userId, listOf(productRequestModel))
            Log.d("detailFragment", "CartRequestModel: $cartRequestModel")
            detailViewModel.addToCart(cartRequestModel)
        }

        // add to cart işleminin sonucunu dinler
        viewLifecycleOwner.lifecycleScope.launch {
            detailViewModel.addResult.collect { result ->
                Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun listenDetailState(productId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            detailViewModel.detailState.collectLatest { detailState ->
                when {
                    detailState.product != null -> {
                        sharedViewModel.setProduct(detailState.product)

                        Glide.with(requireContext())
                            .load(detailState.product.images[0])
                            .into(binding.imageView2)

                        binding.txtViewTitle.text = detailState.product.title
                        binding.txtViewPrice.text = detailState.product.price.toString()

                        // Ürün bilgisi yüklendikten sonra like durumunu kontrol et
                        val isLiked = detailViewModel.dbLikesState.value.likes?.any { it.productId == productId } ?: false
                        if (isLiked) {
                            binding.imgViewLikesIcon.setImageResource(R.drawable.ic_like)
                        } else {
                            binding.imgViewLikesIcon.setImageResource(R.drawable.ic_outline_like)
                        }
                    }
                    detailState.error?.isNotEmpty() == true -> {
                        Log.d("failure", "failure ${detailState.error}")
                    }
                }
            }
        }
    }

    private fun listenDbLikeState(productId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            detailViewModel.dbLikesState.collectLatest { dbLikesState ->
                when {
                    dbLikesState.likes != null -> {
                        val isLiked = dbLikesState.likes.any { it.productId == productId }
                        if (isLiked) {
                            binding.imgViewLikesIcon.setImageResource(R.drawable.ic_like)
                        } else {
                            // Not liked, set the default like icon
                            binding.imgViewLikesIcon.setImageResource(R.drawable.ic_outline_like)
                        }
                    }
                    dbLikesState.error?.isNotEmpty() == true -> {
                        Log.d("failure", "failure ${dbLikesState.error}")
                    }
                }
            }
        }
    }
}