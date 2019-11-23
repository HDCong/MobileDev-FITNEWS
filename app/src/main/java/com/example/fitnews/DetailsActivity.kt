package com.example.fitnews

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    var mNews: News? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "NEWS DETAILS"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        initData()
        registerViews()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun initData() {
        val news = intent.extras?.getParcelable<News>("NEWS")

        mNews = news
        news?.let {
            tvTitle.text = news.title
            tvPubDate.text = news.pubDate
            tvDesc.text = Html.fromHtml(news.description)
            tvDesc.movementMethod=LinkMovementMethod.getInstance()

        }
    }

    private fun registerViews() {
        btnOpenUrl.setOnClickListener {
            mNews?.link?.let { openChromeTab(it) }
        }
    }

    fun openChromeTab(url: String) {
        val builder = CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}
