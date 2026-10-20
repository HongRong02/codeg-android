package app.codeg.android.feature.projects.git

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.codeg.android.core.model.StashEntry
import app.codeg.android.core.network.ApiError
import app.codeg.android.core.network.CodegClient
import app.codeg.android.core.network.displayMessage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StashViewModel(private val client: CodegClient, private val path: String) : ViewModel() {
    data class Ui(
        val entries: List<StashEntry> = emptyList(),
        val supported: Boolean = true,
        val busy: Boolean = false,
        val error: String? = null,
        val result: String? = null,
        val diff: String? = null,
        val diffRef: String? = null,
    )
    private val state = MutableStateFlow(Ui())
    val ui = state.asStateFlow()

    fun refresh() = run { state.update { it.copy(entries = client.gitStashList(path)) } }

    fun show(ref: String) = run {
        state.update { it.copy(diff = null, diffRef = null) }
        val files = client.gitStashShow(path, ref)
        val diff = if (files.isEmpty()) "" else client.gitShowDiff(path, ref)
        state.update { it.copy(diff = diff, diffRef = ref) }
    }

    fun change(action: String, ref: String?, message: String, keepIndex: Boolean, onChanged: () -> Unit) = run {
        try {
            val result = when (action) {
                "push" -> client.gitStashPush(path, message.trim().ifEmpty { null }, keepIndex)
                "apply" -> client.gitStashApply(path, requireNotNull(ref))
                "pop" -> client.gitStashPop(path, requireNotNull(ref))
                "drop" -> client.gitStashDrop(path, requireNotNull(ref))
                "clear" -> client.gitStashClear(path)
                else -> error("Unknown stash action")
            }
            state.update { it.copy(result = result, diff = null, diffRef = null) }
        } finally {
            // A failed apply/pop may still change files or leave conflicts.
            onChanged()
        }
        state.update { it.copy(entries = client.gitStashList(path)) }
    }

    private fun run(block: suspend () -> Unit) {
        if (state.value.busy) return
        state.update { it.copy(busy = true, error = null, result = null) }
        viewModelScope.launch {
            try {
                block()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val unsupported = e is ApiError.Server && (e.status == 404 ||
                    e.code?.contains("unknown", ignoreCase = true) == true ||
                    e.serverMessage.contains("unknown command", ignoreCase = true))
                state.update { it.copy(supported = !unsupported, error = if (unsupported) null else e.displayMessage()) }
            } finally {
                state.update { it.copy(busy = false) }
            }
        }
    }
}
