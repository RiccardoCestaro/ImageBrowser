package com.riccardo.imagebrowser.domain.usecases

import com.riccardo.imagebrowser.data.model.SearchResponse
import com.riccardo.imagebrowser.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    operator fun invoke(query: String): Flow<SearchResponse> = flow {
        repository.searchImages(query).collect {
            it.onSuccess { content ->
                emit(content)
            }
            it.onFailure { e ->
                e.printStackTrace()
            }
        }
    }.catch {
        it.printStackTrace()
    }
}