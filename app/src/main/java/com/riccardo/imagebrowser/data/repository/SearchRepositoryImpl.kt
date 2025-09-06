package com.riccardo.imagebrowser.data.repository

import android.util.Log
import com.riccardo.imagebrowser.data.model.SearchResponse
import com.riccardo.imagebrowser.data.service.SearchApiService
import com.riccardo.imagebrowser.domain.repository.SearchRepository
import retrofit2.HttpException

class SearchRepositoryImpl(private val searchApiService: SearchApiService) : SearchRepository {

    companion object {
        const val TAG = "SearchRepositoryImpl"
    }

    override suspend fun searchImages(query: String, page: Int): Result<SearchResponse> {
        return try {
            val response = searchApiService.search(query, page)
            Log.d(TAG, "searchImages() response: $response")

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(HttpException(response)) // body null
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}