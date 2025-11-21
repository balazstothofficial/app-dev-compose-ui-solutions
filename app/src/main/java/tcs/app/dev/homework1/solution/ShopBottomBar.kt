package tcs.app.dev.homework1.solution

import androidx.compose.animation.animateContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tcs.app.dev.homework1.data.Euro

@Composable
fun ShopBottomBar(
    page: ShopPage,
    payingEnabled: Boolean,
    cartTotal: Euro,
    modifier: Modifier = Modifier,
    onPageChange: (ShopPage) -> Unit = {},
    onPay: () -> Unit = {}
) {
    if (page != ShopPage.Cart) {
        ShopNavigationBar(
            page = page,
            modifier = modifier.animateContentSize(),
            onPageChange = onPageChange
        )
    } else {
        CartBottomBar(
            price = cartTotal,
            payingEnabled = payingEnabled,
            modifier = modifier.animateContentSize(),
            onPay = onPay
        )
    }
}
