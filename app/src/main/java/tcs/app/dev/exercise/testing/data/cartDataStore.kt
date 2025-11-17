package tcs.app.dev.exercise.testing.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import tcs.app.dev.util.JsonDataStoreSerializer

val CartSerializer = JsonDataStoreSerializer(
    kSerializer = Cart.serializer(),
    defaultValue = MockData.ExampleCart
)

val Context.cartDataStore: DataStore<Cart> by dataStore(
    fileName = "cart-testing.json",
    serializer = CartSerializer,
    corruptionHandler = ReplaceFileCorruptionHandler { MockData.ExampleCart }
)
