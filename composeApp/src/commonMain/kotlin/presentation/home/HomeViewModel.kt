package presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.MongoDB
import domain.RequestState
import domain.ToDoTask
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

typealias MutableTasks = MutableState<RequestState<List<ToDoTask>>>
typealias Tasks = State<RequestState<List<ToDoTask>>>

class HomeViewModel(
    private val database: MongoDB
) : ScreenModel {
    private var _activeTasks: MutableTasks = mutableStateOf(RequestState.Idle)
    val activeTasks: Tasks = _activeTasks

    private var _completeTasks: MutableTasks = mutableStateOf(RequestState.Idle)
    val completeTasks: Tasks = _activeTasks

    init {
        _activeTasks.value = RequestState.Loading
        _completeTasks.value = RequestState.Loading
        fetchCompleteTasks()
        fetchActiveTasks()
    }

    private fun fetchCompleteTasks() {
        screenModelScope.launch {
            delay(500)
            database.readCompleteTasks().collectLatest {
                _completeTasks.value = it
            }
        }
    }

    private fun fetchActiveTasks() {
        screenModelScope.launch {
            delay(500)
            database.readActiveTasks().collectLatest {
                _activeTasks.value = it
            }
        }
    }
}
