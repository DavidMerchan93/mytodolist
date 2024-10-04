package presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import presentation.components.HorizontalDivider

class TaskScreen : Screen {

    @Composable
    override fun Content() {
        val taskViewModel = getScreenModel<TaskViewModel>()
        val navigator = LocalNavigator.current

        when {
            taskViewModel.saveTaskState.value.isLoading() -> {

            }
            taskViewModel.saveTaskState.value.isSuccess() -> {
                navigator?.pop()
            }
            taskViewModel.saveTaskState.value.isError() -> {

            }
        }

        Scaffold {
            TaskScreenContent(
                onSave = { title, message ->
                    taskViewModel.saveTask(title, message)
                },
                onCancel = {
                    navigator?.pop()
                }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TaskScreenContent(
        modifier: Modifier = Modifier,
        onSave: (String, String) -> Unit,
        onCancel: () -> Unit
    ) {

        var title by remember { mutableStateOf("") }
        var message by remember { mutableStateOf("") }

        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(title = { Text(text = "New Task") })
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 24.dp)
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding(),
                            start = 20.dp,
                            end = 20.dp
                        )
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = title,
                        label = {
                            Text(text = "Title task")
                        },
                        onValueChange = { value ->
                            title = value
                        }
                    )
                    HorizontalDivider()
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = message,
                        label = {
                            Text(text = "Message task")
                        },
                        onValueChange = { value ->
                            message = value
                        }
                    )
                    HorizontalDivider()
                    Button(
                        modifier = Modifier.fillMaxWidth().background(Color.Green),
                        onClick = {
                            onSave(title, message)
                        }
                    ) {
                        Text(text = "Save", color = Color.White)
                    }
                    HorizontalDivider()
                    Button(
                        modifier = Modifier.fillMaxWidth().background(Color.Red),
                        onClick = onCancel
                    ) {
                        Text(text = "Cancel", color = Color.White)
                    }
                }
            }
        )
    }
}