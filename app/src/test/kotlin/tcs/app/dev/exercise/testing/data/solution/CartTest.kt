package tcs.app.dev.exercise.testing.data.solution

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import tcs.app.dev.exercise.testing.data.Cart
import tcs.app.dev.exercise.testing.data.Item
import tcs.app.dev.exercise.testing.data.MockData
import tcs.app.dev.exercise.testing.data.plus
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * # Unit testing in Kotlin with JUnit
 *
 * ## Goals
 * 1. Understand lifecycle annotations: `@BeforeTest`, `@AfterTest`, `@BeforeAll`, `@AfterAll`.
 * 2. Write classic example-based tests with `@Test`.
 * 3. Generate tests dynamically with `@TestFactory` and `DynamicTest`.
 * 4. Create parameterized tests with `@ParameterizedTest` and `@MethodSource`.
 * 5. Learn how `@JvmStatic` and `@JvmName` work.
 *
 * ## How to run
 * - Use your IDE’s test runner or Gradle: `./gradlew test`
 *
 * ## Lifecycle (per-test) setup and teardown
 *
 * - **Task:** Make `@BeforeTest fun setup()` create a fresh, empty `Cart` for every test.
 *   This ensures each test runs in isolation with a known starting state.
 *
 * - **Task:** Make `@AfterTest fun cleanUp()` reset the cart (or release resources) after each test.
 *   This mirrors real-world cleanup and prevents state leakage between tests.
 *
 * **What it does:** `@BeforeTest` and `@AfterTest` (from `kotlin.test`) run before/after *each* test
 * method in this class. Use them to prepare and clean per-test state.
 * The annotations are equivalent to `@BeforeEach` and `@AfterEach` form `org.junit.jupiter.api`.
 *
 * ## Simple tests (`@Test`)
 * - **Task (testItemCount):** Start from a empty cart, add:
 *   - 5 × `Apple`, 3 × `Banana`
 *   then assert `itemCount == 8u`.
 *   > Demonstrates adding items and asserting the expected result.
 *
 * - **Task (testAddToCart):** Build two carts.
 *   One by passing `items = {Apple→5u, Banana→3u}` to the constructor and another one by adding
 *   the same items using `Cart.plus`.
 *   Assert that the two carts contain the same items.
 *   > Demonstrates equivalence of two construction paths (direct map vs. operations).
 *
 * **What it does:** `@Test` marks a standard, self-contained test case.
 *
 * ## Types of assertions
 *
 * Assertions verify the correctness of your code’s behavior.
 * Here are the most common ones in `kotlin.test` and JUnit:
 *
 * - `assertEquals(expected, actual)` → checks for equality.
 * - `assertNotEquals(unexpected, actual)` → checks for inequality.
 * - `assertTrue(condition)` / `assertFalse(condition)` → checks a Boolean condition.
 * - `assertNull(value)` / `assertNotNull(value)` → checks for nullability.
 * - `assertSame(expected, actual)` / `assertNotSame(expected, actual)` → checks object identity.
 * - `assertContains(collection, element)` (from `kotlin.test`) → checks membership.
 * - `assertFailsWith<T>(block)` (from `kotlin.test`) → checks that a block throws a specific exception.
 * - `assertThrows<T>(block)` (from JUnit 5) → same purpose; more common when using JUnit.
 * - `fail(message)` → explicitly fails a test.
 *
 * - **Task (testAddWrongItem):** Add a test that verifies invalid input is handled correctly.
 *    Try adding an item that is not in the shop to your cart.
 *
 * ## Dynamic tests (`@TestFactory`)
 * - **Task:** Implement `testAddToCartTestFactory`, such that each entry of `testItems` is mapped
 *   to a `DynamicTest` that:
 *   1) creates a `Cart` with `items`, and 2) asserts its `itemCount` equals `expectedCount`.
 *
 * **What it does:** `@TestFactory` returns a collection/stream of tests built at runtime.
 * Useful when the number of tests or their parameters are computed dynamically.
 *
 * **Drawbacks:**
 * - `@BeforeTest` / `@AfterTest` (or  `@BeforeEach` / `@AfterEach` ) are **not** executed
 *   per test case.
 *
 *
 * ## Parameterized tests (`@ParameterizedTest` + `@MethodSource`)
 * - **Task:** Implement `testAddToCartParametrized(items, expectedCount)` to:
 *   1) create a `Cart` with `items`, and 2) assert `itemCount == expectedCount.toUInt()`.
 *
 * - **Data source:** `@MethodSource("getParameterizedTestItems")` expects a `@JvmStatic`
 *   provider that returns `Stream<Arguments>` or an `Iterable` of `Arguments`.
 *   Here we provide it via `parameterizedTestItems()` and the `parameterizedTestItems` property.
 *
 * **What it does:** Parameterized tests run the same test logic over multiple inputs.
 *
 * **Drawbacks & notes:**
 * - Using the annotations correctly is more verbose and less type-safe than plain Kotlin calls.
 * - JUnit parameterization does not accept value class types `UInt` directly as a parameter,
 *   so we pass an `Int` and convert inside the test (`expectedCount.toUInt()`).
 *
 *
 * ## Test data in companion object
 * - **Task:** Find the differences between the property `parameterizedTestItems` and
 *   the function `parameterizedTestItems()`, when used as the `MethodSource` by the
 *   parameterized test.
 *
 * **What it does:**
 * - `testItems` is a list of `(Map<Item, UInt> to UInt)` pairs used to feed
 *    dynamic and parameterized tests with consistent data.
 * - `@JvmStatic` makes the member look like a real static method/field to Java.
 *    Fields can be accessed with a `get`-Prefix. (Mutable variables also get a setter.)
 * - JUnit 5 discovers `@MethodSource` factories via Java static members,
 *   so `@JvmStatic` is needed for sources in Kotlin companion objects.
 *
 *
 * ## Class-level setup/teardown (`@BeforeAll` / `@AfterAll`)
 * - **Task:** Observe the console output to learn *when* `@BeforeAll` and `@AfterAll` run
 *   relative to individual tests.
 *
 * - **Task:** Explain the effect of:
 *   - Find out the purpose of `@JvmName`.
 *
 * **What it does:** These methods handle class-wide initialization/cleanup.
 * They’re useful for expensive, shared resources (databases, servers, temp directories, etc.).
 *
 * ## Resources
 * - [Kotlin test API](https://kotlinlang.org/api/core/kotlin-test/)
 * - [Kotlin JUnit tutorial](https://kotlinlang.org/docs/jvm-test-using-junit.html)
 * - [JUnit](https://docs.junit.org/current/user-guide/)
 * - [@JvmStatic](https://kotlinlang.org/docs/java-to-kotlin-interop.html#static-methods)
 * - [@JvmName](https://kotlinlang.org/docs/java-to-kotlin-interop.html#handling-signature-clashes-with-jvmname)
 * - [Java interop](https://kotlinlang.org/docs/java-interop.html)
 */
class CartTest {

    private lateinit var defaultCart: Cart

    @BeforeTest
    fun setup() {
        defaultCart = MockData.EmptyExampleCart
    }

    @AfterTest
    fun cleanUp() {
        defaultCart = MockData.EmptyExampleCart
    }

    @Test
    fun testItemCount() {
        defaultCart += MockData.Apple to 3u
        defaultCart += MockData.Banana to 5u

        assertEquals(8u, defaultCart.itemCount)
    }

    @Test
    fun testAddToCart() {
        defaultCart = defaultCart
            .copy(items = mapOf(MockData.Banana to 5u))

        assertEquals(5u, defaultCart.itemCount)
    }

    @Test
    fun testAddWrongItem() {
        assertThrows<IllegalArgumentException> {
            defaultCart += Item("test", "Test")
        }
    }

    @TestFactory
    fun testAddToCartTestFactory(): List<DynamicTest> = testItems
        .mapIndexed { index, (items, expectedCount) ->
            DynamicTest.dynamicTest("$index: Adding $items. Expecting item count: $expectedCount") {
                val cart = defaultCart.copy(items = items)
                assertEquals(expectedCount, cart.itemCount)
            }
        }

    @ParameterizedTest(name = "{index}: Adding {0}. Expecting: {1}")
    @MethodSource("parameterizedTestItems")
    fun testAddToCartParametrized(items: Map<Item, UInt>, expectedCount: Int) {
        val cart = defaultCart.copy(items = items)
        assertEquals(expectedCount.toUInt(), cart.itemCount)
    }

    companion object {

        val testItems = listOf(
            mapOf(MockData.Apple to 5u, MockData.Banana to 3u) to 8u,
            mapOf(MockData.Apple to 10000u) to 10000u,
            mapOf(MockData.Apple to 5u, MockData.Banana to 3u, MockData.Onion to 14u) to 22u,
            mapOf<Item, UInt>() to 0u,
            mapOf(MockData.Apple to 0u, MockData.Banana to 0u) to 0u
        )

        @JvmStatic
        val parameterizedTestItems = parameterizedTestItems()

        @JvmStatic
        fun parameterizedTestItems() = testItems.map { (items, amount) ->
            Arguments.of(items, amount.toInt())
        }

        @BeforeAll
        @JvmStatic
        @JvmName("setupStatic")
        fun setup() {
            println("Setting up...")
        }

        @AfterAll
        @JvmStatic
        @JvmName("sleanUpStatic")
        fun cleanUp() {
            println("Cleaning up...")
        }
    }
}
