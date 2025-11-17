package tcs.app.dev.exercise.testing.data

import kotlinx.serialization.Serializable
import kotlin.getValue

@Serializable
data class Cart(
    val shop: Shop,
    val items: Map<Item, UInt> = emptyMap()
) {
    init {
        require(shop.items.map { it.id }.containsAll(items.keys.map { it.id }))
    }

    val price: Euro by lazy {
        items
            .map { (item, amount) -> shop.prices[item]!! * amount }
            .fold(0u.euro, Euro::plus)
    }

    val itemCount by lazy {
        items.values.sum()
    }
}

fun Cart.update(other: Pair<Item, UInt>): Cart = copy(items = items + other)

operator fun Cart.plus(other: Pair<Item, UInt>): Cart {
    val (item, amount) = other

    val currentAmount = items[item] ?: 0u
    val pair = other.copy(second = amount + currentAmount)

    return copy(items = items + pair)
}

operator fun Cart.plus(other: Item): Cart = this + (other to 1u)

operator fun Cart.minus(other: Item): Cart = copy(items = this.items - other)
