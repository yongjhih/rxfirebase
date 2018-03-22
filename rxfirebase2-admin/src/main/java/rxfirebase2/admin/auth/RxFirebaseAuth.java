package rxfirebase2.admin.auth;

import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.annotations.CheckReturnValue;

import javax.annotation.Nonnull;

import io.reactivex.Single;


public final class RxFirebaseAuth {
    @Nonnull
    @CheckReturnValue
    public static Single<String> runTransaction(@Nonnull final FirebaseAuth firebaseAuth, @Nonnull final String uid) {
        return Single.fromFuture(firebaseAuth.createCustomTokenAsync(uid));
    }

    private RxFirebaseAuth() {
        throw new AssertionError("No instances");
    }
}
