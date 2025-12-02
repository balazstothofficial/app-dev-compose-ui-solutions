package tcs.app.dev.homework2.view.full

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
import tcs.app.dev.homework2.solution.full.CartUIItems
import tcs.app.dev.homework2.data.Discount
import tcs.app.dev.homework2.data.Item

fun LazyListScope.cartList(
    cartUIItems: CartUIItems,
    cartDiscounts: List<Discount>,
    shape: Shape,
    onRemoveItem: (Item) -> Unit = {},
    onRemoveDiscount: (Discount) -> Unit = {},
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

    items(items = cartDiscounts, key = { it.toString() }) { discount ->
        DismissibleRow(
            shape = shape,
            onDismiss = { onRemoveDiscount(discount) }
        ) {
            DiscountRow(
                text = discount.text,
                modifier = Modifier.fillMaxWidth(),
                shape = shape,
                onClick = { onRemoveDiscount(discount) },
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
}
