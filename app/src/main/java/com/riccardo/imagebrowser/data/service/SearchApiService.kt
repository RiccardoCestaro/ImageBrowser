package com.riccardo.imagebrowser.data.service

import com.riccardo.imagebrowser.data.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

    @GET("search/photos")
    suspend fun search(@Query("query") query: String): Response<SearchResponse>
}