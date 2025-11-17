package tcs.app.dev.exercise.testing

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tcs.app.dev.exercise.testing.data.Cart
import tcs.app.dev.exercise.testing.data.Item
import tcs.app.dev.exercise.testing.data.update

/**
 * Minimalistic example of a viewModel storing a Cart persistently via DataStore.
 *
 * Do the exercises in the test directories.
 */
class CartViewModel(
    private val cartDataStore: DataStore<Cart>,
    initialCart: Cart
) : ViewModel() {

    data class State(val cart: Cart)

    private val mutableState = MutableStateFlow(State(initialCart))
    val state = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            cartDataStore.data
                .distinctUntilChanged()
                .collect { cart -> mutableState.update { state -> state.copy(cart = cart) } }
        }
    }

    fun changeItemAmount(item: Item, amount: UInt) {
        viewModelScope.launch {
            cartDataStore.updateData { cart -> cart.update(item to amount) }
        }
    }
}
