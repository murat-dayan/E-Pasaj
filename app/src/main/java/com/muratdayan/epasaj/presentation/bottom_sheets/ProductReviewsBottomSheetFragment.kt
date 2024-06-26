package com.muratdayan.epasaj.presentation.bottom_sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muratdayan.epasaj.databinding.FragmentProductReviewsBinding
import com.muratdayan.epasaj.presentation.SharedViewModel
import com.muratdayan.epasaj.presentation.bottom_sheets.components.ReviewCard


class ProductReviewsBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentProductReviewsBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentProductReviewsBinding.inflate(inflater, container, false)

        // sharedviewmodeldeki product doluysa productun reviews listesini alır ve compose lazy column ile listeler
        sharedViewModel.product.observe(viewLifecycleOwner) { product ->
            product?.let {
                binding.composeView.setContent {
                    LazyColumn {
                        items(product.reviews){
                            ReviewCard(
                                rating = it.rating,
                                comment = it.comment,
                                date = it.date,
                                reviewerName = it.reviewerName,
                                reviewerEmail = it.reviewerEmail
                            )
                        }
                    }
                }
            }
        }




        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

