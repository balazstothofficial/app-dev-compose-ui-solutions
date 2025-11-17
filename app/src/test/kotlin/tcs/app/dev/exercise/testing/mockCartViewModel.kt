package tcs.app.dev.exercise.testing

import tcs.app.dev.exercise.testing.data.Cart

fun mockCartViewModel(initialValue: Cart) = CartViewModel(
    MockDataStore(initialValue),
    initialValue
)
