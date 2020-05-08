package me.ibrahimsn.core.domain.interactor

import androidx.paging.DataSource
import kotlinx.coroutines.channels.Channel
import me.ibrahimsn.core.data.model.DataHolder

interface Interactor {

    interface RequestInteractor<P: Params, T: Any?> : Interactor {
        suspend fun invoke(params: P?): DataHolder<T>
    }

    interface RetrieveInteractor<T: Any?> : Interactor {
        suspend fun invoke(): DataHolder<T?>
    }

    interface ObserveInteractor<P: Params, T: Any?>: Interactor {
        suspend fun invoke(channel: Channel<T>, params: P?)
    }

    interface PagingInteractor<K: Any?, V:Any?>: Interactor {
        fun invoke(): DataSource.Factory<K, V>
    }

    abstract class Params {
        // marker class
    }
}
