package com.fosafer.retrofitnote

data class News(
    val list_news: List<NewsItem>,
    val msg: String,
    val status: Int
)

data class NewsItem(
    val content: String,
    val itemid: String,
    val title: String
)