package com.muratdayan.epasaj.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muratdayan.epasaj.databinding.CartProductRowCardBinding
import com.muratdayan.epasaj.databinding.CategoriesRowCardBinding
import com.muratdayan.epasaj.domain.model.network_models.response_models.CartProductModel

class CartProductAdapter(
    private val cartProducts: List<CartProductModel>,
) : RecyclerView.Adapter<CartProductAdapter.CartProductRowHolder>()  {

    inner class CartProductRowHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CartProductRowCardBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductRowHolder {
        val design = CartProductRowCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartProductRowHolder(design.root)
    }

    override fun getItemCount(): Int = cartProducts.size

    // cartproduct nesnesinin bilgilerini yazdırır
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartProductRowHolder, position: Int) {
        val cartProduct = cartProducts[position]

        Glide.with(holder.itemView)
            .load(cartProduct.thumbnail)
            .into(holder.binding.imgViewProductImage)

        holder.binding.txtViewProductPrice.setText("$${cartProduct.price}")
        holder.binding.txtViewProductTitle.text = cartProduct.title
        holder.binding.txtViewDiscountedTotal.setText("Discounted Total: $${cartProduct.discountedTotal}")
        holder.binding.txtViewProductDiscount.setText("Discount: ${cartProduct.discountPercentage}%")
        holder.binding.txtViewProductTotal.setText("Total: $${cartProduct.total}")
        holder.binding.txtViewQuantity.setText("${cartProduct.quantity}")

    }


}