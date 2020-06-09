package android.ibanking.altyn.presentation.global.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirstapp.presentation.global.utils.CoroutineProvider
import com.example.myfirstapp.presentation.global.utils.EventWrapper
import com.example.myfirstapp.presentation.global.utils.Status
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import kz.jibergroup.studyinn.domain.RequestResult
import kz.jibergroup.studyinn.domain.UiCaller
import kz.jibergroup.studyinn.domain.UiCallerImpl
import org.koin.core.KoinComponent

abstract class BaseViewModel(
    private val scopeProvider: CoroutineProvider = CoroutineProvider(),
    private val coroutineJob: Job = Job(),
    protected val scope: CoroutineScope = CoroutineScope(coroutineJob + scopeProvider.IO),
    _statusLiveData: MutableLiveData<Status> = MutableLiveData(),
    _errorLiveData: MutableLiveData<EventWrapper<String>> = MutableLiveData()
) : ViewModel(), KoinComponent, UiCaller {
    private val disposable = CompositeDisposable()

    internal val uiCaller: UiCaller = UiCallerImpl(scope, scopeProvider, _statusLiveData, _errorLiveData)

    override val statusLiveData: LiveData<Status> = uiCaller.statusLiveData
    override val errorLiveData: LiveData<EventWrapper<String>> = uiCaller.errorLiveData

    override fun <T> makeRequest(
        call: suspend CoroutineScope.() -> T,
        resultBlock: ((T) -> Unit)?
    ) = uiCaller.makeRequest(call, resultBlock)

    override fun <T> unwrap(
        result: RequestResult<T>,
        errorBlock: ((String) -> Unit)?,
        successBlock: (T) -> Unit
    ) = uiCaller.unwrap(result, errorBlock, successBlock)

    override fun set(status: Status) = uiCaller.set(status)

    override fun setError(error: String?) {
        uiCaller.setError(error)
    }

    fun launch(job: () -> Disposable) {
        disposable.add(job())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
        coroutineJob.cancel()
    }

    fun io(work: suspend (() -> Unit)): Job =
        scope.launch(scopeProvider.IO) {
            work()
        }

    fun <T : Any> ioThenMain(work: suspend (() -> T?), callback: ((T?) -> Unit)): Job =
        scope.launch(scopeProvider.Main) {
            val data = scope.async(scopeProvider.IO) rt@{
                return@rt work()
            }.await()
            callback(data)
        }
}


inline fun <T>BaseViewModel.launchCoroutine(
    noinline call:  suspend () -> RequestResult<T>,
    crossinline block  :  (T) -> Unit
 ){
     makeRequest({
         call.invoke()
     }){
       unwrap(it){
           block(it)
       }
     }

 }