package com.abapp.revestsolutions

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp


@SuppressLint("StaticFieldLeak")
lateinit var androidContext: Context

fun initContext(context: Context) {
    androidContext = context
}

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()


actual fun LogD(tag: String, message: String) {
    Log.d(tag, message)
}


actual fun LogE(tag: String, message: String) {
    Log.e(tag, message)
}


@Composable
actual fun KmpBackHandler(enabled: Boolean, onBack: () -> Unit) {
    BackHandler(enabled = enabled) { onBack() }
}

actual fun deviceType(): String {
    return "ANDROID"
}



@Composable
actual fun NetworkImage(
    url: String,
    modifier: Modifier,
    contentScale: ContentScale
) {
    val painter = coil.compose.rememberAsyncImagePainter(model = url)
    val state = painter.state

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painter,
            contentDescription = null,
            contentScale = contentScale,
            modifier = Modifier.matchParentSize()
        )

        when (state) {
            is coil.compose.AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator()
            }

            is coil.compose.AsyncImagePainter.State.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.BrokenImage,
                        contentDescription = "Image load failed",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Failed to load image",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            else -> Unit
        }
    }
}
