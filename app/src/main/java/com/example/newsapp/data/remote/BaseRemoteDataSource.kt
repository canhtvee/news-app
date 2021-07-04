package com.example.newsapp.data.remote

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.newsapp.data.models.ApiResponse
import com.example.newsapp.utils.Resource
import retrofit2.Response
import java.util.function.Predicate

abstract class BaseRemoteDataSource {

    protected suspend fun getResult2(content: String, call: suspend () -> Response<ApiResponse>): Resource<ApiResponse> {
        try {
            val response = call()
            Log.d("BaseRemoteDataSource", response.toString())
            Log.d("RESPONSE", response.toString())
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    it.articles.forEach { article ->
                        run {
                            article.content = content
                            if (
                                article.urlToImage.equals(null)
                                || article.url.equals(null)
                                || article.description.equals(null)
                            )
                                it.articles.remove(article)
                        }
                    }
                    return Resource.Success(it)
                }
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                Log.d("Response", body.toString())
                body?.let { return Resource.Success(body) }
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())

        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.Error("Network call has failed for a following reason: $message")
    }

}
