package com.example.expense_tracker.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.expense_tracker.R
import com.example.expense_tracker.feature.add_expense.AddExpense
import com.example.expense_tracker.feature.home.HomeScreen
import com.example.expense_tracker.feature.stats.StatsScreen
import com.example.expense_tracker.ui.theme.Zinc

@Composable
fun NavHostScreen(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    var bottomBarVisibility by remember { mutableStateOf(true) }
    Scaffold(
        bottomBar ={
            AnimatedVisibility(visible= bottomBarVisibility) {
                NavigationBottomBar(
                    navController=navController,
                    items = listOf(
                        NavItem(route = "/home", icon = R.drawable.ic_home),
                        NavItem(route = "/stats", icon = R.drawable.ic_stats)
                    )
                )
            }

        },
        floatingActionButton = {
            Box() {
                AnimatedVisibility(visible = bottomBarVisibility) {
                    FloatingActionButton(
                        onClick = {navController.navigate("/add")},
                        shape = CircleShape,
                        containerColor = Zinc,
                        contentColor = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(80.dp)
                            .offset(x= (-135).dp, y = 52.dp)

                        ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = null
                        )
                    }
                }
            }

        },

    ) {
        NavHost(
            navController = navController,
            startDestination = "/home",
            modifier = Modifier.padding(it)
        ) {
            composable("/home") {
                bottomBarVisibility=true
                HomeScreen(navController)
            }
            composable("/add") {
                bottomBarVisibility=false
                AddExpense(navController)
            }
            composable("/stats") {
                bottomBarVisibility=true
                StatsScreen(navController)

            }
        }
    }


}

data class NavItem(
    val route: String,
    val icon: Int
)

@Composable
fun NavigationBottomBar(navController: NavController, items: List<NavItem>) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    BottomAppBar(

    ){
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true


                    }
                },
                icon = { Icon(painter = painterResource(item.icon), contentDescription = null) },

                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = Zinc,
                    selectedIconColor = Zinc,
                    unselectedTextColor = Color.Gray,
                    unselectedIconColor = Color.Gray
                )

            )
        }
    }
}