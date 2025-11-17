package tcs.app.dev.homework2.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import tcs.app.dev.R
import tcs.app.dev.homework2.view.ShopPage
import tcs.app.dev.homework2.data.Euro

@Composable
fun ShopBottomBar(
    page: ShopPage,
    payingEnabled: Boolean,
    cartTotal: Euro,
    modifier: Modifier = Modifier,
    onPay: () -> Unit = {}
) {
    if (page == ShopPage.Cart) {
        CartBottomBar(
            price = cartTotal,
            payingEnabled = payingEnabled,
            modifier = modifier,
            onPay = onPay
        )
    } else {
        NavigationBar(modifier = modifier) {
            NavigationBarItem(
                selected = true,
                onClick = { },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Storefront,
                        contentDescription = null
                    )
                },
                label = {
                    Text(stringResource(R.string.label_shop))
                }
            )
        }
    }
}
