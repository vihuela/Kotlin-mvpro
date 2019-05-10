package com.ricky.mvp_core.base

import androidx.lifecycle.ViewModel
import com.trello.rxlifecycle3.LifecycleProvider
import com.trello.rxlifecycle3.LifecycleTransformer
import com.trello.rxlifecycle3.RxLifecycle
import com.trello.rxlifecycle3.android.FragmentEvent
import com.trello.rxlifecycle3.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

open class RxViewModel : ViewModel(), LifecycleProvider<FragmentEvent> {
    private val lifecycleSubject = BehaviorSubject.create<FragmentEvent>()
    override fun onCleared() {
        super.onCleared()
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
    }

    override fun lifecycle(): Observable<FragmentEvent> =
            lifecycleSubject.hide()

    override fun <T : Any?> bindUntilEvent(event: FragmentEvent): LifecycleTransformer<T> =
            RxLifecycle.bindUntilEvent(lifecycleSubject, event)

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> =
            RxLifecycleAndroid.bindFragment(lifecycleSubject)
}