package com.riccardo.imagebrowser.presentation

import kotlinx.serialization.Serializable

interface Screen

@Serializable
object SearchScreen : Screen

@Serializable
object DetailsScreen : Screen