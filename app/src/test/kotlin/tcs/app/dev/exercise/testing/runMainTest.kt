package tcs.app.dev.exercise.testing

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
fun runUnconfinedMainTest(testBody: suspend TestScope.() -> Unit) = runTest {
    val dispatcher = UnconfinedTestDispatcher(testScheduler)
    Dispatchers.setMain(dispatcher)

    try {
        testBody()
    } finally {
        Dispatchers.resetMain()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun runStandardMainTest(testBody: suspend TestScope.() -> Unit) = runTest {
    val dispatcher = StandardTestDispatcher(testScheduler)
    Dispatchers.setMain(dispatcher)

    try {
        testBody()
    } finally {
        Dispatchers.resetMain()
    }
}
