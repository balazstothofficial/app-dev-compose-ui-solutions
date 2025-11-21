package tcs.app.dev.homework1.solution.animated

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.homework1.data.Euro
import tcs.app.dev.homework1.data.Item
import tcs.app.dev.homework1.data.MockData
import tcs.app.dev.homework1.data.cents
import tcs.app.dev.homework1.data.times
import tcs.app.dev.ui.theme.AppTheme

@Composable
fun CartItemRow(
    item: Item,
    price: Euro,
    amount: UInt,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    onAmountChange: (UInt) -> Unit = {}
) {
    val total = price * amount

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

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

                Text(
                    text = total.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            AmountSelector(amount, maxValue = 99u, onChange = onAmountChange)
        }
    }
}

@Preview
@Composable
fun CartItemRowPreview() {
    AppTheme {
        CartItemRow(
            item = MockData.Apple,
            price = 60u.cents,
            amount = 3u
        )
    }
}
