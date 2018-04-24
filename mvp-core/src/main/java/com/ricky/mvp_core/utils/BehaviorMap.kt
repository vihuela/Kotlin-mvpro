package com.ricky.mvp_core.utils

import io.reactivex.processors.BehaviorProcessor

class BehaviorMap : HashMap<String, BehaviorProcessor<Boolean>>() {
    override fun get(key: String): BehaviorProcessor<Boolean> {
        var processor = super.get(key)
        processor?.onNext(false)
        processor = BehaviorProcessor.create()
        put(key, processor)
        return processor!!
    }

}