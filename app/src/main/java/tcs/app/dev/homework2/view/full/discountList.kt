package tcs.app.dev.homework2.view.full

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
import tcs.app.dev.homework2.data.Discount

fun LazyListScope.discountList(
    shape: Shape,
    discounts: List<Discount>,
    discountApplicable: (Discount) -> Boolean,
    onAddToCart: (Discount) -> Unit = {}
) {
    items(items = discounts, key = { it.toString() }) { discount ->
        DiscountRow(
            text = discount.text,
            shape = shape,
            modifier = Modifier.fillMaxWidth(),
            buttonEnabled = discountApplicable(discount),
            buttonIcon = {
                Icon(
                    imageVector = Icons.Outlined.AddShoppingCart,
                    contentDescription = stringResource(R.string.description_add_to_cart)
                )
            },
            onClick = { onAddToCart(discount) }
        )
    }
}
