@file:Suppress("NOTHING_TO_INLINE")

package rxfirebase2.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import io.reactivex.Completable
import io.reactivex.Single

inline fun FirebaseUser.deletes()
        : Completable
        = RxFirebaseUser.delete(this)

inline fun FirebaseUser.getsToken(forceRefresh: Boolean)
        : Single<String>
        = RxFirebaseUser.getToken(this, forceRefresh)

inline fun FirebaseUser.linksWithCredential(credential: AuthCredential)
        : Single<FirebaseUser>
        = RxFirebaseUser.linkWithCredential(this, credential)

inline fun FirebaseUser.reauthenticates(credential: AuthCredential)
        : Completable
        = RxFirebaseUser.reauthenticate(this, credential)

inline fun FirebaseUser.reloads()
        : Completable
        = RxFirebaseUser.reload(this)

inline fun FirebaseUser.sendsEmailVerification()
        : Completable
        = RxFirebaseUser.sendEmailVerification(this)

inline fun FirebaseUser.unlinks(provider: String)
        : Single<FirebaseUser>
        = RxFirebaseUser.unlink(this, provider)

inline fun FirebaseUser.updatesEmail(email: String)
        : Completable
        = RxFirebaseUser.updateEmail(this, email)

inline fun FirebaseUser.updatesPassword(password: String)
        : Completable
        = RxFirebaseUser.updatePassword(this, password)

inline fun FirebaseUser.updatesProfile(request: UserProfileChangeRequest)
        : Completable
        = RxFirebaseUser.updateProfile(this, request)

