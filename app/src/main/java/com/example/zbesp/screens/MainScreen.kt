package com.example.zbesp.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.zbesp.navigation.bottombar.BottomBarScreen
import com.example.zbesp.navigation.bottombar.BottomNavGraph
import com.example.zbesp.ui.theme.SapphireBlue
import com.example.zbesp.ui.theme.TitleTextWhite
import java.io.InputStream

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(context: Context) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) },
        content = { padding -> // We have to pass the scaffold inner padding to our content. That's why we use Box.
            Box(modifier = Modifier.padding(padding)) {
                BottomNavGraph(navController = navController, context)
            }
        }
    )
}
@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Vehicles,
        BottomBarScreen.Map,
        BottomBarScreen.Settings,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation (backgroundColor = SapphireBlue, contentColor = Color.White) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

@Composable
fun ZBEspTopBar(title: String) {
    TopAppBar(
        title = {
            TitleTextWhite(text = title, alignment = TextAlign.Start)
        }
    )
}


//
//@Composable
//fun BottomNavigationBar(navController: NavHostController) {
//
//    val screens = listOf(
//        BottomBarScreen.Vehicles,
//        BottomBarScreen.Map,
//        BottomBarScreen.Settings,
//    )
//
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentDestination = navBackStackEntry?.destination
//
//    NavigationBar (containerColor = RoyalBlue) {
//        screens.forEach { screen ->
//            NavigationBarItem(
//                label = {
//                    Text(text = screen.title)
//                },
//                icon = {
//                    Icon(
//                        imageVector = screen.icon,
//                        contentDescription = "Navigation Icon"
//                    )
//                },
//                selected = currentDestination?.hierarchy?.any {
//                    it.route == screen.route
//                } == true,
//                onClick = {
//                    navController.navigate(screen.route) {
//                        popUpTo(navController.graph.findStartDestination().id)
//                        launchSingleTop = true
//                    }
//                }
//            )
//        }
//    }
//}
