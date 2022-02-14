package com.codecademy.githup

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load

class RepositoriesFragment() : Fragment() {

    private val viewModel: RepositoryViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repositories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.repositoriesRecyclerView)
        val adapter = RepositoriesAdapter()
        recyclerView.adapter = adapter

        viewModel.repositories.observe(viewLifecycleOwner) {
            adapter.repositories = it
            adapter.notifyDataSetChanged()
        }

        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        val errorView: View = view.findViewById(R.id.errorView)
        viewModel.status.observe(viewLifecycleOwner) {
            progressBar.visibility = if (it == ApiStatus.LOADING) View.VISIBLE else View.GONE
            errorView.visibility = if (it == ApiStatus.ERROR) View.VISIBLE else View.GONE
        }


        adapter.onItemClicked = {
            viewModel.selectedRepository = it
            findNavController().navigate(R.id.action_repositoriesFragment_to_repositoryDetailFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
        val searchItem = menu.findItem(R.id.actionSearch)
        val searchView = searchItem.actionView as? SearchView
        searchView?.queryHint = "Search Repository"
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchRepositories(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("MainActivity", "onQueryTextChange $newText")
                return true
            }

        })
    }
}