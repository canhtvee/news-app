package com.canhtv.ee.newsapp.data.remote

import com.canhtv.ee.newsapp.data.models.ApiResponse
import com.canhtv.ee.newsapp.utils.Resource
import retrofit2.Response

abstract class BaseRemoteDataSource {

    protected suspend fun getResult(content: String, call: suspend () -> Response<ApiResponse>): Resource<ApiResponse> {

        try {
            val response = call()
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

    private fun <T> error(message: String): Resource<T> {
        return Resource.Error("Network call has failed for a following reason: $message")
    }
}
