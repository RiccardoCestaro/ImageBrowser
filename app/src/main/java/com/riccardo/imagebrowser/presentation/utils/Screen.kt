package com.riccardo.imagebrowser.presentation.utils

import kotlinx.serialization.Serializable

interface Screen

@Serializable
object SearchScreen : Screen

@Serializable
data class DetailsScreen(val photoId: String?) : Screen