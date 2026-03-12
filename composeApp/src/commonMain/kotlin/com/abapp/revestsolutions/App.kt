package com.abapp.revestsolutions

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.abapp.revestsolutions.model.Product
import com.abapp.revestsolutions.ui.screen.ProductDetailsScreen
import com.abapp.revestsolutions.ui.screen.ProductListScreen
import com.abapp.revestsolutions.ui.theme.RevestSolutionsTheme
import com.abapp.revestsolutions.viewmodel.ProductViewModel
import io.ktor.client.HttpClient
import org.koin.compose.koinInject
import com.abapp.revestsolutions.ui.screen.SplashScreen



@Composable
@Preview
fun App() {

    //http client
    HttpClient()

    RevestSolutionsTheme {

        Navigator(SplashScreenNav) { navigator ->
            AnimatedContent(
                targetState = navigator.lastItem,
                transitionSpec = {
                    if (navigator.lastEvent == StackEvent.Pop) {
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(300)
                        ) togetherWith slideOutHorizontally(
                            targetOffsetX = { fullWidth -> fullWidth / 3 },
                            animationSpec = tween(300)
                        )
                    } else {
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(300)
                        ) togetherWith slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth / 3 },
                            animationSpec = tween(300)
                        )
                    }
                }
            ) { screen: Screen ->
                screen.Content()
            }
        }
    }
}





//splash
object SplashScreenNav : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow


        SplashScreen(
            splashTime = 2600,
            onSplashFinished = {
                //next
                navigator.popAll()
                navigator.push(ProductListScreenNav)
            }
        )
    }
}





//product list
object ProductListScreenNav : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        //view model
        val viewModel: ProductViewModel = koinInject()


        ProductListScreen(
            viewModel = viewModel,
            onItemClick = {
                navigator.push(ProductDetailsScreenNav(it))
            },
            onBackPressed = {

            },
        )

    }
}




//product details
data class ProductDetailsScreenNav(
    val product: Product
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow


        ProductDetailsScreen(
            product = product,
            onBackPressed = {
                navigator.pop()
            },
        )

    }
}

