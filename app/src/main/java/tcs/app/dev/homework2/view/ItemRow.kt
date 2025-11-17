package tcs.app.dev.homework2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.R
import tcs.app.dev.homework2.data.Euro
import tcs.app.dev.homework2.data.cents
import tcs.app.dev.ui.theme.AppTheme

@Composable
fun ItemRow(
    itemName: String,
    price: Euro,
    image: @Composable (Modifier) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    onAddToCart: () -> Unit = {}
) {
    Card(modifier = modifier, shape = shape) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            image(
                Modifier
                    .padding(4.dp)
                    .size(42.dp)
            )

            Text(
                text = itemName,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = price.toString(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            FilledTonalButton(
                onClick = onAddToCart,
                contentPadding = PaddingValues(all = 4.dp),
                colors = ButtonDefaults.buttonColors()
            ) {
                Icon(
                    imageVector = Icons.Outlined.AddShoppingCart,
                    contentDescription = stringResource(R.string.description_add_to_cart)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemRowPreview() {
    AppTheme {
        ItemRow(
            itemName = "Apple",
            price = 60u.cents,
            image = { modifier ->
                Image(
                    modifier = modifier,
                    painter = painterResource(R.drawable.apple),
                    contentDescription = null
                )
            }
        )
    }
}
