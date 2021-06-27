package com.saqib.gitsearchapp.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saqib.gitsearchapp.databinding.AdapterSearchListBinding
import com.saqib.gitsearchapp.model.GitSearchListItemModel
import com.saqib.gitsearchapp.view.RepoDetailActivity

class SearchListAdapter(
    private val context: Context,
    private val searchList: ArrayList<GitSearchListItemModel>
) :
    RecyclerView.Adapter<SearchListAdapter.Holder>() {


    class Holder(private val binding: AdapterSearchListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, model: GitSearchListItemModel) {
            binding.tvFullName.text = "Repo Name : ${model.fullName}"
            binding.tvDesc.text = "Repo Desc : ${model.description}"
            binding.cardMain.setOnClickListener {
                val intent = Intent(context, RepoDetailActivity::class.java)
                val bundle = Bundle().apply {
                    this.putParcelable("model", model)
                }
                intent.putExtras(bundle)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            AdapterSearchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(context, searchList[position])
    }

    fun addNewItems(newList: ArrayList<GitSearchListItemModel>) {
        searchList.clear()
        searchList.addAll(newList)
        notifyDataSetChanged()
    }
}