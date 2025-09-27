package com.example.menusencompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.menusencompose.ui.theme.MenusEnComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MenusEnComposeTheme {
                MainNavigation()
            }
        }
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { CustomScaffold(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
        composable("star") { StarScreen(navController) }
        composable("close") { CloseScreen(navController) }
    }
}

@Composable
fun CustomScaffold(navController: NavHostController) {
    // 👉 Estado del contador
    var clickCount by remember { mutableStateOf(0) }

    Scaffold(
        topBar = { CustomTopBar(navController) },
        floatingActionButton = { CustomFAB { clickCount++ } }, // cada clic aumenta contador
        bottomBar = { CustomBottomBar(navController) }
    ) { padding ->
        CustomContent(padding, clickCount) // mostramos el contador
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(navController: NavHostController) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { navController.navigate("settings") }) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = "Configuración")
            }
        },
        title = { Text(text = "Mi Aplicación") },
        actions = {
            IconButton(onClick = { navController.navigate("star") }) {
                Icon(imageVector = Icons.Filled.Star, contentDescription = "Favoritos")
            }
            IconButton(onClick = { navController.navigate("profile") }) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Perfil")
            }
        }
    )
}

@Composable
fun CustomFAB(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Text(
            fontSize = 24.sp,
            text = "+"
        )
    }
}

@Composable
fun CustomBottomBar(navController: NavHostController) {
    BottomAppBar {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigate("settings") }) {
                Icon(Icons.Filled.Settings, contentDescription = "Configuración")
            }
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(Icons.Filled.Home, contentDescription = "Inicio")
            }
            IconButton(onClick = { navController.navigate("star") }) {
                Icon(Icons.Filled.Star, contentDescription = "Favoritos")
            }
            IconButton(onClick = { navController.navigate("close") }) {
                Icon(Icons.Filled.Close, contentDescription = "Cerrar Sesión")
            }
        }
    }
}

@Composable
fun CustomContent(padding: PaddingValues, clickCount: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Has presionado el botón $clickCount veces", fontSize = 18.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil de Usuario") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Avatar de usuario",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Nombre: Juan Pérez", fontSize = 18.sp)
            Text(text = "Email: juan.perez@example.com", fontSize = 16.sp)
        }
    }
}

/* === NUEVAS VISTAS === */

@Composable
fun SettingsScreen(navController: NavHostController) {
    ScreenTemplate("Pantalla de Configuración", navController, Icons.Filled.Settings)
}

@Composable
fun StarScreen(navController: NavHostController) {
    ScreenTemplate("Pantalla de Favoritos", navController, Icons.Filled.Star)
}

@Composable
fun CloseScreen(navController: NavHostController) {
    ScreenTemplate("Pantalla de Cerrar Sesión", navController, Icons.Filled.Close)
}

/* Reutilizamos un mismo diseño */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTemplate(title: String, navController: NavHostController, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = title, fontSize = 20.sp)
        }
    }
}
