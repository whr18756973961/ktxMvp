package com.xiaozhoudao.loanassistant.api

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.whr.baseui.mvp.BaseMvpPresenter
import com.whr.baseui.mvp.BaseMvpView
import kotlinx.coroutines.*

/**
 * 用于配置底层协程操作的presenter、
 *
 *
 */
abstract class KotlinPresenter<V : BaseMvpView> : BaseMvpPresenter<V>() {
    val presenterScope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.Main + Job())
    }

    @ExperimentalCoroutinesApi
    override fun detachView() {
        presenterScope.cancel()
    }

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        presenterScope.launch { block() }
    }

    suspend fun <T> launchIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
        }
    }

    fun launch(tryBlock: suspend CoroutineScope.() -> Unit) {
        launchOnUI {
            tryCatch(tryBlock, {}, {})
        }
    }

    fun launchOnUITryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(String?) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit
    ) {
        launchOnUI {
            tryCatch(tryBlock, catchBlock, finallyBlock)
        }
    }

    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(String?) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Throwable) {
                var errMsg = e.message
                catchBlock(errMsg)
            } finally {
                finallyBlock()
            }
        }
    }
}