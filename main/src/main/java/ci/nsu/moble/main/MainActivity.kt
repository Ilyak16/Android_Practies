package ci.nsu.moble.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

private val colorsMap = mapOf(
    "Red" to Color.Red,
    "Orange" to Color(0xFFFFA500),
    "Yellow" to Color.Yellow,
    "Green" to Color.Green,
    "Blue" to Color.Blue,
    "Indigo" to Color(0xFF4B0082),
    "Violet" to Color(0xFF8A2BE2)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                MainApp(navController = navController)
            }
        }
    }
}

@Composable
fun MainApp(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // ИСПРАВЛЕНО: используем Triple вместо вложенных Pair
    val bottomNavItems = listOf(
        Triple(Screen.Home, Icons.Filled.Home, "Главная"),
        Triple(Screen.Palette, Icons.Filled.Star, "Палитра"),  // ✅
        Triple(Screen.Settings, Icons.Filled.Settings, "Настройки")
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    val screen = item.first
                    val icon = item.second
                    val label = item.third

                    NavigationBarItem(
                        icon = { Icon(imageVector = icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToSecondActivity = { data ->
                        val context = navController.context
                        val intent = Intent(context, SecondActivity::class.java).apply {
                            putExtra(SecondActivity.EXTRA_DATA, data)
                        }
                        context.startActivity(intent)
                    }
                )
            }

            composable(Screen.Palette.route) {
                PaletteScreen()
            }

            composable(Screen.Settings.route) {
                SettingsScreen()
            }
        }
    }
}

@Composable
fun HomeScreen(onNavigateToSecondActivity: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var colorValue by remember { mutableStateOf(Color.Black) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Поиск цвета",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Введите цвет") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorValue
            ),
            onClick = {
                colorValue = if (searchQuery.isNotEmpty()) {
                    val foundColor = colorsMap[searchQuery]
                    if (foundColor != null) {
                        foundColor
                    } else {
                        Log.d("ColorSearch", "Цвет \"$searchQuery\" не найден")
                        Color.Black
                    }
                } else {
                    Color.Black
                }
            }
        ) {
            Text(
                text = "Применить цвет",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (colorValue == Color.Black || colorValue == Color.Yellow) Color.Black else Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка перехода на SecondActivity
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onNavigateToSecondActivity("Данные: $searchQuery")
            }
        ) {
            Text("Перейти на второй экран (Intent)")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Доступные цвета:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.Start)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(colorsMap.toList()) { (name, value) ->
                ColorCard(name = name, value = value)
            }
        }
    }
}

@Composable
fun PaletteScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Палитра цветов",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(colorsMap.toList()) { (name, value) ->
                ColorCard(name = name, value = value)
            }
        }
    }
}

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Настройки",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Здесь будут настройки приложения")
    }
}

@Composable
fun ColorCard(name: String, value: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = value
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = if (value == Color.Yellow || value == Color.White) Color.Black else Color.White
            )
        }
    }
}