package com.example.webantapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.webantapp.model.Photo
import com.example.webantapp.navigation.WebAntNavHost
import com.example.webantapp.navigation.topLevelRoutes
import com.example.webantapp.ui.screens.MainScreenDestination
import com.example.webantapp.ui.screens.PhotoDescScreenDestination
import com.example.webantapp.ui.theme.darkGrey
import com.example.webantapp.ui.theme.purple
import com.example.webantapp.ui.theme.white

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun WebAntApp(navController: NavHostController = rememberNavController(), photo: Photo) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        bottomBar = {
            if (currentDestination?.route != PhotoDescScreenDestination.route) {
                BottomNavigation(backgroundColor = MaterialTheme.colorScheme.background) {

                    topLevelRoutes.forEach { topLevelRoute ->
                        BottomNavigationItem(
                            selected = currentDestination?.hierarchy?.any {
                                it.hasRoute(
                                    topLevelRoute.route::class
                                )
                            } == true,
                            icon = {
                                Icon(
                                    painter = painterResource(id = topLevelRoute.icon),
                                    topLevelRoute.name,
                                    tint = if (currentDestination?.route == topLevelRoute.route) {
                                        purple
                                    } else {
                                        darkGrey
                                    }
                                )
                            },
                            onClick = {
                                navController.navigate(topLevelRoute.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        )
                    }
                }
            }
        },
        topBar = {
            if (currentDestination?.route == PhotoDescScreenDestination.route) {
                CenterAlignedTopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                tint = darkGrey,
                                contentDescription = "Back"
                            )
                        }
                    },
                    colors = topAppBarColors(
        white
        )
                )
            }
        }
    ) { innerPadding ->
        WebAntNavHost(
            navController = navController,
            startDestination = MainScreenDestination.route,
            photo = photo,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview
@Composable
fun
         BottomNavPreview() {
    BottomNavigation(backgroundColor = MaterialTheme.colorScheme.background) {
        topLevelRoutes.forEach { topLevelRoute ->
            BottomNavigationItem(
                onClick = {},
                selected = true,
                icon = { Icon(painter = painterResource(id = topLevelRoute.icon) , topLevelRoute.name, tint = darkGrey) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AppBarPreview() {
    CenterAlignedTopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = darkGrey
                )
            }
        },
        colors = topAppBarColors(
        white
        )
    )
}
