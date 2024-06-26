package com.muratdayan.epasaj.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.muratdayan.epasaj.databinding.FragmentSearchBinding
import com.muratdayan.epasaj.presentation.adapters.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()
    private var currentQuery: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.rvSearch.setHasFixedSize(true)
        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())

        setupSearchView()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSearchView() {

        // searchview her harf değiştiğinde çalışır
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    binding.rvSearch.visibility = View.GONE
                    binding.rvSearch.adapter = null
                }else{
                    performSearch(query)
                }
                return false
            }

            //searchview yazma işlemi bittikten sonra çalışır
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    // Clear adapter and hide RecyclerView if search is empty
                    binding.rvSearch.adapter = null
                    binding.rvSearch.visibility = View.GONE
                    currentQuery = "" // Reset current query
                } else {
                    currentQuery = newText
                    performSearch(newText)
                }
                return false
            }

        })
    }

    // arama işlemine göre verilerin tutuldugu search state içindeki product liste bakar
    private fun performSearch(query: String) {
        searchViewModel.searchProducts(query)
        if (query.isNotEmpty()) {
            lifecycleScope.launch {
                searchViewModel.searchProductState.collectLatest {searchState->
                    when{
                        searchState.productList != null -> {
                            binding.rvSearch.visibility = View.VISIBLE
                            binding.rvSearch.adapter = ProductAdapter(searchState.productList.productList){
                                val action = SearchFragmentDirections.navigateSearchFragmentToDetailFragment(productId = it.id)
                                Navigation.findNavController(binding.rvSearch).navigate(action)
                            }
                        }
                        searchState.error?.isNotEmpty() == true -> {
                            Log.d("failure", "failure ${searchState.error}")
                        }
                    }
                }
            }
        }
    }


}