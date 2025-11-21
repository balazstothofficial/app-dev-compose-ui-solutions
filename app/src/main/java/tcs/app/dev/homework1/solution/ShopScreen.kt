package tcs.app.dev.homework1.solution

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.homework1.data.Cart
import tcs.app.dev.homework1.data.Discount
import tcs.app.dev.homework1.data.MockData
import tcs.app.dev.homework1.data.Shop
import tcs.app.dev.homework1.solution.ShopPage.Cart
import tcs.app.dev.homework1.solution.ShopPage.Discounts
import tcs.app.dev.homework1.solution.ShopPage.Shop
import tcs.app.dev.ui.theme.AppTheme

/**
 * Solution without animations.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(
    shop: Shop,
    availableDiscounts: List<Discount>,
    modifier: Modifier = Modifier
) {
    var cart by rememberSaveable { mutableStateOf(Cart(shop = shop)) }
    var page by rememberSaveable { mutableStateOf(Shop) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ShopTopBar(
                cartCount = cart.totalCount,
                page = page,
                goToCartEnabled = cart.isReadyForCheckout,
                onGoToCart = { page = Cart },
                onGoToShop = { page = Shop }
            )
        },
        bottomBar = {
            ShopBottomBar(
                page = page,
                payingEnabled = cart.payingEnabled,
                cartTotal = cart.price,
                onPageChange = { newPage -> page = newPage },
                onPay = {
                    page = Shop
                    cart = Cart(shop)
                }
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
            val onCartUpdate = { newCart: Cart -> cart = newCart }

            when (page) {
                Shop -> itemList(cart, rowShape, onCartUpdate)
                Discounts -> discountList(cart, rowShape, availableDiscounts, onCartUpdate)
                Cart -> cartList(cart, rowShape, onCartUpdate)
            }
        }
    }
}

private val Cart.isReadyForCheckout
    get() = items.isNotEmpty()

private val Cart.payingEnabled
    get() = 0u < itemCount

@Preview
@Composable
fun ShopScreenPreview() {
    AppTheme {
        ShopScreen(
            shop = MockData.ExampleShop,
            availableDiscounts = MockData.ExampleDiscounts
        )
    }
}
