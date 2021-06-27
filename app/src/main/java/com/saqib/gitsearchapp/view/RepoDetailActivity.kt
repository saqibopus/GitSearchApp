package com.saqib.gitsearchapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.saqib.gitsearchapp.R
import com.saqib.gitsearchapp.databinding.ActivityRepoDetailBinding
import com.saqib.gitsearchapp.helper.Logs
import com.saqib.gitsearchapp.helper.loadImage
import com.saqib.gitsearchapp.model.GitSearchListItemModel

class RepoDetailActivity : AppCompatActivity() {
    private var model: GitSearchListItemModel? = null
    private lateinit var binding: ActivityRepoDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repo_detail)
        getIntentData()
        setupUI()
    }

    private fun getIntentData() {
        model = intent.getParcelableExtra("model")
        Logs.p("MoreListActivity")
        Logs.p("getIntentData - $model")
    }

    private fun setupToolbarTitle(title: String) {
        binding.tb.title.text = title

        binding.tb.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun setupUI() {
        model?.let {
            setupToolbarTitle(it.fullName)
            binding.tvName.text = it.fullName
            binding.tvDescription.text = it.description
            binding.imgAvatar.loadImage(this, it.owner.thumbnailUrl)
            binding.tvStarCount.text = it.stars.toString()
            binding.tvProjectLink.text = it.htmlUrl
            binding.tvProjectLink.setOnClickListener { view ->
                val intent = Intent(this, WebViewActivity::class.java)
                val bundle = Bundle().apply {
                    this.putString("url", it.htmlUrl)
                }
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }
}