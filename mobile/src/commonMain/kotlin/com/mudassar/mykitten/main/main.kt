package com.mudassar.mykitten.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mudassar.mykitten.detail.KittenDetailItem
import com.mudassar.mykitten.detail.KittenDetailScreen
import com.mudassar.mykitten.model.Platform
import com.mudassar.mykitten.util.inject
import com.mudassar.mykitten.kittens.KittensScreen
import com.mudassar.mykitten.main.Routes.KittenDetail
import com.mudassar.mykitten.main.Routes.Main
import com.mudassar.mykitten.main.Routes.StartRoute
import com.mudassar.mykitten.navigation.SuspendApp
import com.mudassar.mykitten.navigation.Navigator
import com.mudassar.mykitten.navigation.Route
import com.mudassar.mykitten.navigation.Screen
import com.mudassar.mykitten.navigation.SimpleNavigator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Composable
internal fun app(modifier: Modifier) {
    Navigator(StartRoute) {
        Content(modifier)
    }
}

@Composable
private fun Navigator(
    initialRoute: Route,
    content: @Composable Route.() -> Unit
) {
    val navigator by remember { inject<Navigator>() }
    var config by remember { mutableStateOf<Screen?>(null) }

    LaunchedEffect(Unit) {
        (navigator as SimpleNavigator).destinations
            .receiveAsFlow()
            .onEach {
                config = it
            }
            .launchIn(this)

        launch { navigator.navigate(initialRoute) }
    }

    when (config) {
        SuspendApp -> SuspendApp(inject<Platform>().value)
        is Route -> content(config as Route)
        null -> {}
    }
}

@Composable
expect fun SuspendApp(platform: Platform)

@Composable
private fun Route.Content(modifier: Modifier = Modifier) {
    when (id) {
        Main -> KittensScreen(modifier)
        KittenDetail -> KittenDetailScreen(argument as KittenDetailItem, modifier)
    }
}

private object Routes {
    val Main = "main"
    val KittenDetail = "kitten-detail"

    val StartRoute = Route(Main)
}
