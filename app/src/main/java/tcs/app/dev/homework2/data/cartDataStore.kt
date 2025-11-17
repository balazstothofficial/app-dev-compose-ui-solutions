package tcs.app.dev.homework2.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import tcs.app.dev.util.JsonDataStoreSerializer

val Context.cartDataStore: DataStore<Cart> by dataStore(
    fileName = "cart.json",
    serializer = JsonDataStoreSerializer(
        kSerializer = Cart.serializer(),
        defaultValue = Cart.Empty
    ),
    corruptionHandler = ReplaceFileCorruptionHandler { Cart.Empty }
)
