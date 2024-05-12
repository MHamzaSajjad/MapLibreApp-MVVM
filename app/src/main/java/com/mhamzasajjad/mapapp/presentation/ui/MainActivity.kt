package com.mhamzasajjad.mapapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mhamzasajjad.mapapp.presentation.theme.MapAppTheme
import com.mhamzasajjad.mapapp.presentation.theme.MapApp_Typography
import com.mhamzasajjad.mapapp.presentation.theme.appPrimaryColorLightTheme
import com.mhamzasajjad.mapapp.presentation.ui.map.MapScreen
import com.mhamzasajjad.mapapp.presentation.ui.navigation.BottomNavBarItem
import com.mhamzasajjad.mapapp.presentation.ui.profile.UserProfileScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MapAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(bottomBar = {
                        BottomNavigationBar(
                            BottomNavBarItem.bottomNavBarItems,
                            navController
                        )
                    }) { padding ->
                        NavHost(
                            navController = navController,
                            startDestination = BottomNavBarItem.Map.tabRoute,
                            modifier = Modifier.padding(padding),
                            exitTransition = {
                                ExitTransition.None
                            }
                        ) {
                            composable(BottomNavBarItem.Map.tabRoute) {
                                MapScreen()
                            }
                            composable(BottomNavBarItem.Profile.tabRoute) {
                                UserProfileScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(bottomNavBarItems: List<BottomNavBarItem>, navController: NavController) {
    BottomNavigation(
        modifier = Modifier.padding(vertical = 5.dp),
        backgroundColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        bottomNavBarItems.forEach { item ->
            val isSelected = (currentRoute == item.tabRoute)
            val itemColour = if (isSelected) appPrimaryColorLightTheme else Color.Black
            BottomNavigationItem(
                modifier = Modifier.padding(vertical = 5.dp),
                selected = isSelected,
                onClick = {
                    navController.navigate(item.tabRoute) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                        anim {

                        }
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.scale(1.1f),
                        imageVector = item.tabIconId,
                        tint = itemColour,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        modifier = Modifier.scale(1.1f),
                        text = stringResource(item.tabLabelResId),
                        style = MapApp_Typography.body1,
                        color = itemColour
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MapAppTheme() {
        Text("Testing Preview")
    }
}
