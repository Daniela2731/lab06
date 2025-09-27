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
    }
}

@Composable
fun CustomScaffold(navController: NavHostController) {
    // Estado del contador (se guarda aunque cambie la composición)
    var clickCount by remember { mutableStateOf(0) }

    Scaffold(
        topBar = { CustomTopBar(navController) },
        floatingActionButton = {
            CustomFAB { clickCount++ }
        },
        bottomBar = { CustomBottomBar() }
    ) { padding ->
        CustomContent(padding, clickCount)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(navController: NavHostController) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { /* abrir drawer si lo implementas */ }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menú")
            }
        },
        title = { Text(text = "Mi Aplicación") },
        actions = {
            IconButton(onClick = { /* búsqueda */ }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar")
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
        Text("+", fontSize = 20.sp, modifier = Modifier.padding(6.dp))
    }
}

@Composable
fun CustomBottomBar() {
    BottomAppBar {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { println("Build") }) {
                Icon(Icons.Filled.Build, contentDescription = "Build")
            }
            IconButton(onClick = { println("Menu") }) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
            IconButton(onClick = { println("Favorite") }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
            }
            IconButton(onClick = { println("Delete") }) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun CustomContent(padding: PaddingValues, clickCount: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "My app content", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Has presionado el botón $clickCount veces")
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
