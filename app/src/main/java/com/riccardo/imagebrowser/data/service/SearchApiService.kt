package com.riccardo.imagebrowser.data.service

import com.riccardo.imagebrowser.data.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchApiService {

    @GET("search/photos")
    suspend fun search(@Path("query") query: String): Response<SearchResponse>
}