package tcs.app.dev.homework2.data

import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tcs.app.dev.util.ImageDownloader
import kotlin.Result.Companion.success

suspend fun getAvailableItems(): Result<Map<Item, Euro>> = withContext(Dispatchers.IO) {
    success(
        mapOf(
            Item("apple", "Apple") to 60u.cents,
            Item("banana", "Banana") to 90u.cents,
            Item("golden_fig", "Golden Fig") to 16u.euro + 80u.cents,
            Item("bread", "Bread") to 1u.euro + 90u.cents,
            Item("chair", "Chair") to 49u.euro + 99u.cents,
            Item("computer", "Computer") to 1499u.euro + 99u.cents,
            Item("knives", "Knives") to 40u.euro,
            Item("onion", "Onion") to 40u.cents,
            Item("scissor", "Scissor") to 3u.euro + 50u.cents,
            Item("spaghetti", "Spaghetti") to 1u.euro,
            Item("table", "Table") to 400u.euro,
            Item("tomato", "Tomato") to 50u.cents
        )
    )
}

suspend fun getAvailableDiscounts(): Result<List<Discount>> = withContext(Dispatchers.IO) {
    success(
        listOf(
            Discount.Fixed(3u.euro),
            Discount.Percentage(10u),
            Discount.Bundle(Item("apple", "Apple"), 5u, 3u),
            Discount.Bundle(Item("banana", "Banana"), 3u, 2u)
        )
    )
}

suspend fun getImage(item: Item): Result<Bitmap> = withContext(Dispatchers.IO) {
    ImageDownloader.TCSAppDevImages.getImage(
        id = item.id,
        targetHeight = 64,
        targetWidth = 64
    )
}
