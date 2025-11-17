package tcs.app.dev.exercise.testing

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.updateAndGet

/**
 * # Mock DataStore implementation for testing purposes.
 *
 * ## Resources
 * - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
 */
class MockDataStore<T>(initialValue: T) : DataStore<T> {
    private val mutableData = MutableStateFlow(initialValue)

    override val data: Flow<T> = mutableData

    override suspend fun updateData(transform: suspend (T) -> T): T {
        return mutableData.updateAndGet { value -> transform(value) }
    }

    fun exercise() {

    }
}
