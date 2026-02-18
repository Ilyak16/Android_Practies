import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ci.nsu.moble.main.AppScreens
import ci.nsu.moble.main.AppTopBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ci.nsu.moble.main.ui.theme.PracticeTheme() {
                MyApp()
            }
        }
    }
}
@Composable
fun Screen1(onNavigateToScreen2: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Это экран 1",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToScreen2) {
            Text("Перейти на экран 2")
        }
    }
}

@Composable
fun Screen2(
    onNavigateToScreen3: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Это экран 2",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateBack) {
            Text("Назад")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateToScreen3) {
            Text("Далее → экран 3")
        }
    }
}

@Composable
fun Screen3(
    onNavigateToScreen4: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Это экран 3",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateBack) {
            Text("Назад")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateToScreen4) {
            Text("Далее → экран 4")
        }
    }
}

@Composable
fun Screen4(
    onNavigateToScreen5: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Это экран 4",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateBack) {
            Text("Назад")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateToScreen5) {
            Text("Далее → экран 5")
        }
    }
}

@Composable
fun Screen5(onNavigateToStart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Это экран 5",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToStart) {
            Text("Вернуться в начало")
        }
    }
}
@Composable
fun MyApp(){
    val navController = rememberNavController()
    val backStateEntry = navController.currentBackStackEntryAsState()
    val currentScreens = AppScreens.valueOf(
        navController.currentBackStackEntry?.destination?.route ?: AppScreens.Screen1.name)
    Scaffold(
        topBar = {
            AppTopBar(
                currentScreens.title,
                navController.previousBackStackEntry != null,
                {navController.navigateUp()}
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreens.Screen1.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AppScreens.Screen1.name) {
                Screen1(
                    onNavigateToScreen2 = {
                        navController.navigate(AppScreens.Screen2.name)
                    }
                )
            }

            composable(route = AppScreens.Screen2.name) {
                Screen2(
                    onNavigateToScreen3 = {
                        navController.navigate(AppScreens.Screen3.name)
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = AppScreens.Screen3.name) {
                Screen3(
                    onNavigateToScreen4 = {
                        navController.navigate(AppScreens.Screen4.name)
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = AppScreens.Screen4.name) {
                Screen4(
                    onNavigateToScreen5 = {
                        navController.navigate(AppScreens.Screen5.name)
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = AppScreens.Screen5.name) {
                Screen5(
                    onNavigateToStart = {
                        navController.popBackStack(
                            AppScreens.Screen1.name,
                            inclusive = false
                        )
                    }
                )
            }
        }
    }
}