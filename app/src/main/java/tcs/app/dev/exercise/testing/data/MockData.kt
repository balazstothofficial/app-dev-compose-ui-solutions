package tcs.app.dev.exercise.testing.data

object MockData {
    val Apple = Item("apple", "Apple")
    val Banana = Item("banana", "Banana")

    val Onion = Item("onion", "Onion")

    private val ExampleItems = mapOf(
        Apple to 60u.cents,
        Banana to 90u.cents,
        Onion to 40u.cents
    )

    val ExampleCart =
        Cart(Shop(prices = ExampleItems), items = mapOf(Apple to 1u, Banana to 1u))

    val EmptyExampleCart =
        Cart(Shop(prices = ExampleItems))
}
