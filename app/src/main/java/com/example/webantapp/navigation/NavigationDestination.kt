package com.example.webantapp.navigation

interface NavigationDestination {
    val route: String
}

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: Int)
