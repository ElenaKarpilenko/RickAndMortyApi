@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.rickandmortyapi
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rick and Morty") },
                actions = {
                    IconButton(onClick = { navController.navigate("gifScreen") }) {
                        Icon(painter = painterResource(id = R.drawable.character_icon), contentDescription = "Go to GIF Screen")
                    }
                    IconButton(onClick = { navController.navigate("episodeScreen") }) {
                        Icon(painter = painterResource(id = R.drawable.img_1), contentDescription = "Go to Episode Screen")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavigationHost(navController = navController, paddingValues = paddingValues)
    }
}

@Composable
fun GifScreen() {
    Box(modifier = Modifier.fillMaxWidth()) {
        // Используем URL для GIF
        val gifUrl = "https://media.giphy.com/media/LmgKC5pk5TRYSGdRvG/giphy.gif" // Пример URL GIF

        // Загрузка GIF с использованием AsyncImage
        AsyncImage(
            model = gifUrl,
            contentDescription = "GIF Image",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f) // Установим высоту в 80% экрана
        )

        // Текст под GIF
        Text(
            text = "Rick and Morty GIF",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun EpisodeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Статичное изображение для экрана эпизода
        Image(
            painter = painterResource(id = R.drawable.img), // Замените на существующий ресурс
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f) // Устанавливаем высоту на 50% экрана
        )

        // Текстовое описание под изображением
        Box(modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)) {
            Text(
                text = """
                    "Rick and Morty" is an animated science fiction sitcom created by Justin Roiland and Dan Harmon. 
                    The series explores interdimensional adventures of Rick Sanchez, a brilliant but eccentric scientist, 
                    and his grandson Morty. It combines satire, dark humor, and themes like nihilism and scientific curiosity.
                """.trimIndent(),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = "gifScreen",
        modifier = Modifier.padding(paddingValues)
    ) {
        composable("gifScreen") { GifScreen() }
        composable("episodeScreen") { EpisodeScreen() }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("GIF Screen", "gifScreen", R.drawable.character_icon), // Замените на существующий ресурс
        BottomNavItem("Episode Screen", "episodeScreen", R.drawable.img_1) // Замените на существующий ресурс
    )

    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = null, modifier = Modifier.size(24.dp)) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

data class BottomNavItem(val label: String, val route: String, val icon: Int)
