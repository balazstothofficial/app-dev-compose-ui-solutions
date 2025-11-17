package tcs.app.dev.homework2.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.R
import tcs.app.dev.ui.theme.AppTheme

@Composable
fun AmountSelector(
    amount: UInt,
    modifier: Modifier = Modifier,
    minValue: UInt = 0u,
    maxValue: UInt = UInt.MAX_VALUE,
    onChange: (UInt) -> Unit = {}
) {
    val canDecrease = amount > minValue
    val canIncrease = amount < maxValue

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        FilledTonalIconButton(
            enabled = canDecrease,
            onClick = { if (canDecrease) onChange(amount - 1u) }
        ) {
            Icon(
                imageVector = Icons.Outlined.Remove,
                contentDescription = stringResource(R.string.description_decrease_amount)
            )
        }

        Text(
            text = amount.toString(),
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        FilledTonalIconButton(
            enabled = canIncrease,
            onClick = { if (canIncrease) onChange(amount + 1u) }
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = stringResource(R.string.description_increase_amount)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AmountSelectorPreview() {
    AppTheme {
        AmountSelector(amount = 3u)
    }
}
