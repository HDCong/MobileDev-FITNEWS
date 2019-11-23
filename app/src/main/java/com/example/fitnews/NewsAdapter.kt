package com.example.fitnews

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class NewsAdapter(context : Context):BaseAdapter(){


    private val inflater: LayoutInflater= context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var items = ArrayList<News>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View
        if (convertView==null){
            view = inflater.inflate(R.layout.row_item_news,parent,false)
        }
        else{
            view = convertView
        }
        var title = view.findViewById(R.id.tvRowTitle) as TextView
        var pubdate = view.findViewById(R.id.tvRowPubDate) as TextView
        var content = view.findViewById(R.id.tvRowDesc) as TextView

        val news = items.get(position)

        title.text = "#${ position +1}: " + news.title
        pubdate.text=news.pubDate
        content.text= Html.fromHtml(news.description)
        return view
    }
    fun setItem(list: List<News>) {
        this.items = list as ArrayList<News>
        notifyDataSetChanged()
    }

    override fun getItem(p0: Int): Any {
        return items.get(p0)
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

}