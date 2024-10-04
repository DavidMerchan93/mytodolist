package presentation.task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.MongoDB
import domain.RequestState
import domain.ToDoTask
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TaskViewModel(
    private val database: MongoDB
) : ScreenModel {

    private val _saveTaskState = mutableStateOf<RequestState<ToDoTask>>(RequestState.Idle)
    val saveTaskState: State<RequestState<ToDoTask>> = _saveTaskState

    fun saveTask(titleTask: String, message: String) {
        screenModelScope.launch {
            val task = ToDoTask().apply {
                title = titleTask
                description = message
            }
            database.addTask(task)
            delay(500)
            _saveTaskState.value = RequestState.Success(task)
        }
    }

}
