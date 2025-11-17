package tcs.app.dev.homework2

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import tcs.app.dev.homework2.data.Cart
import tcs.app.dev.homework2.data.cartDataStore

/**
 * Provides a `ShopViewModel` instance for composables.
 *
 * ## What happens here:
 * - We obtain a `DataStore<Cart>` from the application `Context` via the `cartDataStore` extension.
 * - We build a `ViewModel` using `viewModelFactory { initializer { … } }`, which lets us pass
 *   constructor parameters (the `DataStore<Cart>`) to `ShopViewModel`.
 * - We return the instance via `viewModel(...)`, so Compose can remember/scope it to the current
 *   NavBackStack/Composition.
 *
 * ## Resources
 * - [ViewModelFactory](https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories)
 * - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
 * - [Android context](https://developer.android.com/reference/android/content/Context)
 */
@Composable
fun shopViewModel(
    cartDataStore: DataStore<Cart> = LocalContext.current.applicationContext.cartDataStore
): ShopViewModel = viewModel(
    factory = viewModelFactory {
        initializer { ShopViewModel(cartDataStore) }
    }
)
