package com.riccardo.imagebrowser.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.riccardo.imagebrowser.presentation.components.ImageItem
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {

    val searchViewModel = LocalSearchViewModel.current
    val uiState by searchViewModel.uiState
    val results = uiState.searchResults

    Column(
        modifier
            .fillMaxSize()
            .padding(innerPadding)
            .semantics { isTraversalGroup = true },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val focusManager = LocalFocusManager.current

        SearchBar(
            modifier = Modifier
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = uiState.query,
                    onQueryChange = searchViewModel::onQueryChange,
                    onSearch = {
                        searchViewModel.search()
                        focusManager.clearFocus()
                    },
                    expanded = false,
                    onExpandedChange = { },
                    placeholder = { Text("Search") },
                    trailingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                )
            },
            expanded = false,
            onExpandedChange = { },
        ) {}
        // Show search results in a lazy column for better performance
        when {
            uiState.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            uiState.errorMessage != null -> {
                Text(
                    uiState.errorMessage ?: "Unknown error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            else -> {

                val listState = rememberLazyListState()

                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(count = results.size) { index ->
                        ImageItem(results[index])
                    }
                }

                LaunchedEffect(listState, results.size) {
                    snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                        .distinctUntilChanged() // avoid multiple calls
                        .collect { lastVisible ->
                            if (lastVisible == results.lastIndex) {
                                Log.d("SearchScreen", "End reached, load more")
                                searchViewModel.onPageChange()
                            }
                        }
                }
            }
        }
    }

}