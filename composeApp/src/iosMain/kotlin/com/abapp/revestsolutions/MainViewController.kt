package com.abapp.revestsolutions

import androidx.compose.ui.window.ComposeUIViewController
import com.abapp.revestsolutions.di.initKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}
