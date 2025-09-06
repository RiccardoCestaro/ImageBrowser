package com.riccardo.imagebrowser.data.service

import com.riccardo.imagebrowser.data.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

    @GET("search/photos")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30
    ): Response<SearchResponse>
}