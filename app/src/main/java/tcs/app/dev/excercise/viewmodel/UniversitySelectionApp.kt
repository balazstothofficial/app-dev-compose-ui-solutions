package tcs.app.dev.excercise.viewmodel

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import tcs.app.dev.R
import tcs.app.dev.excercise.viewmodel.data.SelectionViewModel

/**
 * # University selection app with view model
 *
 * ## Tasks:
 *
 * ## Resources:
 *
 **/
@Composable
fun UniversitySelectionApp(
    modifier: Modifier = Modifier,
    viewModel: SelectionViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadOptions()
    }

    when (val selection = state.selection) {
        null -> {
            SelectionScreen(
                title = stringResource(R.string.university_selection),
                options = state.options,
                modifier = modifier
            ) { selected -> viewModel.select(selected) }
        }

        else -> {
            BackHandler {
                viewModel.select(null)
            }
            DetailsScreen(
                title = selection.title,
                description = selection.description,
                image = { modifier ->
                    LoadingBitmapImage(
                        bitmap = state.selectionImage,
                        contentDescription = null,
                        modifier = modifier
                    )
                },
                modifier = modifier
            )
        }
    }
}
