package tcs.app.dev.homework1.solution.animated

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
    AnimatedVisibility(
        visible = page != ShopPage.Cart,
        enter = slideInVertically { it },
        exit = slideOutVertically { it }
    ) {
        ShopNavigationBar(
            page = page,
            modifier = modifier.animateContentSize(),
            onPageChange = onPageChange
        )
    }

    AnimatedVisibility(
        visible = page == ShopPage.Cart,
        enter = slideInVertically { it },
        exit = slideOutVertically { it }
    ) {
        CartBottomBar(
            price = cartTotal,
            payingEnabled = payingEnabled,
            modifier = modifier.animateContentSize(),
            onPay = onPay
        )
    }
}
