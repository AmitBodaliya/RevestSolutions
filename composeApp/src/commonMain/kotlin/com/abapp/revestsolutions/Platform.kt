package com.abapp.revestsolutions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun LogD(tag: String, message: String)
expect fun LogE(tag: String, message: String)

@Composable
expect fun KmpBackHandler(enabled: Boolean = true, onBack: () -> Unit)

expect fun deviceType(): String


@Composable
expect fun NetworkImage(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
)
