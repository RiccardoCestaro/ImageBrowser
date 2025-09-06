package com.riccardo.imagebrowser.domain.repository

import com.riccardo.imagebrowser.data.model.SearchResponse

interface SearchRepository {
    /**
     * [searchImages] returns a [Result] of [SearchResponse]
     */
    suspend fun searchImages(query: String, page: Int): Result<SearchResponse>
}