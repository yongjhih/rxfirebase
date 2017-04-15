package rxfirebase2.auth;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import rxtasks2.RxTask;


public final class RxFirebaseAuth {

    /**
     * @param instance
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Observable<FirebaseAuth> changes(@NonNull FirebaseAuth instance) {
        return Observable.create(new AuthStateChangesOnSubscribe(instance));
    }

    /**
     * @param instance
     * @param email
     * @param password
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Single<FirebaseUser> createUserWithEmailAndPassword(
            @NonNull final FirebaseAuth instance,
            @NonNull final String email, @NonNull final String password) {
        return RxTask.single(new Callable<Task<AuthResult>>() {
            @Override
            public Task<AuthResult> call() throws Exception {
                return instance.createUserWithEmailAndPassword(email, password);
            }
        }).map(authToUserFunction());
    }

    /**
     * TODO: Should use Maybe instead of Single
     * TODO: flatten List
     *
     * @param instance
     * @param email
     * @return &lt;emptyList&gt; if providers is null
     */
    @CheckReturnValue
    @NonNull
    public static Single<List<String>> fetchProvidersForEmail(
            @NonNull final FirebaseAuth instance, @NonNull final String email) {
        return RxTask.single(new Callable<Task<ProviderQueryResult>>() {
            @Override
            public Task<ProviderQueryResult> call() throws Exception {
                return instance.fetchProvidersForEmail(email);
            }
        }).map(new Function<ProviderQueryResult, List<String>>() {
            @Override
            public List<String> apply(@NonNull ProviderQueryResult providerQueryResult)
                    throws Exception {
                List<String> providers = providerQueryResult.getProviders();
                if (null == providers) {
                    providers = Collections.emptyList();
                }
                return providers;
            }
        });
    }

    /**
     * @param instance
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Maybe<FirebaseUser> getCurrentUser(
            @NonNull final FirebaseAuth instance) {
        return Maybe.create(new GetCurrentUserOnSubscribe(instance));
    }

    /**
     * @param instance
     * @param email
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Completable sendPasswordResetEmail(
            @NonNull final FirebaseAuth instance, @NonNull final String email) {
        return RxTask.completes(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return instance.sendPasswordResetEmail(email);
            }
        });
    }

    /**
     * @param instance
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Single<FirebaseUser> signInAnonymous(
            @NonNull final FirebaseAuth instance) {
        return RxTask.single(new Callable<Task<AuthResult>>() {
            @Override
            public Task<AuthResult> call() throws Exception {
                return instance.signInAnonymously();
            }
        }).map(authToUserFunction());
    }

    /**
     * TODO: AuthToUserFunction class
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Function<AuthResult, FirebaseUser> authToUserFunction() {
        return new Function<AuthResult, FirebaseUser>() {
            @Override
            public FirebaseUser apply(@NonNull AuthResult authResult) throws Exception {
                return authResult.getUser();
            }
        };
    }

    /**
     * @param instance
     * @param credential
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Single<FirebaseUser> signInWithCredential(
            @NonNull final FirebaseAuth instance, @NonNull final AuthCredential credential) {
        return RxTask.single(new Callable<Task<AuthResult>>() {
            @Override
            public Task<AuthResult> call() throws Exception {
                return instance.signInWithCredential(credential);
            }
        }).map(authToUserFunction());
    }

    /**
     * @param instance
     * @param token
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Single<FirebaseUser> signInWithCustomToken(
            @NonNull final FirebaseAuth instance, @NonNull final String token) {
        return RxTask.single(new Callable<Task<AuthResult>>() {
            @Override
            public Task<AuthResult> call() throws Exception {
                return instance.signInWithCustomToken(token);
            }
        }).map(authToUserFunction());
    }

    /**
     * @param instance
     * @param email
     * @param password
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Single<FirebaseUser> signInWithEmailAndPassword(
            @NonNull final FirebaseAuth instance,
            @NonNull final String email, @NonNull final String password) {
        return RxTask.single(new Callable<Task<AuthResult>>() {
            @Override
            public Task<AuthResult> call() throws Exception {
                return instance.signInWithEmailAndPassword(email, password);
            }
        }).map(authToUserFunction());
    }

    /**
     * @param instance
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Completable signOut(@NonNull final FirebaseAuth instance) {
        return Completable.create(new SignOutOnSubscribe(instance));
    }

    private RxFirebaseAuth() {
        throw new AssertionError("No instances");
    }
}
