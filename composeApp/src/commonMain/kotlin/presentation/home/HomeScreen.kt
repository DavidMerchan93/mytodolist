package presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import domain.RequestState
import domain.ToDoTask
import presentation.components.ErrorScreen
import presentation.components.LoadingScreen
import presentation.components.TaskView
import presentation.task.TaskScreen

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        HomeScreenContent()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HomeScreenContent(modifier: Modifier = Modifier) {

        val navigator = LocalNavigator.current

        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(title = { Text(text = "Home") })
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navigator?.push(TaskScreen())
                    },
                ) {
                    Icon(Icons.Filled.Edit, "Floating action button.")
                }
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 24.dp)
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding()
                        )
                ) {
                    DisplayTasks(
                        modifier = Modifier.weight(1f),
                        showActive = true,
                        onComplete = { _, _ ->

                        },
                    )
                    DisplayTasks(
                        modifier = Modifier.weight(1f),
                        showActive = false,
                        onComplete = { _, _ ->

                        },
                    )
                }
            }
        )
    }

    @Composable
    fun DisplayTasks(
        modifier: Modifier = Modifier,
        tasks: RequestState<List<ToDoTask>>? = null,
        showActive: Boolean = true,
        onSelect: ((ToDoTask) -> Unit)? = null,
        onFavorite: ((ToDoTask, Boolean) -> Unit)? = null,
        onComplete: (ToDoTask, Boolean) -> Unit,
        onDelete: ((ToDoTask) -> Unit)? = null
    ) {
        var showDialog by remember { mutableStateOf(false) }
        var taskToDelete: ToDoTask? by remember { mutableStateOf(null) }

        if (showDialog) {
            DeleteTaskDialog(
                taskToDelete = taskToDelete!!,
                showDialog = { showDialog = it },
                changeTaskToDelete = { taskToDelete = it },
                onDelete = onDelete
            )
        }

        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = if (showActive) "Active Tasks" else "Completed Tasks",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(12.dp))
            tasks?.DisplayResult(
                onLoading = { LoadingScreen() },
                onError = { ErrorScreen(message = it) },
                onSuccess = {
                    if (it.isNotEmpty()) {
                        LazyColumn {
                            items(items = it, key = { it._id }) { task ->
                                TaskView(
                                    task = task,
                                    showActive = showActive,
                                    onSelect = onSelect!!,
                                    onComplete = onComplete,
                                    onFavorite = onFavorite!!,
                                    onDelete = {
                                        taskToDelete = task
                                        showDialog = true
                                    }
                                )
                            }
                        }
                    } else {
                        ErrorScreen("No tasks found")
                    }
                }
            )
        }
    }

    @Composable
    fun DeleteTaskDialog(
        taskToDelete: ToDoTask,
        showDialog: (Boolean) -> Unit,
        changeTaskToDelete: (ToDoTask?) -> Unit,
        onDelete: ((ToDoTask) -> Unit)?,
    ) {
        AlertDialog(
            title = {
                Text(
                    text = "Delete",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete ${taskToDelete.title} task?",
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete?.invoke(taskToDelete)
                        showDialog(false)
                        changeTaskToDelete(null)
                    },
                ) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog(false)
                        changeTaskToDelete(null)
                    }
                ) {
                    Text(text = "Cancel")
                }
            },
            onDismissRequest = {
                showDialog(false)
                changeTaskToDelete(null)
            }
        )
    }
}
