package tcs.app.dev.homework2.view

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tcs.app.dev.R
import tcs.app.dev.homework2.view.ShopPage
import tcs.app.dev.homework2.view.ShopPage.Cart
import tcs.app.dev.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopTopBar(
    page: ShopPage,
    cartCount: UInt,
    modifier: Modifier = Modifier,
    goToCartEnabled: Boolean = true,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onGoToCart: () -> Unit = {},
    onGoToShop: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            val title = when (page) {
                ShopPage.Shop -> stringResource(R.string.name_shop)
                Cart -> stringResource(R.string.title_cart)
            }

            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            if (page != Cart) {
                IconButton(
                    enabled = goToCartEnabled,
                    modifier = Modifier.size(64.dp),
                    onClick = onGoToCart
                ) {
                    val moreThan99 = stringResource(R.string.more_than_99)
                    val countText =
                        if (cartCount < 100u) cartCount.toString()
                        else moreThan99

                    BadgedBox(
                        badge = {
                            if (cartCount > 0u) {
                                Badge { Text(text = countText) }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = stringResource(R.string.description_go_to_cart)
                        )
                    }
                }
            }
        },
        navigationIcon = {
            if (page == Cart) {
                IconButton(onClick = onGoToShop) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = stringResource(R.string.description_go_to_shop)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ShopTopBarPreview() {
    AppTheme {
        ShopTopBar(
            page = ShopPage.Shop,
            cartCount = 6u
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CartTopBarPreview() {
    AppTheme {
        ShopTopBar(
            page = Cart,
            cartCount = 6u
        )
    }
}
