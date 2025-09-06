package com.riccardo.imagebrowser.presentation

import com.riccardo.imagebrowser.data.model.Photo

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val searchResults: List<Photo> = listOf(),
    val errorMessage: String? = null,
    val page: Int = 1,
    val totalPages: Int = Int.MAX_VALUE
)