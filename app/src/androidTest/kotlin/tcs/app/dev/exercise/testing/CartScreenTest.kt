package tcs.app.dev.exercise.testing

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.datastore.core.DataStoreFactory
import androidx.test.core.app.ApplicationProvider
import org.junit.Rule
import org.junit.Test
import tcs.app.dev.R
import tcs.app.dev.exercise.testing.data.CartSerializer
import tcs.app.dev.exercise.testing.data.MockData
import java.io.File
import java.util.UUID

/**
 * # UI Testing
 *
 * UI tests for ComposeUI.
 *
 * ## Tasks
 * - Familiarize yourself with the [CartScreen] implementation.
 * - What are the tests doing? Try to change values, such that a test fails.
 * - What is the problem if we use [CartScreen] directly in the tests?
 *   How is that problem solved?
 *
 * ## Resources
 * - [ComposeUI Testing](https://developer.android.com/develop/ui/compose/testing)
 */
class CartScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun increaseTest() {
        composeTestRule.setContent {
            TestCartScreen()
        }

        composeTestRule
            .onNodeWithText(totalAmountText(2u))
            .assertExists()

        composeTestRule
            .onAllNodesWithContentDescription(increaseDescription)
            .onFirst()
            .performClick()

        composeTestRule
            .onNodeWithText(totalAmountText(3u))
            .assertExists()
    }

    @Test
    fun decreaseTest() {
        composeTestRule.setContent {
            TestCartScreen()
        }

        composeTestRule
            .onNodeWithText(totalAmountText(2u))
            .assertExists()

        composeTestRule
            .onAllNodesWithContentDescription(decreaseDescription)
            .onFirst()
            .performClick()

        composeTestRule
            .onNodeWithText(totalAmountText(1u))
            .assertExists()
    }

    @Composable
    private fun TestCartScreen() {
        CartScreen(viewModel = CartViewModel(dataStore(), MockData.ExampleCart))
    }

    private val context: Context get() = ApplicationProvider.getApplicationContext()

    private fun dataStore() = DataStoreFactory.create(
        CartSerializer,
        produceFile = { File(context.filesDir, "cart-test-${UUID.randomUUID()}.json)") }
    )

    private val decreaseDescription
        get() = context.getString(R.string.description_decrease_amount)

    private val increaseDescription
        get() = context.getString(R.string.description_increase_amount)

    private fun totalAmountText(amount: UInt) = context.getString(R.string.total_amount, amount)
}
