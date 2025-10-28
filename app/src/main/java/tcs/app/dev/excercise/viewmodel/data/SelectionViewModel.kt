package tcs.app.dev.excercise.viewmodel.data

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectionViewModel : ViewModel() {
    data class State(
        val options: List<Option> = emptyList(),
        val selection: Option? = null,
        val selectionImage: Bitmap? = null
    )

    private val mutableState = MutableStateFlow(State())
    val state = mutableState.asStateFlow()

    fun loadOptions() {
        viewModelScope.launch {
            val options = getOptions().getOrNull()

            if (options != null) {
                mutableState.update { state -> state.copy(options = options) }

                /* This could be further improved by loading the images in parallel and/or
                    updating the state after each new image loaded */
                val withPreviews = withContext(Dispatchers.IO) {
                    options.map { option -> option.copy(previewImage = loadPreview(option.id)) }
                }

                mutableState.update { state ->
                    if (state.options.map { it.id } == options.map { it.id })
                        state.copy(options = withPreviews)
                    else state
                }
            }
        }
    }

    /**
     * It would be useful to cache the images, such that we do not need to load them multiple times.
     */
    fun select(selection: Option?) {
        mutableState.update { state ->
            if (selection == null) state.copy(selection = null, selectionImage = null)
            else state.copy(selection = selection)
        }

        if (selection == null) return

        viewModelScope.launch {
            val image = withContext(Dispatchers.IO) { loadImage(selection.id) }

            mutableState.update { state ->
                if (state.selection?.id == selection.id) state.copy(selectionImage = image)
                else state
            }
        }
    }

    /**
     * We keep trying until the images are loaded.
     * In a real app, we would set a maximum number of tries and
     * then notice the user to check the internet connection for example.
     */
    private tailrec suspend fun loadPreview(id: String): Bitmap =
        when (val preview = getPreviewImage(id).getOrNull()) {
            null -> loadPreview(id)
            else -> preview
        }

    private tailrec suspend fun loadImage(id: String): Bitmap =
        when (val image = getImage(id).getOrNull()) {
            null -> loadImage(id)
            else -> image
        }
}
