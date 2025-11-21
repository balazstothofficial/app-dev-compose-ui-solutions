package tcs.app.dev.homework1.solution

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import tcs.app.dev.R
import tcs.app.dev.homework1.data.Cart
import tcs.app.dev.homework1.data.minus
import tcs.app.dev.homework1.data.update

fun LazyListScope.cartList(
    cart: Cart,
    shape: Shape,
    onCartUpdate: (Cart) -> Unit = {}
) {
    items(
        items = cart.items.toList(),
        key = { (item, _) -> item.id }
    ) { (item, amount) ->
        val price = cart.shop.prices[item]

        if (price != null) {
            CartItemRow(
                item = item,
                price = price,
                amount = amount,
                modifier = Modifier.fillMaxWidth(),
                shape = shape
            ) { amount -> onCartUpdate(cart.update(item to amount)) }
        }
    }

    items(items = cart.discounts, key = { it }) { discount ->
        DiscountRow(
            discount = discount,
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
            onClick = { onCartUpdate(cart - discount) },
            buttonIcon = {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = stringResource(R.string.description_remove_from_cart)
                )
            },
            buttonColors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        )
    }
}
