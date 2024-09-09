package com.example.webantapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.webantapp.R
import com.example.webantapp.model.Photo
import com.example.webantapp.ui.screens.MainScreen
import com.example.webantapp.ui.screens.MainScreenDestination
import com.example.webantapp.ui.screens.PhotoDesc
import com.example.webantapp.ui.screens.PhotoDescScreenDestination
import com.example.webantapp.ui.screens.PhotosScreen
import com.example.webantapp.ui.screens.PhotosScreenDestination
import com.example.webantapp.ui.screens.ProfileScreen
import com.example.webantapp.ui.screens.ProfileScreenDestination

val topLevelRoutes = listOf(
    TopLevelRoute("Main", MainScreenDestination.route, R.drawable.nome),
    TopLevelRoute("Photos", PhotosScreenDestination.route, R.drawable.camera),
    TopLevelRoute("Profile", ProfileScreenDestination.route, R.drawable.person)
)

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun WebAntNavHost(
    navController: NavHostController,
    startDestination: String,
    photo: Photo,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainScreenDestination.route
    ) {
        composable(route = MainScreenDestination.route) {
            MainScreen(
                navigateToPhoto = { navController.navigate(PhotoDescScreenDestination.route) },
                modifier = modifier
            )
        }
        composable(route = PhotosScreenDestination.route) {
            PhotosScreen()
        }
        composable(route = ProfileScreenDestination.route) {
            ProfileScreen()
        }
        composable(route = PhotoDescScreenDestination.route) {
            PhotoDesc(photo = photo, modifier)
        }
    }
}

