package tcs.app.dev.homework2.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import tcs.app.dev.homework2.solution.CartUIItems
import tcs.app.dev.homework2.data.Item
import tcs.app.dev.homework2.view.full.DismissibleRow

fun LazyListScope.cartList(
    cartUIItems: CartUIItems,
    shape: Shape,
    onRemoveItem: (Item) -> Unit = {},
    onUpdateItemAmount: (Item, UInt) -> Unit = { _, _ -> }
) {
    items(
        items = cartUIItems,
        key = { (uiItem, _) -> uiItem.item.id }
    ) { (uiItem, amount) ->
        DismissibleRow(
            shape = shape,
            onDismiss = { onRemoveItem(uiItem.item) }
        ) {
            CartItemRow(
                itemName = uiItem.item.name,
                price = uiItem.price,
                amount = amount,
                image = { modifier ->
                    LoadingBitmapImage(
                        modifier = modifier,
                        bitmap = uiItem.image,
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = shape
            ) { amount -> onUpdateItemAmount(uiItem.item, amount) }
        }
    }
}
