package com.cinevision.plugin

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.loadExtractor
import org.jsoup.Jsoup

class CineVisionPlugin : MainAPI() {
    override var name = "CineVision Manual" 
    override var mainUrl = "https://cinevisionv6.online" 
    override var supportedTypes = setOf(TvType.Movie, TvType.TvSeries)

    override suspend fun search(query: String): List<SearchResponse> {
        val searchUrl = "$mainUrl/?s=$query"
        val html = app.get(searchUrl).text
        val document = Jsoup.parse(html)

        return document.select("div.poster, div.item, article").map { element ->
            val title = element.select("h2, h3, .title").text()
            val link = element.select("a").attr("href")
            val poster = element.select("img").attr("src")

            MovieSearchResponse(title, link, this.name, TvType.Movie, poster)
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCdn: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val html = app.get(data).text
        val document = Jsoup.parse(html)

        document.select("iframe").forEach { iframe ->
            val playerUrl = iframe.attr("src")
            if (playerUrl.isNotEmpty()) {
                loadExtractor(playerUrl, subtitleCallback, callback)
            }
        }
        return true
    }
}

