package ci.nsu.moble.main

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Главная")
    object Palette : Screen("palette", "Палитра")
    object Settings : Screen("settings", "Настройки")
}