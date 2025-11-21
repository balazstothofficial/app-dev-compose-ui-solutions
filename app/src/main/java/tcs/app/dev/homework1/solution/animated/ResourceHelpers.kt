package tcs.app.dev.homework1.solution.animated

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import tcs.app.dev.R
import tcs.app.dev.homework1.data.Discount
import tcs.app.dev.homework1.data.Item
import tcs.app.dev.homework1.data.MockData

@get:Composable
val Item.name: String
    get() = stringResource(MockData.getName(this))

@get:Composable
val Item.image: Painter
    get() = painterResource(MockData.getImage(this))

@get:Composable
val Discount.text: String
    get() = when (this) {
        is Discount.Fixed -> stringResource(R.string.amount_off, amount)
        is Discount.Percentage -> stringResource(R.string.percentage_off, value)
        is Discount.Bundle ->
            stringResource(
                id = R.string.pay_n_items_and_get,
                amountItemsPay,
                item.name,
                amountItemsGet
            )
    }
