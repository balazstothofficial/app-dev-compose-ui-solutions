package tcs.app.dev.homework2.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import tcs.app.dev.homework2.solution.ShopViewModel.UIItem
import tcs.app.dev.homework2.data.Item

fun LazyListScope.itemList(
    uiItems: List<UIItem>,
    shape: Shape,
    onAddToCart: (Item) -> Unit = {}
) {
    items(
        items = uiItems,
        key = { it.item.id }
    ) { uiItem ->
        ItemRow(
            itemName = uiItem.item.name,
            price = uiItem.price,
            image = { modifier ->
                LoadingBitmapImage(
                    modifier = modifier,
                    bitmap = uiItem.image,
                    contentDescription = null
                )
            },
            shape = shape,
            modifier = Modifier.fillMaxWidth()
        ) { onAddToCart(uiItem.item) }
    }
}
