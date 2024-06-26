package com.muratdayan.epasaj.presentation.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.muratdayan.epasaj.R
import com.muratdayan.epasaj.databinding.CategoriesRowCardBinding
import com.muratdayan.epasaj.domain.model.network_models.response_models.CategoriesItemModel
import com.muratdayan.epasaj.presentation.categories.CategoriesFragmentDirections

class CategoriesAdapter(
    private val categories: List<CategoriesItemModel>
) : RecyclerView.Adapter<CategoriesAdapter.CategoriesRowHolder>() {

    inner class CategoriesRowHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CategoriesRowCardBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesRowHolder {
        val design =
            LayoutInflater.from(parent.context).inflate(R.layout.categories_row_card, parent, false)
        return CategoriesRowHolder(design)
    }

    // category nesnesini alır ve bilgilerini yazdırır
    override fun onBindViewHolder(holder: CategoriesRowHolder, position: Int) {

        val category = categories[position]
        holder.binding.txtCategoriesCardTitle.text = category.categoryName

        holder.binding.cvCategories.setOnClickListener {
            val action = CategoriesFragmentDirections.navigateCategoriesFragmentToCategoryProductsFragment(categorySlug = category.categorySlug)
            Navigation.findNavController(holder.binding.cvCategories).navigate(action)
        }
    }


    override fun getItemCount(): Int {
        return categories.size
    }



}