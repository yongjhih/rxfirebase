@file:Suppress("NOTHING_TO_INLINE")

package rxfirebase2.config

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

inline fun FirebaseRemoteConfig.fetches()
        : Completable
        = RxFirebaseRemoteConfig.fetches(this)
