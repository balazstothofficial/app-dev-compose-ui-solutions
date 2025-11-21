package tcs.app.dev.homework1.solution.animated

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import tcs.app.dev.R
import tcs.app.dev.homework1.data.Cart
import tcs.app.dev.homework1.data.Discount
import tcs.app.dev.homework1.data.plus

fun LazyListScope.discountList(
    cart: Cart,
    shape: Shape,
    discounts: List<Discount>,
    onCartUpdate: (Cart) -> Unit = {}
) {
    items(items = discounts, key = { it }) { discount ->
        DiscountRow(
            discount = discount,
            shape = shape,
            modifier = Modifier.fillMaxWidth(),
            buttonEnabled = discount !in cart.discounts,
            buttonIcon = {
                Icon(
                    imageVector = Icons.Outlined.AddShoppingCart,
                    contentDescription = stringResource(R.string.description_add_to_cart)
                )
            },
            onClick = { if (discount !in cart.discounts) onCartUpdate(cart + discount) }
        )
    }
}
