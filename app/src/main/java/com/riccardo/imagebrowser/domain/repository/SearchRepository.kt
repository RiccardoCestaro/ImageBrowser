package com.riccardo.imagebrowser.domain.repository

import com.riccardo.imagebrowser.data.model.SearchResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    /**
     * [searchImages] returns a [Flow] of [Result] of [SearchResponse]
     */
    suspend fun searchImages(query: String): Flow<Result<SearchResponse>>
}