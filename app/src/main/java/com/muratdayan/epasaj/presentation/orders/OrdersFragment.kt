package com.muratdayan.epasaj.presentation.orders

import android.annotation.SuppressLint
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.muratdayan.epasaj.presentation.base.SharedPrefManager
import com.muratdayan.epasaj.databinding.FragmentOrdersBinding
import com.muratdayan.epasaj.presentation.adapters.CartProductAdapter
import com.muratdayan.epasaj.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>() {

    private val orderViewModel: OrdersViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentOrdersBinding {
        return FragmentOrdersBinding.inflate(inflater, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        binding.rvCarts.setHasFixedSize(true)
        binding.rvCarts.layoutManager = LinearLayoutManager(requireContext())

        binding.txtViewTotalPrice.paintFlags = binding.txtViewTotalPrice.paintFlags or STRIKE_THRU_TEXT_FLAG

        // user id'nin shared üzerinden alınışı ver viewmodelin getarts fonksiyonunun çağrılması
        val sharedPrefManager = SharedPrefManager(requireContext(), "user_data")
        val userId = sharedPrefManager.getValue("user_id", 0)
        orderViewModel.getCartsByUserId(userId)

        listenOrderState()

        return view
    }

    // order state ile durum kontrolü dinleyicisi
    @SuppressLint("SetTextI18n")
    private fun listenOrderState(){
        lifecycleScope.launch {
            orderViewModel.ordersState.collectLatest { ordersState ->
                when {
                    !(ordersState.orders.isNullOrEmpty()) -> {
                        ordersState.orders.map {
                            binding.rvCarts.adapter = CartProductAdapter(it.products)
                        }
                        binding.txtViewTotalPrice.text = "$${ordersState.orders[0].total}"
                        binding.txtViewDiscountedTotalPrice.text = "$${ordersState.orders[0].discountedTotal}"
                    }
                    ordersState.error?.isNotEmpty() == true -> {
                        Log.d("failure", "failure ${ordersState.error}")
                        binding.txtViewTotalPrice.visibility = View.GONE
                        binding.txtViewDiscountedTotalPrice.visibility = View.GONE
                    }
                }
            }
        }
    }
}
