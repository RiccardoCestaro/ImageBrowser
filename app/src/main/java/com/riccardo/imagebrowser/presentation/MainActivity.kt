package com.riccardo.imagebrowser.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.riccardo.imagebrowser.presentation.ui.theme.ImageBrowserTheme
import com.riccardo.imagebrowser.presentation.utils.DetailsScreen
import com.riccardo.imagebrowser.presentation.utils.SearchScreen
import dagger.hilt.android.AndroidEntryPoint


val LocalNavController = compositionLocalOf<NavHostController> { error("No nav controller") }
val LocalSearchViewModel = compositionLocalOf<SearchViewModel> {
    error("No SearchViewModel provided")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            ImageBrowserTheme {
                val navController = rememberNavController()
                val searchViewModel: SearchViewModel = hiltViewModel()
                CompositionLocalProvider(
                    values = arrayOf(
                        LocalNavController provides navController,
                        LocalSearchViewModel provides searchViewModel
                    )
                ) {
                    Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->

                        NavHost(
                            navController = navController,
                            startDestination = SearchScreen
                        ) {
                            composable<SearchScreen> {
                                SearchScreen(innerPadding)
                            }
                            composable<DetailsScreen> {
                                val photoId = it.arguments?.getString("photoId")
                                DetailsScreen(id = photoId)
                            }
                        }
                    }
                }
            }
        }
    }
}