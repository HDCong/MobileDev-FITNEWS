package com.example.fitnews

import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import org.jsoup.safety.Whitelist
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.URL

class myAsyncTask() : AsyncTask<String, String, List<News>>() {
    lateinit var callback: FeedCallback

    fun setListener(callback: FeedCallback) {
        this.callback = callback
    }
    override fun doInBackground(vararg p0: String?): List<News> {
        val newsList = mutableListOf<News>()
        callback.startTask()
        try {
            val url = p0[0]
            try{
                val document = Jsoup.connect(url).get();
            }catch ( exception: SocketTimeoutException) {
                callback.onError(exception)
            }
            val document = Jsoup.parse(URL(url).openStream(),"UTF-8", "", Parser.xmlParser())

            val elements = document.getElementsByTag("item")
            for (element in elements) {
                val title = element.getElementsByTag("title").text()
                val link = element.getElementsByTag("link").text()
                val pubDate = element.getElementsByTag("pubDate").text()
                val rawHtml = element.getElementsByTag("description")
                    .html()
                    .replace("![CDATA[<", "")
                    .replace("]]>", "")
                var description: String
                if(rawHtml.length > 1000*250)
                    description=
                                Jsoup.clean(rawHtml, Whitelist.none().addTags("div"))
                else
                    description=rawHtml

                newsList.add(
                    News(title, link, pubDate, description)
                )
//                newsList.add(
//                    News(title, link, pubDate, rawHtml))
            }
        } catch (exception: Exception) {
            callback.onError(exception)
        }
        return newsList
    }

    override fun onPostExecute(result: List<News>?) {
        super.onPostExecute(result)
        result?.let { callback.onSuccess(result) }
        callback.onComplete()
    }
}