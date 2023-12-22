package com.bangkit.edims.presentation.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bangkit.edims.database.Product
import com.bangkit.edims.database.UserPreference
import com.bangkit.edims.presentation.components.navigation.NavigationItem
import com.bangkit.edims.presentation.components.navigation.Screen
import com.bangkit.edims.presentation.theme.Beige
import com.bangkit.edims.presentation.theme.MyProjectTheme
import com.bangkit.edims.presentation.theme.PaleLeaf
import com.bangkit.edims.presentation.ui.add.AddScreen
import com.bangkit.edims.presentation.ui.add.AddViewModel
import com.bangkit.edims.presentation.ui.detail.DetailScreen
import com.bangkit.edims.presentation.ui.detail.DetailViewModel
import com.bangkit.edims.presentation.ui.home.HomeScreen
import com.bangkit.edims.presentation.ui.home.HomeViewModel
import com.bangkit.edims.presentation.ui.login.LoginScreen
import com.bangkit.edims.presentation.ui.login.LoginViewModel
import com.bangkit.edims.presentation.ui.profile.ProfileScreen
import com.bangkit.edims.presentation.ui.profile.ProfileViewModel
import com.bangkit.edims.presentation.ui.settings.SettingsScreen
import com.bangkit.edims.presentation.ui.settings.SettingsViewModel
import com.bangkit.edims.presentation.ui.signup.SignUpScreen
import com.bangkit.edims.presentation.ui.signup.SignUpViewModel
import com.bangkit.edims.presentation.ui.splash.SplashScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.getViewModel
import org.koin.compose.koinInject

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    var isStartPage by remember {
        mutableStateOf(false)
    }

    var isAddPage by remember {
        mutableStateOf(false)
    }

    val fabPosition by animateIntAsState(
        if (isAddPage) 80 else 50, label = ""
    )

    val snackbarPosition by animateIntAsState(
        if (isStartPage) 0 else 50, label = ""
    )

    val userPref: UserPreference = koinInject()

    val token = runBlocking { userPref.token.first() }

    val isLoggedIn by remember {
        mutableStateOf(
            token != ""
        )
    }

    val navigateNext by remember {
        mutableStateOf(
            if (isLoggedIn) Screen.Home.route else Screen.Login.route
        )
    }


    isStartPage = currentRoute == Screen.Splash.route || currentRoute == Screen.Login.route || currentRoute == Screen.SignUp.route

    isAddPage = currentRoute == Screen.Add.route

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.offset(y = snackbarPosition.dp)
            )
        },
        bottomBar = {
            if (!isStartPage) {
                AnimatedVisibility(visible = !isAddPage) {
                    BottomBar(
                        navController
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            if (!isStartPage) {
                FloatingActionButton(
                    modifier = modifier
                        .size(60.dp)
                        .offset(y = fabPosition.dp),
                    shape = CircleShape,
                    onClick = {
                        navController.navigate(Screen.Add.route) {
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    containerColor = PaleLeaf,
                    contentColor = Color.Black,
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            composable(route = Screen.Splash.route) {
                SplashScreen(
                    modifier = Modifier.zIndex(1f),
                    navigateNext = {
                        navController.navigate(navigateNext) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(route = Screen.Login.route) {
                val loginViewModel = getViewModel<LoginViewModel>()
                LoginScreen(
                    modifier = Modifier.zIndex(1f),
                    viewModel = loginViewModel,
                    showSnackbar = { message, duration ->
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = message,
                                duration = duration,
                                actionLabel = "Ok"
                            )
                        }
                    },
                    navigateToSignUp = {
                        navController.navigate(Screen.SignUp.route)
                    },
                    onLoginSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(route = Screen.SignUp.route) {
                val signUpViewModel = getViewModel<SignUpViewModel>()
                SignUpScreen(
                    viewModel = signUpViewModel,
                    showSnackbar = { message, duration ->
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = message,
                                duration = duration,
                                actionLabel = "Ok"
                            )
                        }
                    },
                    navigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(
                route = Screen.Home.route,
            ) {
                val homeViewModel = getViewModel<HomeViewModel>()
                HomeScreen(
                    homeViewModel = homeViewModel,
                    navigateToDetail = { itemId ->
                        navController.navigate(Screen.Detail.createRoute(itemId))
                    },
                )
            }
            composable(Screen.Add.route) {
                val addViewModel = getViewModel<AddViewModel>()
                AddScreen(
                    addViewModel = addViewModel,
                    navigateToHome = {
                        navController.navigateUp()
                    },
                    showSnackbar = { message, duration ->
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = message,
                                duration = duration,
                                withDismissAction = true
                            )
                        }
                    }
                )
            }
            composable(
                route = Screen.Profile.route,
            ) {
                val profileViewModel = getViewModel<ProfileViewModel>()
                ProfileScreen(
                    profileViewModel = profileViewModel,
                    navigateToSettings = {
                        navController.navigate(Screen.Settings.route)
                    },
                    navigateToLogOut = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                        profileViewModel.logout()
                    }
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType }),
                enterTransition = {
                    slideInVertically(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        ),
                        initialOffsetY = {
                            it / 2
                        }
                    ) + slideIntoContainer(
                        animationSpec = tween(300, delayMillis = 90),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                },
                exitTransition = {
                    slideOutVertically(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        ),
                        targetOffsetY = {
                            it / 2
                        }
                    ) + slideOutOfContainer(
                        animationSpec = tween(300, delayMillis = 90),
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    )
                }
            ) {
                val id = it.arguments?.getInt("id") ?: 0
                var deleteProduct by remember {
                    mutableStateOf<Product?>(null)
                }

                val detailViewModel = getViewModel<DetailViewModel>()

                DetailScreen(
                    detailViewModel = detailViewModel,
                    id = id,
                    showSnackbar = { message, duration ->
                        scope.launch {
                            val snackbarResult = snackbarHostState.showSnackbar(
                                message = message,
                                duration = duration,
                                actionLabel = "Cancel",
                            )
                            when (snackbarResult) {
                                SnackbarResult.Dismissed -> {
                                    deleteProduct?.let { product ->
                                        detailViewModel.delete(product)
                                    }
                                    snackbarHostState.showSnackbar(
                                        message = "Deleted",
                                        duration = SnackbarDuration.Short
                                    )
                                }

                                SnackbarResult.ActionPerformed -> {
                                    deleteProduct = null
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                }
                            }
                        }
                    },
                    navigateBack = { item ->
                        item?.let { product ->
                            deleteProduct = product
                        }

                        navController.navigateUp()
                    }
                )
            }
            composable(
                route = Screen.Settings.route,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideIntoContainer(
                        animationSpec = tween(300, delayMillis = 90),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideOutOfContainer(
                        animationSpec = tween(300, delayMillis = 90),
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    )
                }
            ) {
                val settingsViewModel = getViewModel<SettingsViewModel>()
                SettingsScreen(
                    context = context,
                    settingsViewModel = settingsViewModel,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = PaleLeaf,
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItem = listOf(
            NavigationItem(
                title = "Home",
                icon = Icons.Default.Home,
                screen = Screen.Home,
            ),
            NavigationItem(
                title = "Profile",
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            )
        )
        navigationItem.map { item ->
            val isSelected = currentRoute == item.screen.route
            val scale by animateFloatAsState(if (isSelected) 1.1f else 1f, label = "scale")
            val alpha by animateFloatAsState(if (isSelected) 1f else 0.6f, label = "alpha")

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = Color.Black,
                    selectedIndicatorColor = Beige,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    disabledIconColor = Color.Black,
                    disabledTextColor = Color.Black
                ),
                alwaysShowLabel = false,
                modifier = Modifier
                    .scale(scale)
                    .graphicsLayer(alpha = alpha)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyProjectTheme {
        MainScreen()
    }
}