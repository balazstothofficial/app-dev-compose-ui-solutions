package tcs.app.dev.exercise.testing.data

import kotlinx.serialization.Serializable

typealias ShopItems = Map<Item, Euro>

@Serializable
@JvmInline
value class Shop(val prices: ShopItems) {
    val items
        get() = prices.keys
}
