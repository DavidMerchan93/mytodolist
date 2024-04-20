import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.home.HomeScreen

@Composable
@Preview
fun App(
    useDarkTheme: Boolean = isSystemInDarkTheme()
) {
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }
    MaterialTheme(
        colorScheme = colors
    ) {
        Navigator(HomeScreen()) {
            SlideTransition(it)
        }
    }
}