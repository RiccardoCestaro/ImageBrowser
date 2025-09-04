package com.riccardo.imagebrowser.presentation

import com.riccardo.imagebrowser.data.model.Photo

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val searchResults: List<Photo> = emptyList(),
    val errorMessage: String? = null
)