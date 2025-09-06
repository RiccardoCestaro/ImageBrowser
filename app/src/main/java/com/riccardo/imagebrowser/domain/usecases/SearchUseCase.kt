package com.riccardo.imagebrowser.domain.usecases

import com.riccardo.imagebrowser.data.model.SearchResponse
import com.riccardo.imagebrowser.domain.repository.SearchRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    suspend operator fun invoke(query: String, page: Int): SearchResponse {
        return repository.searchImages(query, page).getOrThrow()
    }
}