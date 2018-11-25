package com.fosafer.retrofitnote

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    @Suppress("UNUSED_PARAMETER")
    fun onClick(view: View) {
//        commonReq()
        rxReq()
    }

    private fun commonReq() {
        val retrofit = Retrofit.Builder()
            .baseUrl(IApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(IApi::class.java)
        val orderCall = api.getNews(IApi.ReqBody("news"))
        orderCall.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                toast("onFailure")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                toast(response.body()?.string() ?: "No Response")
                tv_output.text = response.body()?.string() ?: "No Response"
            }
        })
    }

    @SuppressLint("CheckResult")
    private fun rxReq() {
        val retrofit = Retrofit.Builder()
            .baseUrl(IApi.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(IApi::class.java)
        api.getNews(action = "news")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                tv_output.text = it.toString()
            }, {
                tv_output.text = it.message
            })
    }
}
