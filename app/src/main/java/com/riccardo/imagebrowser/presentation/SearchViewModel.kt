package com.riccardo.imagebrowser.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riccardo.imagebrowser.domain.usecases.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "SearchViewModel"
    }

    private val _uiState = mutableStateOf(SearchUiState())

    val uiState: State<SearchUiState> = _uiState

    fun onQueryChange(newQuery: String) {
        _uiState.value = _uiState.value.copy(query = newQuery)
    }

    fun onPageChange() {
        Log.d(TAG, "onPageChange page: ${_uiState.value.page}")
        if (_uiState.value.page >= _uiState.value.totalPages) return
        viewModelScope.launch {
            runCatching {
                searchUseCase(_uiState.value.query, _uiState.value.page + 1)
            }.onSuccess { results ->
                _uiState.value = _uiState.value.copy(
                    page = _uiState.value.page + 1,
                    searchResults = _uiState.value.searchResults + results.map { it.results }
                        .first()
                )
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message
                )
            }
        }
    }

    fun search() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null, page = 1)

            runCatching {
                searchUseCase(_uiState.value.query, 1)
            }.onSuccess { results ->
                _uiState.value = _uiState.value.copy(
                    searchResults = results.map { it.results }.first().toMutableList(),
                    isLoading = false
                )
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

}