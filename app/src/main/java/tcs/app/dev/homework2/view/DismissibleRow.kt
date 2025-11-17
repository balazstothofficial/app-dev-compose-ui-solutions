package tcs.app.dev.homework2.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue.Settled
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.runBlocking
import tcs.app.dev.R
import tcs.app.dev.ui.theme.AppTheme

@Composable
fun DismissibleRow(
    shape: Shape,
    onDismiss: () -> Unit = {},
    row: @Composable () -> Unit
) {
    val state = rememberSwipeToDismissBoxState()

    SwipeToDismissBox(
        state = state,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape)
                    .background(MaterialTheme.colorScheme.errorContainer)
                    .wrapContentSize(Alignment.CenterEnd)
                    .padding(horizontal = 12.dp),
                imageVector = Icons.Outlined.Delete,
                contentDescription = stringResource(R.string.description_remove_from_cart),
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
        },
        onDismiss = {
            onDismiss()
            /*
             Fixing bug:
             When last item is swiped away and the same item is re-added afterwards.
            */
            runBlocking { state.snapTo(Settled) }
        }
    ) {
        row()
    }
}

@Preview
@Composable
fun DismissibleRowPreview() {
    AppTheme {
        DismissibleRow(shape = MaterialTheme.shapes.large) {
           Box(modifier = Modifier.height(height = 64.dp).fillMaxWidth()) {  }
        }
    }
}
