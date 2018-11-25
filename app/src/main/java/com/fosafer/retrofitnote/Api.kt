package com.fosafer.retrofitnote

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by suikajy on 2018.11.23
 */
const val IS_DEBUG = true

interface IApi {

    companion object {
        const val BASE_URL = "http://192.168.1.181:8096/interface/app/"
        const val HOME_NEWS = "index.inc.php"
        // error: baseUrl must end in /
        // 如果想使用全路径就要在接口指定@Url注解
        const val ENTIRE_NEWS_URL = BASE_URL + HOME_NEWS
    }

    // 通过Body注解传值，则会把body中的世纪对象变成Json形式传给服务器
    // 这里传的是{"action":"news"}
    @POST(HOME_NEWS)
    fun getNews(@Body body: ReqBody): Call<ResponseBody>

    data class ReqBody(val action: String)

    // 动态的URL请求
    @FormUrlEncoded
    @POST
    fun getNews(@Url url: String = ENTIRE_NEWS_URL,
                @Field("action") action: String = "news"): Observable<NewsBean>
}