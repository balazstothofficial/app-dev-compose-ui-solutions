package tcs.app.dev.homework2.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import tcs.app.dev.R
import tcs.app.dev.homework2.view.ShopPage
import tcs.app.dev.ui.theme.AppTheme

@Composable
fun ShopNavigationBar(
    page: ShopPage,
    modifier: Modifier = Modifier,
    onPageChange: (ShopPage) -> Unit = {}
) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = page == ShopPage.Shop,
            onClick = { onPageChange(ShopPage.Shop) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Storefront,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.label_shop))
            }
        )
    }
}

@Preview
@Composable
fun ShopNavigationBarPreview() {
    AppTheme {
        ShopNavigationBar(page = ShopPage.Shop)
    }
}
