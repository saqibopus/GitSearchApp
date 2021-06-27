package com.saqib.gitsearchapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.saqib.gitsearchapp.R
import com.saqib.gitsearchapp.databinding.ActivityWebViewBinding
import com.saqib.gitsearchapp.helper.Logs
import com.saqib.gitsearchapp.model.GitSearchListItemModel

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    private var url: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)
        getIntentData()
        initWebView()

    }

    private fun setupToolbarTitle(title: String) {
        binding.tb.title.text = title

        binding.tb.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun initWebView() {
        binding.wv.settings.loadsImagesAutomatically = true
        binding.wv.settings.javaScriptEnabled = true
        binding.wv.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        url?.let {
            setupToolbarTitle(it)
            binding.wv.loadUrl(it)
        }
    }

    private fun getIntentData() {
        url = intent.getStringExtra("url")
        Logs.p("getIntentData - $url")
    }

}