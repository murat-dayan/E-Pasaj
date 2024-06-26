package com.muratdayan.epasaj.presentation.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muratdayan.epasaj.R
import com.muratdayan.epasaj.databinding.ProductRowCardBinding
import com.muratdayan.epasaj.domain.model.network_models.response_models.ProductModel
import com.muratdayan.epasaj.presentation.products.ProductFragmentDirections

class ProductAdapter(
    private val productList: List<ProductModel>,
    private val onItemClicked: (ProductModel) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductRowHolder>() {

    inner class ProductRowHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ProductRowCardBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductRowHolder {
        val design =
            LayoutInflater.from(parent.context).inflate(R.layout.product_row_card, parent, false)
        return ProductRowHolder(design)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    // product nesnesini alır ve bilgileri yazdırır
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductRowHolder, position: Int) {

        val product = productList[position]

        holder.binding.txtProductRowTitle.text = product.title
        holder.binding.txtProductRowPrice.setText("$ ${product.price}")

        Glide.with(holder.itemView.context)
            .load(product.images[0])
            .into(holder.binding.imgviewProductRowImage)

        holder.binding.cvProductRow.setOnClickListener {
            onItemClicked(product)
        }

    }


}