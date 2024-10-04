import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import data.MongoDB
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.home.HomeScreen
import presentation.home.HomeViewModel

val lightPrimaryColor = Color(0xAA8E00)
val lightOnPrimaryColor = Color(0xFFFFFF)
val lightPrimaryContainerColor = Color(0x504200)
val lightSecondaryColor = Color(0x9B906D)
val lightSecondaryContainerColor = Color(0x4A4227)
val lightErrorColor = Color(0x4E0002)
val lightErrorContainerColor = Color(0x8C0009)
val lightSurfaceColor = Color(0xFFF8EF)

val darkPrimaryColor = Color(0xFFFAF5)
val darkOnPrimaryColor = Color(0x000000)
val darkPrimaryContainerColor = Color(0xE2CA71)
val darkSecondaryColor = Color(0xFFFAF5)
val darkSecondaryContainerColor = Color(0xD7CAA5)
val darkErrorColor = Color(0xFFF9F9)
val darkErrorContainerColor = Color(0xFFBAB1)
val darkSurfaceColor = Color(0x16130B)

val lightColors = lightColorScheme(
    primary = lightPrimaryColor,
    onPrimary = lightOnPrimaryColor,
    primaryContainer = lightPrimaryContainerColor,
    secondary = lightSecondaryColor,
    secondaryContainer = lightSecondaryContainerColor,
    onSecondary = lightOnPrimaryColor,
    error = lightErrorColor,
    errorContainer = lightErrorContainerColor,
    surface = lightSurfaceColor
)
val darkColors = darkColorScheme(
    primary = darkPrimaryColor,
    onPrimary = darkOnPrimaryColor,
    primaryContainer = darkPrimaryContainerColor,
    secondary = darkSecondaryColor,
    secondaryContainer = darkSecondaryContainerColor,
    onSecondary = darkOnPrimaryColor,
    error = darkErrorColor,
    errorContainer = darkErrorContainerColor,
    surface = darkSurfaceColor
)

@Composable
@Preview
fun App(
    useDarkTheme: Boolean = isSystemInDarkTheme()
) {
    val colors = if (!useDarkTheme) {
        lightColors
    } else {
        darkColors
    }

    initKoin()

    MaterialTheme(colorScheme = colors) {
        Navigator(HomeScreen()) {
            SlideTransition(it)
        }
    }
}

val databaseModule = module {
    single { MongoDB() }
    factory { HomeViewModel(get()) }
}

fun initKoin() {
    startKoin {
        modules(listOf(databaseModule))
    }
}
