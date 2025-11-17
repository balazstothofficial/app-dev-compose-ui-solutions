package tcs.app.dev.homework2.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.R
import tcs.app.dev.homework2.data.Euro
import tcs.app.dev.homework2.data.euro
import tcs.app.dev.ui.theme.AppTheme

@Composable
fun CartBottomBar(
    price: Euro,
    modifier: Modifier = Modifier,
    payingEnabled: Boolean = true,
    onPay: () -> Unit = {}
) {
    NavigationBar(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = stringResource(R.string.total_price, price),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )

            Button(
                enabled = payingEnabled,
                onClick = onPay
            ) {
                Text(text = stringResource(R.string.label_pay))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartBottomBarPreview() {
    AppTheme {
        CartBottomBar(price = 3u.euro)
    }
}
