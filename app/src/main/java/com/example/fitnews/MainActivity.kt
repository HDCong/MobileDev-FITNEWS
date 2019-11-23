package com.example.fitnews

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import android.widget.AdapterView.OnItemClickListener
import android.net.ConnectivityManager
import android.net.NetworkInfo

class MainActivity : AppCompatActivity(), FeedCallback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        registerViews()
    }

    private fun registerViews() {
        listviewNews.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val news = listviewNews.getItemAtPosition(position) as News
                onItemClickInMain(news);
            }
    }

    private fun initData() {
        val feedTask = myAsyncTask()
        feedTask.setListener(this)
        if (isOnline())
            feedTask.execute("https://www.fit.hcmus.edu.vn/vn/feed.aspx")
        else
        {
            pbLoading.visibility = View.GONE
            tvError.text="No internet connection"
            tvError.visibility = View.VISIBLE
        }
    }

    private fun onItemClickInMain(news: News) {
        val intent = Intent(this, DetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("NEWS", news)
        intent.putExtras(bundle)// 1000
        startActivity(intent)
    }
    private fun isOnline():Boolean{
        val connMng = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connMng.activeNetworkInfo
        if(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable){
            return false;
        }
        return true;
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.refresh -> {
                tvError.visibility= View.GONE
                pbLoading.visibility = View.VISIBLE
                listviewNews.visibility = View.GONE
                initData()
            }
            R.id.exit -> {
                this.finish();
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onSuccess(list: List<News>) {
        runOnUiThread {
            val adapter = NewsAdapter(this)
            list.let {
                adapter.setItem(it)
                if (list.isNotEmpty()) {
                    listviewNews.visibility = View.VISIBLE
                } else {
                    tvError.visibility = View.VISIBLE
                    pbLoading.visibility = View.GONE
                }
            }
            listviewNews.adapter = adapter
        }

    }

    override fun onError(exception: Exception) {
        runOnUiThread {
            pbLoading.visibility = View.GONE
            tvError.text="Cannot load data"
            tvError.visibility= View.VISIBLE
            listviewNews.visibility = View.GONE
        }
    }

    override fun onComplete() {
        runOnUiThread {
            pbLoading.visibility = View.GONE
            listviewNews.visibility = View.VISIBLE
            tvError.visibility = View.GONE
        }
    }

    override fun startTask() {
        runOnUiThread {
            pbLoading.visibility = View.VISIBLE
            listviewNews.visibility = View.GONE
            tvError.visibility = View.GONE
        }
    }
}
