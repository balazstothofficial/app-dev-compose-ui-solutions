package tcs.app.dev.homework1.solution.animated

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.homework1.data.Discount
import tcs.app.dev.homework1.data.MockData
import tcs.app.dev.ui.theme.AppTheme

@Composable
fun DiscountRow(
    discount: Discount,
    modifier: Modifier = Modifier,
    buttonEnabled: Boolean = true,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    shape: Shape = MaterialTheme.shapes.large,
    onClick: () -> Unit = {},
    buttonIcon: @Composable () -> Unit,
) {
    Card(modifier = modifier, shape = shape) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Icon(
                modifier = Modifier
                    .padding(4.dp)
                    .size(42.dp),
                imageVector = Icons.Outlined.LocalOffer,
                contentDescription = null
            )

            Text(
                text = discount.text,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            FilledTonalButton(
                enabled = buttonEnabled,
                onClick = onClick,
                contentPadding = PaddingValues(all = 4.dp),
                colors = buttonColors
            ) {
                buttonIcon()
            }
        }
    }
}

@Preview
@Composable
fun DiscountRowPreview() {
    AppTheme {
        DiscountRow(
            discount = MockData.ExampleDiscounts.first(),
            buttonIcon = {}
        )
    }
}
