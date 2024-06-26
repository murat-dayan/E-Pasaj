package com.muratdayan.epasaj.presentation.bottom_sheets

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muratdayan.epasaj.databinding.FragmentProductFeaturesBottomSheetBinding
import com.muratdayan.epasaj.domain.model.network_models.response_models.ProductModel
import com.muratdayan.epasaj.presentation.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFeaturesBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentProductFeaturesBottomSheetBinding?= null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentProductFeaturesBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.product.observe(viewLifecycleOwner) { product ->
            product?.let {
                setProductDetails(it)
            }
        }
    }

    // ürün detaylarını yazdırır
    @SuppressLint("SetTextI18n")
    fun setProductDetails(product: ProductModel) {
        binding.txtTitle.text = product.title
        binding.txtDescription.text = product.description
        binding.txtCategory.text = product.category
        binding.txtPrice.setText("$${product.price}")
        binding.txtStock.setText("stock: ${product.stock}")
        binding.txtDiscountPercentage.setText("discount: ${product.discountPercentage}%")
        binding.txtRating.setText("rating: ${product.rating}")
        binding.txtTags.setText("tags: ${product.tags}")
        binding.txtBrand.setText("brand: ${product.brand}")
        binding.txtSku.setText("sku: ${product.sku}")
        binding.txtWeight.setText("weight: ${product.weight}")
        binding.txtWarrantyInformation.text = product.warrantyInformation
        binding.txtShippingInformation.text = product.shippingInformation
        binding.txtAvailabilityStatus.setText("status: ${product.availabilityStatus}")
        binding.txtReturnPolicy.text = product.returnPolicy
        binding.txtMinimumOrderQuantity.text = product.minimumOrderQuantity.toString()
        Glide.with(this)
            .load(product.meta.qrCode)
            .into(binding.imgViewQrCode)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}