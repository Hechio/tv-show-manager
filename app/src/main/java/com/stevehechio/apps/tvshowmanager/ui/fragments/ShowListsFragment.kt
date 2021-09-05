package com.stevehechio.apps.tvshowmanager.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.stevehechio.apps.tvshowmanager.MoviesListQuery
import com.stevehechio.apps.tvshowmanager.R
import com.stevehechio.apps.tvshowmanager.data.Resource
import com.stevehechio.apps.tvshowmanager.databinding.FragmentShowListsBinding
import com.stevehechio.apps.tvshowmanager.ui.adapters.TvShowAdapter
import com.stevehechio.apps.tvshowmanager.ui.viewmodels.MoviesListViewModel
import com.stevehechio.apps.tvshowmanager.utils.gone
import com.stevehechio.apps.tvshowmanager.utils.visible
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import androidx.recyclerview.widget.RecyclerView




class ShowListsFragment : Fragment() {
    private var _binding: FragmentShowListsBinding? = null
    private val binding get() = _binding!!
    private var pageResponse: MoviesListQuery.PageInfo? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var moviesListViewModel: MoviesListViewModel

    private val mAdapter = TvShowAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowListsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        binding.cvAddShow.setOnClickListener {
            findNavController().navigate(R.id.addShowFragment)
        }
        startLoading()
        binding.rvShows.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }

        moviesListViewModel = ViewModelProviders.of(this,viewModelFactory)
            .get(MoviesListViewModel::class.java)
        moviesListViewModel.moviesItem.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    stopLoadingWithSucess()
                    pageResponse = it.data?.pageInfo
                    val list = it.data?.edges
                    if (list != null) {
                        mAdapter.addMoviesList(list)
                    }

                }
                is Resource.Loading -> {
                    if (mAdapter.itemCount < 1){
                        startLoading()
                    }
                }
                else -> {
                    binding.tvError.text = getString(R.string.something_went_wrong)
                    stopLoadingWithError()
                }
            }
        })
        mAdapter.onEndOfListReached = {
            if (pageResponse?.hasNextPage == true){
                val endCursor = pageResponse?.endCursor
                moviesListViewModel.getMoviesLists(cursor = endCursor)
            }
        }
    }

    private fun stopLoadingWithSucess() {
        binding.rvShows.visible()
        binding.pbShows.gone()
        binding.tvError.gone()
    }
    private fun stopLoadingWithError() {
        binding.rvShows.gone()
        binding.pbShows.gone()
        binding.tvError.visible()
    }

    private fun startLoading() {
        binding.rvShows.gone()
        binding.pbShows.visible()
        binding.tvError.gone()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        refreshTheMovieList()
    }

    private fun refreshTheMovieList() {
        if (mAdapter.itemCount > 0){
            mAdapter.clearList()
            moviesListViewModel.getMoviesLists(null)
        }
    }
}