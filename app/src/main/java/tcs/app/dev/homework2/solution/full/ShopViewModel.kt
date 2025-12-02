package tcs.app.dev.homework2.solution.full

import android.graphics.Bitmap
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tcs.app.dev.homework2.view.full.ShopPage.Cart
import tcs.app.dev.homework2.view.full.ShopPage.Shop
import tcs.app.dev.homework2.data.Cart
import tcs.app.dev.homework2.data.Cart.Companion.Empty as EmptyCart
import tcs.app.dev.homework2.data.Discount
import tcs.app.dev.homework2.data.Euro
import tcs.app.dev.homework2.data.Item
import tcs.app.dev.homework2.data.Shop
import tcs.app.dev.homework2.data.ShopItems
import tcs.app.dev.homework2.data.clear
import tcs.app.dev.homework2.data.discountApplicable
import tcs.app.dev.homework2.data.getAvailableDiscounts
import tcs.app.dev.homework2.data.getAvailableItems
import tcs.app.dev.homework2.data.getImage
import tcs.app.dev.homework2.data.minus
import tcs.app.dev.homework2.data.plus
import tcs.app.dev.homework2.data.update
import tcs.app.dev.homework2.solution.full.ShopViewModel.State
import tcs.app.dev.homework2.solution.full.ShopViewModel.State.PageCount.All
import tcs.app.dev.homework2.solution.full.ShopViewModel.State.PageCount.NoCart
import tcs.app.dev.homework2.solution.full.ShopViewModel.UIItem
import tcs.app.dev.homework2.view.full.ShopPage

typealias CartUIItems = List<Pair<UIItem, UInt>>

/**
 * # Homework 2 — Shop ViewModel
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
        val discounts: List<Discount> = emptyList(),
        val cart: Cart = EmptyCart,
        val page: ShopPage = Shop,
        val pageCount: PageCount = NoCart
    ) {
        enum class PageCount(val value: Int) {
            All(ShopPage.entries.size), NoCart(ShopPage.entries.size - 1)
        }

        val items
            get() = uiItems.map { it.item }

        val cartUIItems: CartUIItems
            get() {
                val byItem = uiItems.associateBy { it.item }

                return cart.items.mapNotNull { (item, amount) -> byItem[item]?.let { it to amount } }
            }
    }

    private val mutableState = MutableStateFlow(State())
    val state = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            cartDataStore.data
                .distinctUntilChanged()
                .collect { cart -> mutableState.update { state -> state.copy(cart = cart) } }
        }
    }

    fun loadItems() {
        viewModelScope.launch {
            val items = getAvailableItems().getOrNull() ?: return@launch

            updateCart { cart -> cart.initializeShop(items) }
            mutableState.update { state -> state.copy(uiItems = items.uiItems) }
        }
    }

    fun loadDiscounts() {
        viewModelScope.launch {
            val discounts = getAvailableDiscounts().getOrNull() ?: return@launch

            mutableState.update { state -> state.copy(discounts = discounts) }
        }
    }

    private var loadImageJob: Job? = null

    fun loadImages() {
        loadImageJob?.cancel()

        val itemsSnapshot = state.value.items
        if (itemsSnapshot.isEmpty()) return

        loadImageJob = viewModelScope.launch {
            itemsSnapshot.forEach { item ->
                launch {
                    val image = getImage(item).getOrNull() ?: return@launch

                    mutableState.update { state ->
                        if (itemsSnapshot == state.items) state.updateImage(item, image)
                        else state
                    }
                }
            }
        }
    }

    fun addToCart(item: Item) {
        updateCart { cart -> cart + item }
    }

    fun updateCart(item: Item, amount: UInt) {
        updateCart { cart -> cart.update(item to amount) }
    }

    fun addToCart(discount: Discount) {
        updateCart { cart -> if (cart.discountApplicable(discount)) cart + discount else cart }
    }

    fun removeFromCart(discount: Discount) {
        updateCart { cart -> cart - discount }
    }

    fun removeFromCart(item: Item) {
        updateCart { cart -> cart - item }
    }

    fun finishedProgrammaticScroll() {
        mutableState.update { state ->
            if (!state.cart.showCheckout) state.copy(pageCount = NoCart)
            else state
        }
    }

    fun updatePage(page: ShopPage) {
        mutableState.update { state -> state.copy(page = page) }
    }

    fun pay() {
        updateCart { cart -> cart.clear() }
        updatePage(Shop)
    }

    private fun updateCart(update: (Cart) -> Cart) {
        fun Cart.onCartUpdate() {
            if (!showCheckout) {
                mutableState
                    .update { state -> if (state.page == Cart) state.copy(page = Shop) else state }
            } else {
                mutableState.update { state -> state.copy(pageCount = All) }
            }
        }

        viewModelScope.launch {
            val newCart = cartDataStore.updateData { cart -> update(cart) }
            newCart.onCartUpdate()
        }
    }
}

val Cart.showCheckout
    get() = items.isNotEmpty()

private fun Cart.initializeShop(items: ShopItems): Cart =
    if (shop.items == items.keys) copy(shop = Shop(items))
    else Cart(Shop(items))

private fun State.updateImage(item: Item, image: Bitmap): State =
    copy(
        uiItems = uiItems
            .map { uiItem -> if (uiItem.item == item) uiItem.copy(image = image) else uiItem }
    )

private val ShopItems.uiItems
    get() = entries.map { (item, price) -> UIItem(item, price) }
