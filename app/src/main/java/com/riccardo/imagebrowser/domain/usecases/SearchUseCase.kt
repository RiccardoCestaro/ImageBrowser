package com.riccardo.imagebrowser.domain.usecases

import android.util.Log
import com.riccardo.imagebrowser.data.model.SearchResponse
import com.riccardo.imagebrowser.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    suspend operator fun invoke(query: String): Flow<SearchResponse> =
        repository.searchImages(query)
            .map { result ->
                result.onSuccess { content ->
                    Log.d("SearchUseCase", "invoke: $content")
                }
                result.getOrThrow()
            }
            .catch { e ->
                e.printStackTrace()
            }
}