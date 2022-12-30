@file:Suppress("FunctionName")

package com.mudassar.mykitten.navigation

import kotlinx.coroutines.channels.Channel
import org.koin.core.annotation.Single

interface Navigator {
    fun navigate(route: Route)
    fun pop()
}

@Single
class SimpleNavigator : Navigator {
    private val backStack = ArrayDeque<String>()
    private val routesArguments = mutableMapOf<String, Any?>()
    val destinations = Channel<Screen>()

    override fun pop() {
        if (backStack.isEmpty()) return
        backStack.removeLast()
        if (backStack.isNotEmpty()) {
            val route = backStack[backStack.lastIndex]
            navigate(Route(id = route, argument = routesArguments[route]))
        } else destinations.trySend(SuspendApp)
    }

    override fun navigate(route: Route) {
        routesArguments[route.id] = route.argument
        backStack.addLast(route.id)
        destinations.trySend(route)
    }
}

sealed interface Screen
class Route(val id: String, val argument: Any?) : Screen
object SuspendApp : Screen

fun Route(id: String) = Route(id = id, argument = null)
