package tcs.app.dev.homework2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.homework2.data.euro
import tcs.app.dev.homework2.view.ShopBottomBar
import tcs.app.dev.homework2.view.ShopPage
import tcs.app.dev.homework2.view.ShopTopBar
import tcs.app.dev.homework2.view.cartList
import tcs.app.dev.homework2.view.itemList
import tcs.app.dev.ui.theme.AppTheme

/**
 * # Homework 4 — ViewModel for Shop App
 *
 * We will remove the state management from the view and put it into a ViewModel.
 *
 * ## Option 1
 *
 * - Take your solution for the homework from previous week and
 *   manage your UI State using (a) ViewModel(s).
 * - You can use as much as you want from the template code,
 *   but make sure that your shopping cart is persisted.
 *
 * ## Option 2
 *
 * - Wire this screen to the provided `ShopViewModel` and
 *   finish the implementation of `ShopViewModel`.
 * - This is a simplified version of the solution for the homework from previous week.
 * - You just have to focus on this composable.
 *   The other composables are (basically) stateless.
 * - Finish the tasks below and in `ShopViewModel`.
 *
 * ## Tasks
 *
 * 1. **Collect state from the ViewModel**
 *    - Use `viewModel.state.collectAsStateWithLifecycle()` and replace all hardcoded placeholders.
 *    - Load the shop items using the ViewModel on the first composition.
 *    - Load the item images using the ViewModel whenever the shop items change.
 *
 * ## Hints
 *
 * - Use [LaunchedEffect] for loading the items and images.
 *
 * ## Remarks
 *
 * - In bigger applications we often have multiple ViewModels.
 *   For that it is important on how the ViewModels are scoped.
 *   See the resources for more information.
 *
 * ## Resources
 *
 * - [ViewModel in UI](https://developer.android.com/topic/architecture/ui-layer#udf)
 * - [collectAsSateWithLifecycle](https://developer.android.com/develop/ui/compose/state#use-other-types-of-state-in-jetpack-compose)
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(
    modifier: Modifier = Modifier,
    viewModel: ShopViewModel = shopViewModel()
) {
    // TODO: Replace by viewModel state
    val page = ShopPage.Cart

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            // TODO: Fill the parameters using the viewModel
            ShopTopBar(
                cartCount = 0u,
                page = page,
                goToCartEnabled = true,
                onGoToCart = { },
                onGoToShop = { }
            )
        },
        bottomBar = {
            // TODO: Fill the parameters using the viewModel
            ShopBottomBar(
                page = page,
                payingEnabled = true,
                cartTotal = 0u.euro,
                onPay = {}
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(paddingValues),
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val rowShape = RoundedCornerShape(20.dp)

            // TODO: Fill the parameters using the viewModel
            when (page) {
                ShopPage.Shop -> itemList(
                    uiItems = emptyList(),
                    shape = rowShape,
                    onAddToCart = {}
                )

                ShopPage.Cart -> cartList(
                    cartUIItems = emptyList(),
                    shape = rowShape,
                    onRemoveItem = {},
                    onUpdateItemAmount = { _, _ -> }
                )
            }
        }
    }
}

@Preview
@Composable
fun ShopScreenPreview() {
    AppTheme {
        ShopScreen()
    }
}
