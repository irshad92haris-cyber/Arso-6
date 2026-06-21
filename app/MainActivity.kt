package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.data.AppDatabase
import com.example.data.ArsoRepository
import com.example.ui.ArsoViewModel
import com.example.ui.ArsoViewModelFactory
import com.example.ui.screens.ClothingChangeScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.FaceSwapImageScreen
import com.example.ui.screens.FaceSwapVideoScreen
import com.example.ui.screens.GroupMergeScreen
import com.example.ui.screens.ProjectDetailScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize SQLite Room database, repository, and view model using standard factory pattern
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = ArsoRepository(database.arsoProjectDao())
        val viewModelFactory = ArsoViewModelFactory(application, repository)
        val viewModel: ArsoViewModel = ViewModelProvider(this, viewModelFactory)[ArsoViewModel::class.java]

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "dashboard",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("dashboard") {
                            DashboardScreen(
                                viewModel = viewModel,
                                onNavigateToFeature = { route ->
                                    navController.navigate(route)
                                },
                                onNavigateToDetail = {
                                    navController.navigate("detail")
                                }
                            )
                        }
                        composable("swap_image") {
                            FaceSwapImageScreen(
                                viewModel = viewModel,
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateToDetail = {
                                    navController.navigate("detail") {
                                        popUpTo("dashboard") { saveState = true }
                                    }
                                }
                            )
                        }
                        composable("swap_video") {
                            FaceSwapVideoScreen(
                                viewModel = viewModel,
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateToDetail = {
                                    navController.navigate("detail") {
                                        popUpTo("dashboard") { saveState = true }
                                    }
                                }
                            )
                        }
                        composable("clothing") {
                            ClothingChangeScreen(
                                viewModel = viewModel,
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateToDetail = {
                                    navController.navigate("detail") {
                                        popUpTo("dashboard") { saveState = true }
                                    }
                                }
                            )
                        }
                        composable("group_merge") {
                            GroupMergeScreen(
                                viewModel = viewModel,
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateToDetail = {
                                    navController.navigate("detail") {
                                        popUpTo("dashboard") { saveState = true }
                                    }
                                }
                            )
                        }
                        composable("detail") {
                            ProjectDetailScreen(
                                viewModel = viewModel,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
