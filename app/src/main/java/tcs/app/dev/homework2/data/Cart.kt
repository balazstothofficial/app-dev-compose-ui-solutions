package tcs.app.dev.homework2.data

import kotlinx.serialization.Serializable
import kotlin.getValue

@Serializable
data class Cart(
    val shop: Shop,
    val items: Map<Item, UInt> = emptyMap(),
    private val allDiscounts: List<Discount> = emptyList()
) {
    init {
        require(shop.items.containsAll(items.keys))
    }

    val discounts by lazy {
        allDiscounts.clean()
    }

    val price: Euro by lazy {
        val price = items
            .map { (item, amount) -> shop.prices[item]!! * amount }
            .fold(0u.euro, Euro::plus)

        discounts
            .fold(price) { total, price -> price.apply(this, total) }
    }

    val itemCount by lazy {
        items.values.sum()
    }

    val discountsCount by lazy {
        discounts.size.toUInt()
    }

    val totalCount by lazy {
        discountsCount + itemCount
    }

    companion object {
        val Empty = Cart(Shop(emptyMap()))
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

operator fun Cart.plus(other: Discount): Cart = copy(allDiscounts = this.discounts + other)

operator fun Cart.plus(other: List<Discount>): Cart = copy(allDiscounts = this.discounts + other)

operator fun Cart.minus(other: Item): Cart = copy(items = this.items - other)

operator fun Cart.minus(other: Discount): Cart = copy(allDiscounts = this.discounts - other)

val Cart.isReadyForCheckout
    get() = 0u < itemCount

fun Cart.discountApplicable(discount: Discount): Boolean = discount !in discounts

fun Cart.clear(): Cart = copy(items = emptyMap(), allDiscounts = emptyList())
