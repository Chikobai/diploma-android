package android.ibanking.altyn.presentation.global.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseInteractor : CoroutineScope {

    private val coroutineJob = Job()

    override val coroutineContext: CoroutineContext
        get() = coroutineJob + Dispatchers.IO

    val scope = CoroutineScope(coroutineContext)
}