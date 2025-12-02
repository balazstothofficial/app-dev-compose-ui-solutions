package tcs.app.dev.homework2.view.full

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import tcs.app.dev.R
import tcs.app.dev.homework2.data.Discount
import tcs.app.dev.homework2.data.Discount.Bundle
import tcs.app.dev.homework2.data.Discount.Fixed
import tcs.app.dev.homework2.data.Discount.Percentage

@get:Composable
val Discount.text: String
    get() = when (this) {
        is Fixed -> stringResource(R.string.amount_off, amount)
        is Percentage -> stringResource(R.string.percentage_off, value)
        is Bundle -> stringResource(
            R.string.pay_n_items_and_get,
            amountItemsPay,
            item.name,
            amountItemsGet
        )
    }
