package tcs.app.dev.homework2

import android.graphics.Bitmap
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tcs.app.dev.homework2.ShopViewModel.State
import tcs.app.dev.homework2.ShopViewModel.UIItem
import tcs.app.dev.homework2.view.ShopPage.Shop
import tcs.app.dev.homework2.data.Cart
import tcs.app.dev.homework2.data.Cart.Companion.Empty as EmptyCart
import tcs.app.dev.homework2.data.Euro
import tcs.app.dev.homework2.data.Item
import tcs.app.dev.homework2.data.Shop
import tcs.app.dev.homework2.data.ShopItems
import tcs.app.dev.homework2.data.clear
import tcs.app.dev.homework2.data.getAvailableItems
import tcs.app.dev.homework2.data.getImage
import tcs.app.dev.homework2.view.ShopPage

typealias CartUIItems = List<Pair<UIItem, UInt>>

/**
 * # Homework 4 — Shop ViewModel
 *
 * Fill in the holes in the implementation if you decided to use the template.
 * Otherwise also adapt the ViewModel to fit your needs.
 *
 * ## Tasks
 *
 * 1. **Expose derived state for the UI**
 *    - Fill the TODOs in `ShopViewModel.State`.
 *
 * 2. **Observe DataStore**
 *    - Observe changes of the persistently stored cart in the `init`-block.
 *
 * 3. **Load items & images**
 *    - Implement `loadItems()`: fetch available items, initialize the cart’s shop, set `uiItems`.
 *    - Implement `loadImages()`: fetch asynchronously images per item.
 *
 * 4. **Cart mutations**
 *    - Implement `addToCart`, `updateCart(item, amount)`, `removeFromCart`
 *      using the private `updateCart { cart -> … }` which persists via DataStore.
 *
 *  ## Hints
 *  - Always keep `cart.shop` intact when updating items.
 *  - Use `viewModelScope.launch { … }` for calling suspending functions.
 *
 * ## Resources
 *
 * - [ViewModels](https://developer.android.com/topic/libraries/architecture/viewmodel)
 * - [Coroutine dispatchers](https://kotlinlang.org/docs/coroutines-basics.html#coroutine-dispatchers)
 * - [Coroutines](https://kotlinlang.org/docs/coroutines-guide.html)
 * - [ViewModel scope](https://developer.android.com/topic/libraries/architecture/coroutines#viewmodelscope)
 * - [StateFlow in ViewModels](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#stateflow)
 * - [StateFlow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/)
 * - [Flow](https://kotlinlang.org/docs/flow.html)
 * - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
 *
 */
class ShopViewModel(private val cartDataStore: DataStore<Cart>) : ViewModel() {
    data class UIItem(val item: Item, val price: Euro, val image: Bitmap? = null)

    data class State(
        val uiItems: List<UIItem> = emptyList(),
        val cart: Cart = EmptyCart,
        val page: ShopPage = Shop
    ) {

        /**
         * TODO: Derive the list of domain items using [uiItems].
         */
        val items: List<Item>
            get() = TODO()

        /**
         * TODO: Join `cart.items` (Map<Item, UInt>) with [uiItems] to produce
         *  a list of ([UIItem], amount).
         */
        val cartUIItems: CartUIItems
            get() = TODO()
    }

    private val mutableState = MutableStateFlow(State())
    val state = mutableState.asStateFlow()

    /**
     * TODO: Collect `cartDataStore.data` such that the state is always updated accordingly.
     */
    init {
        viewModelScope.launch {
            // TODO
        }
    }

    /**
     * TODO: Fetch the shop items using [getAvailableItems] and
     *  initialize the cart and the uiItems in the state.
     *
     * Hint: [Cart.initializeShop] and [ShopItems.uiItems] can be helpful.
     */
    fun loadItems() {
        // TODO
    }

    /**
     * TODO: For each item, load its image asynchronously using [getImage]
     *  and update the state whenever an image arrives.
     *
     * Hint: [State.updateImage] can be helpful.
     */
    fun loadImages() {
        val items = state.value.items

        items.forEach { item ->
            viewModelScope.launch {
                // TODO
            }
        }
    }

    /**
     * TODO: Use [updateCart] to add [item] to the cart
     */
    fun addToCart(item: Item) {
        // TODO
    }

    /**
     * TODO: Use [updateCart] to change the amount of [item] in the cart
     */
    fun updateCart(item: Item, amount: UInt) {
        // TODO
    }

    /**
     * TODO: Use [updateCart] to remove [item] from the cart
     */
    fun removeFromCart(item: Item) {
        // TODO
    }

    /**
     * Update the page in the state.
     */
    fun updatePage(page: ShopPage) {
        mutableState.update { state -> state.copy(page = page) }
    }

    /**
     * Simulate payment.
     */
    fun pay() {
        updateCart { cart -> cart.clear() }
        updatePage(Shop)
    }

    /**
     * Update the cart in the [cartDataStore].
     */
    private fun updateCart(update: (Cart) -> Cart) {
        viewModelScope.launch {
            cartDataStore.updateData(update)
        }
    }
}

// Here are some useful helpers.

val Cart.showCheckout
    get() = items.isNotEmpty()

private fun Cart.initializeShop(items: ShopItems): Cart {
    val shop = Shop(items)

    return if (shop.items.containsAll(items.keys)) copy(shop = Shop(items))
    else Cart(Shop(items))
}

private fun State.updateImage(item: Item, image: Bitmap): State =
    copy(
        uiItems = uiItems
            .map { uiItem -> if (uiItem.item == item) uiItem.copy(image = image) else uiItem }
    )

private val ShopItems.uiItems
    get() = entries.map { (item, price) -> UIItem(item, price) }
