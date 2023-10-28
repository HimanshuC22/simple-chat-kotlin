package com.example.simplechat.data.api

import android.util.Log
import com.example.simplechat.data.models.response.BaseResponse
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

object RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.i("RequestInterceptor", "[Request] ${request.url()}")
        val response: Response = chain.proceed(request)

        if(!response.isSuccessful)
        {
            var gson = GsonBuilder().create()
            var obj = gson.fromJson(response.peekBody(2048).string().toString(), BaseResponse::class.java)
            var modifiedResponse = Response.Builder()
                .code(response.code())
                .protocol(response.protocol())
                .request(response.request())
                .message(response.message())
                .headers(response.headers())
                .body(ResponseBody.create(response.body()!!.contentType(), gson.toJson(obj)))
                .sentRequestAtMillis(response.sentRequestAtMillis())
                .receivedResponseAtMillis(response.receivedResponseAtMillis())
                .build()
            Log.i("RequestInterceptor" ,"[isNotSuccessful] ${modifiedResponse.peekBody(2048).string()}")
//            Log.i("RequestInterceptor" ,"[isNotSuccessful] ${obj.message}")
            return modifiedResponse
        }

        Log.i(
            "RequestInterceptor",
            "Response from ${request.url()}\nStatus = ${response.code()}\nBody = ${
                response.peekBody(2048).string()
            }"
        )
        return response
    }
}