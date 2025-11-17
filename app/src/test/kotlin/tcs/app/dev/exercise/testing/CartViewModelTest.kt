package tcs.app.dev.exercise.testing

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import tcs.app.dev.exercise.testing.data.MockData
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * # ViewModel Testing
 *
 * Integration tests for viewModels can reveal bugs in your viewModel logic.
 *
 * ## Tasks
 * - Familiarize yourself with the [CartViewModel] implementation.
 * - Familiarize yourself with the [MockDataStore] implementation. What is it good for?
 * - Watch [Coroutine testing video](https://www.youtube.com/watch?v=nKCsIHWircA).
 * - Explain what [runUnconfinedMainTest] and [runStandardMainTest] are doing.
 * - Explain what the tests are doing.
 * - What happens if you change [testUpdateCart] to use [runStandardMainTest]?
 *   Fix the issue using [advanceUntilIdle].
 *
 * ## Remark
 * - If you want to use the `MainDispatcherRule` from the tutorials,
 *   you have to move these tests to the androidTest directory. Otherwise it won't work.
 *
 * ## Resources
 * - [Coroutine testing video](https://www.youtube.com/watch?v=nKCsIHWircA)
 * - [Coroutine testing on Android guide](https://developer.android.com/kotlin/coroutines/test)
 */
class CartViewModelTest {

    @Test
    fun testInitialCart() = runUnconfinedMainTest {
        val viewModel = mockCartViewModel(MockData.EmptyExampleCart)

        assertEquals(MockData.EmptyExampleCart, viewModel.state.value.cart)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testUpdateCart() = runUnconfinedMainTest {
        val viewModel = mockCartViewModel(MockData.EmptyExampleCart)

        viewModel.changeItemAmount(MockData.Apple, 5u)
        assertEquals(5u, viewModel.state.value.cart.items[MockData.Apple])
    }
}
