package tcs.app.dev.homework2.data

import kotlinx.serialization.Serializable

@Serializable
sealed interface Discount {
    fun apply(cart: Cart, price: Euro): Euro

    @Serializable
    data class Percentage(val value: UInt) : Discount {
        init {
            require(value in 0u..100u)
        }

        override fun apply(cart: Cart, price: Euro): Euro {
            val percentageToPay = 100u - value
            val cents = price.cents * percentageToPay / 100u
            return Euro(cents)
        }
    }

    @Serializable
    data class Fixed(val amount: Euro) : Discount {
        override fun apply(cart: Cart, price: Euro): Euro = price - amount
    }

    @Serializable
    data class Bundle(
        val item: Item,
        val amountItemsGet: UInt,
        val amountItemsPay: UInt
    ) : Discount {
        init {
            require(amountItemsGet > amountItemsPay)
        }

        val amountFreeItems by lazy { amountItemsGet - amountItemsPay }

        override fun apply(cart: Cart, price: Euro): Euro {
            val quantity = cart.items[item] ?: return price
            val itemPrice = cart.shop.prices[item] ?: return price

            val groups = quantity / amountItemsGet

            val lastGroup = quantity % amountItemsGet
            val lastGroupDiscount =
                if (lastGroup <= amountItemsPay) 0u.euro
                else itemPrice * (lastGroup - amountItemsPay)

            val discount = itemPrice * groups * amountFreeItems + lastGroupDiscount

            return price - discount
        }
    }
}

fun List<Discount>.clean(): List<Discount> {
    val bundles = filterIsInstance<Discount.Bundle>()
    val fixed = filterIsInstance<Discount.Fixed>()
    val percentage = filterIsInstance<Discount.Percentage>()

    return bundles.filterBest() + fixed + percentage
}

fun List<Discount.Bundle>.filterBest() = this
    .groupBy { bundle -> bundle.item }
    .mapNotNull { bundles ->
        bundles.value.maxWithOrNull { first, second ->
            first.amountFreeItems.compareTo(second.amountFreeItems)
        }
    }
