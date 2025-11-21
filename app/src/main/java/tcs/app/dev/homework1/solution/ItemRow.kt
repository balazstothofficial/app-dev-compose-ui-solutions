package tcs.app.dev.homework1.solution

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.R
import tcs.app.dev.homework1.data.Euro
import tcs.app.dev.homework1.data.Item
import tcs.app.dev.homework1.data.MockData
import tcs.app.dev.homework1.data.cents
import tcs.app.dev.ui.theme.AppTheme

@Composable
fun ItemRow(
    item: Item,
    price: Euro,
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
            Image(
                modifier = Modifier
                    .padding(4.dp)
                    .size(42.dp),
                painter = item.image,
                contentDescription = null
            )

            Text(
                text = item.name,
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

@Preview
@Composable
fun ItemRowPreview() {
    AppTheme {
       ItemRow(
            item = MockData.Apple,
            price = 60u.cents
        )
    }
}

