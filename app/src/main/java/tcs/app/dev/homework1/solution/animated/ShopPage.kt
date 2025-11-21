package tcs.app.dev.homework1.solution.animated

enum class ShopPage(val number: Int) {
    Shop(0), Discounts(1), Cart(2);

    companion object {
        fun from(number: Int) = entries.firstOrNull { it.number == number } ?: Shop
    }
}
