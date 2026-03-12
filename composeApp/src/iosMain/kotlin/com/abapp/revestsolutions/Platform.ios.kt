package com.abapp.revestsolutions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSLog
import platform.Foundation.NSURL
import platform.Foundation.NSURLSession
import platform.Foundation.dataTaskWithURL
import platform.UIKit.UIDevice
import platform.UIKit.UIImage
import platform.UIKit.UIImageView
import platform.UIKit.UIViewContentMode
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()


actual fun LogD(tag: String, message: String) {
    NSLog("[$tag] $message")
}


actual fun LogE(tag: String, message: String) {
    NSLog("[$tag] $message")
}


@Composable
actual fun KmpBackHandler(enabled: Boolean, onBack: () -> Unit) {
}

actual fun deviceType(): String {
    return "IOS"
}


@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun NetworkImage(
    url: String,
    modifier: Modifier,
    contentScale: ContentScale
) {
    var uiImage by remember(url) { mutableStateOf<UIImage?>(null) }
    var isLoading by remember(url) { mutableStateOf(true) }

    LaunchedEffect(url) {
        isLoading = true

        val nsUrl = NSURL(string = url)
        NSURLSession.sharedSession.dataTaskWithURL(nsUrl) { data, _, _ ->
            val image = data?.let { UIImage(data = it) }
            dispatch_async(dispatch_get_main_queue()) {
                uiImage = image
                isLoading = false
            }
        }.resume()
    }

    UIKitView(
        modifier = modifier,
        factory = {
            UIImageView().apply {
                contentMode = UIViewContentMode.UIViewContentModeScaleAspectFill
                clipsToBounds = true
            }
        },
        update = { imageView ->
            when {
                isLoading -> {
                    imageView.image = UIImage.imageNamed("placeholder")
                }
                uiImage != null -> {
                    imageView.image = uiImage
                }
                else -> {
                    imageView.image = UIImage.imageNamed("error")
                }
            }
        }
    )
}