package com.android.banca_android.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home: BottomNavItem(Routes.HOME_SCREEN, Icons.Default.Home, "Inicio")
    object Profile: BottomNavItem(Routes.PROFILE_SCREEN, Icons.Default.Person, "Perfil")
    object Other: BottomNavItem(Routes.OTHER_SCREEN, Icons.Default.DateRange, "Otros")
}
