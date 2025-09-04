package com.riccardo.imagebrowser.data.repository

import android.util.Log
import com.riccardo.imagebrowser.data.model.SearchResponse
import com.riccardo.imagebrowser.data.service.SearchApiService
import com.riccardo.imagebrowser.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class SearchRepositoryImpl(private val searchApiService: SearchApiService) : SearchRepository {

    companion object {
        const val TAG = "SearchRepositoryImpl"
    }

    override suspend fun searchImages(query: String): Flow<Result<SearchResponse>> = flow {
        val response = searchApiService.search(query)
        Log.d(TAG, "searchImages() response: $response")
        if (response.isSuccessful)
            emit(Result.success(response.body()!!))
        else
            Result.failure<HttpException>(HttpException(response))
    }.catch {
        emit(Result.failure(it))
    }
}