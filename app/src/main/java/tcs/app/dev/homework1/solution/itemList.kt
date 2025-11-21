package tcs.app.dev.homework1.solution

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import tcs.app.dev.homework1.data.Cart
import tcs.app.dev.homework1.data.plus

fun LazyListScope.itemList(cart: Cart, shape: Shape, onCartUpdate: (Cart) -> Unit) {
    items(
        items = cart.shop.prices.toList(),
        key = { (item, _) -> item.id }
    ) { (item, price) ->
        ItemRow(
            item = item,
            price = price,
            shape = shape,
            modifier = Modifier.fillMaxWidth()
        ) { onCartUpdate(cart + item) }
    }
}
