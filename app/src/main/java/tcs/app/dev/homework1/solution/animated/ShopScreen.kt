package tcs.app.dev.homework1.solution.animated

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import tcs.app.dev.homework1.data.Cart
import tcs.app.dev.homework1.data.Discount
import tcs.app.dev.homework1.data.MockData
import tcs.app.dev.homework1.data.Shop
import tcs.app.dev.homework1.solution.animated.ShopPage.Discounts
import tcs.app.dev.homework1.solution.animated.ShopPage.Cart
import tcs.app.dev.homework1.solution.animated.ShopPage.Shop
import tcs.app.dev.ui.theme.AppTheme

/**
 * Solution with some basic animations.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(
    shop: Shop,
    availableDiscounts: List<Discount>,
    modifier: Modifier = Modifier
) {
    var cart by rememberSaveable { mutableStateOf(Cart(shop = shop)) }

    val listScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(initialPage = 0, pageCount = {
        if (cart.isReadyForCheckout) ShopPage.entries.size
        else ShopPage.entries.size - 1
    })

    val page = ShopPage.from(pagerState.currentPage)

    fun scrollTo(
        page: ShopPage,
        beforeScroll: suspend () -> Unit = {},
        afterScroll: suspend () -> Unit = {}
    ) {
        scope.launch {
            beforeScroll()
            pagerState.animateScrollToPage(page.number)
            afterScroll()
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(listScrollBehavior.nestedScrollConnection),
        topBar = {
            ShopTopBar(
                cartCount = cart.totalCount,
                page = page,
                goToCartEnabled = cart.isReadyForCheckout,
                scrollBehavior = listScrollBehavior,
                onGoToCart = { scrollTo(Cart) },
                onGoToShop = { scrollTo(Shop) }
            )
        },
        bottomBar = {
            ShopBottomBar(
                page = page,
                payingEnabled = cart.payingEnabled,
                cartTotal = cart.price,
                onPageChange = { page -> scrollTo(page) },
                onPay =  { scrollTo(Shop, afterScroll = { cart = Cart(shop) }) }
            )
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            contentPadding = paddingValues,
            key = { it }
        ) { page ->
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                contentPadding = PaddingValues(all = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val rowShape = RoundedCornerShape(20.dp)
                val onCartUpdate = { newCart: Cart -> cart = newCart }

                when (ShopPage.from(page)) {
                    Shop -> itemList(cart, rowShape, onCartUpdate)
                    Discounts -> discountList(cart, rowShape, availableDiscounts, onCartUpdate)
                    Cart -> cartList(cart, rowShape, onCartUpdate)
                }
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
