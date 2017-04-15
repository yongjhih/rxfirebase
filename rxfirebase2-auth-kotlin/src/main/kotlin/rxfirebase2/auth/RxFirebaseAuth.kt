@file:Suppress("NOTHING_TO_INLINE")

package rxfirebase2.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

inline fun FirebaseAuth.changes()
        : Observable<FirebaseAuth>
        = RxFirebaseAuth.changes(this)

inline fun FirebaseAuth.createsUserWithEmailAndPassword(email: String, password: String)
        : Single<FirebaseUser>
        = RxFirebaseAuth.createUserWithEmailAndPassword(this, email, password)

inline fun FirebaseAuth.fetchesProvidersForEmail(email: String)
        : Single<List<String>>
        = RxFirebaseAuth.fetchProvidersForEmail(this, email)

inline fun FirebaseAuth.getsCurrentUser()
        : Maybe<FirebaseUser>
        = RxFirebaseAuth.getCurrentUser(this)

inline fun FirebaseAuth.sendsPasswordResetEmail(email: String)
        : Completable
        = RxFirebaseAuth.sendPasswordResetEmail(this, email)

inline fun FirebaseAuth.signsInAnonymous()
        : Single<FirebaseUser>
        = RxFirebaseAuth.signInAnonymous(this)

inline fun FirebaseAuth.signsInWithCredential(credential: AuthCredential)
        : Single<FirebaseUser>
        = RxFirebaseAuth.signInWithCredential(this, credential)

inline fun FirebaseAuth.signsInWithCustomToken(token: String)
        : Single<FirebaseUser>
        = RxFirebaseAuth.signInWithCustomToken(this, token)

inline fun FirebaseAuth.signsInWithEmailAndPassword(email: String, password: String)
        : Single<FirebaseUser>
        = RxFirebaseAuth.signInWithEmailAndPassword(this, email, password)

inline fun FirebaseAuth.signsOut()
        : Completable
        = RxFirebaseAuth.signOut(this)
