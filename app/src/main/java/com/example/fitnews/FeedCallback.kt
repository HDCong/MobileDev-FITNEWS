package com.example.fitnews

import java.lang.Exception

interface FeedCallback {
    fun onSuccess(list: List<News>)
    fun onError(exception: Exception)
    fun onComplete()
    fun startTask()
}
