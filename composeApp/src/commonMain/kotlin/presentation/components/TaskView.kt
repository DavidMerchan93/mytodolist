package presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import domain.ToDoTask
import misrutinas.composeapp.generated.resources.Res
import misrutinas.composeapp.generated.resources.delete
import misrutinas.composeapp.generated.resources.star
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TaskView(
    task: ToDoTask,
    showActive: Boolean = true,
    onSelect: (ToDoTask) -> Unit,
    onComplete: (ToDoTask, Boolean) -> Unit,
    onFavorite: (ToDoTask, Boolean) -> Unit,
    onDelete: (ToDoTask) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (showActive) {
                    onSelect(task)
                } else {
                    onDelete(task)
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = {
                    onComplete(task, task.isCompleted.not())
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.alpha(if (showActive) 1f else 0.5f),
                text = task.title,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                textDecoration = if (showActive) TextDecoration.None else TextDecoration.LineThrough
            )
        }
        IconButton(
            onClick = {
                if (showActive) {
                    onFavorite(task, task.isFavorite.not())
                } else {
                    onDelete(task)
                }
            }
        ) {
            Icon(
                painter = painterResource(
                    if (showActive) {
                        Res.drawable.star
                    } else {
                        Res.drawable.delete
                    }
                ),
                contentDescription = "Favorite",
                tint = if (task.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.38f
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTaskView() {
    MaterialTheme {
        TaskView(
            task = ToDoTask(),
            onSelect = {},
            onComplete = { _, _ -> },
            onFavorite = { _, _ -> },
            onDelete = {}
        )
    }
}
