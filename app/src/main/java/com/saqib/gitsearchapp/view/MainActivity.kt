package com.saqib.gitsearchapp.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saqib.gitsearchapp.R
import com.saqib.gitsearchapp.adapters.SearchListAdapter
import com.saqib.gitsearchapp.base.BaseActivity
import com.saqib.gitsearchapp.databinding.ActivityMainBinding
import com.saqib.gitsearchapp.helper.Logs
import com.saqib.gitsearchapp.model.GitSearchListItemModel
import com.saqib.gitsearchapp.model.SearchParams
import com.saqib.gitsearchapp.viewmodel.MainActivityVM

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityVM
    private var searchListAdapter: SearchListAdapter? = null
    private var pageNumber = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUtil()
        initViewModelUtils()
        setupToolbarTitle("GitSearchApp")
        binding.btSearch.setOnClickListener {
            initSearch()
        }
        binding.paging.btNext.setOnClickListener {
            pageNumber++
            initSearch()
        }
        binding.paging.btPrev.setOnClickListener {
            if(pageNumber >= 1){
                pageNumber--
                initSearch()
            }else{
                Toast.makeText(this,"No Prev Available",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initUtil() {
        viewModel = ViewModelProvider(this).get(MainActivityVM::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private fun setupToolbarTitle(title: String) {
        binding.tb.title.text = title
    }
    private fun setupPageNumber(){
        binding.paging.tvPageNumber.text = pageNumber.toString()
    }

    /**
     * LiveData observer for Api Response
     * **/
    private fun initSearch() {
        if (binding.etSearchQuery.text.toString() == "") {
            binding.etSearchQuery.error = "Enter search query here.."
            return
        }
        viewModel.getRepoLists(
            SearchParams(
                searchParam = binding.etSearchQuery.text.toString(),
                page = pageNumber
            )
        ).observe(this,
            Observer { model ->
                Logs.p("List : $model")
                model?.let {
                    initFactsAdapter(it.items!!)
                }
                setupPageNumber()
            })
    }

    private fun initFactsAdapter(factRows: ArrayList<GitSearchListItemModel>) {
        binding.rvFactsList.layoutManager = LinearLayoutManager(this)
        if (searchListAdapter == null) {
            searchListAdapter = SearchListAdapter(this, factRows)
            binding.rvFactsList.adapter = searchListAdapter
        } else {
            searchListAdapter?.addNewItems(factRows)
        }
    }

    /**
     * getError() receives errors form ViewModel.
     * getLoading() receives progress values from ViewModel.
     **/
    private fun initViewModelUtils() {
        viewModel.getError().observe(this, Observer {
            showError(it)
        })
        viewModel.getLoading().observe(this, Observer {
            if (it)
                showProgress()
            else
                hideProgress()
        })

    }

    private fun showProgress() {
        if (searchListAdapter == null) {
            binding.rvFactsList.visibility = View.GONE
            binding.pbProgress.visibility = View.VISIBLE
            binding.tvError.visibility = View.GONE
        } else {

        }
    }

    private fun hideProgress() {
        if (searchListAdapter == null) {
            binding.rvFactsList.visibility = View.VISIBLE
            binding.pbProgress.visibility = View.GONE
            binding.tvError.visibility = View.GONE
        } else {

        }
    }

    private fun showError(error: String) {
        binding.rvFactsList.visibility = View.GONE
        binding.pbProgress.visibility = View.GONE
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = error
    }

    /**
     * Disposable will be cleared on ViewModel Base Class so
     * no need to call it manually in onDestroy()
     * **/
    override fun onDestroy() {
        super.onDestroy()
    }
}