package com.riccardo.imagebrowser.domain.usecases

import com.riccardo.imagebrowser.data.model.SearchResponse
import com.riccardo.imagebrowser.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    suspend operator fun invoke(query: String, page: Int): Flow<SearchResponse> =
        repository.searchImages(query, page)
            .map { result ->
                result.getOrThrow()
            }
            .catch { e ->
                e.printStackTrace()
            }
}