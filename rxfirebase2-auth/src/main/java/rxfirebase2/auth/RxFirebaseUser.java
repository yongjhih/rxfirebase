package rxfirebase2.auth;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import rxtasks2.RxTask;


public final class RxFirebaseUser {

    /**
     * @param user
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Completable delete(@NonNull final FirebaseUser user) {
        return RxTask.completes(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return user.delete();
            }
        });
    }

    /**
     * @param user
     * @param forceRefresh
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Single<String> getToken(@NonNull final FirebaseUser user,
                                          final boolean forceRefresh) {
        return RxTask.single(new Callable<Task<GetTokenResult>>() {
            @Override
            public Task<GetTokenResult> call() throws Exception {
                return user.getToken(forceRefresh);
            }
        })
        .map(new Function<GetTokenResult, String>() {
            @Override
            public String apply(@NonNull GetTokenResult getTokenResult) throws Exception {
                return getTokenResult.getToken();
            }
        });
    }

    /**
     * @param user
     * @param credential
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Single<FirebaseUser> linkWithCredential(
            @NonNull final FirebaseUser user, @NonNull final AuthCredential credential) {
        return RxTask.single(new Callable<Task<AuthResult>>() {
            @Override
            public Task<AuthResult> call() throws Exception {
                return user.linkWithCredential(credential);
            }
        }).map(RxFirebaseAuth.authToUserFunction());
    }

    /**
     * @param user
     * @param credential
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Completable reauthenticate(
            @NonNull final FirebaseUser user, @NonNull final AuthCredential credential) {
        return RxTask.completes(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return user.reauthenticate(credential);
            }
        });
    }

    /**
     * @param user
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Completable reload(@NonNull final FirebaseUser user) {
        return RxTask.completes(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return user.reload();
            }
        });
    }

    /**
     * @param user
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Completable sendEmailVerification(@NonNull final FirebaseUser user) {
        return RxTask.completes(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return user.sendEmailVerification();
            }
        });
    }

    /**
     * @param user
     * @param provider
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Single<FirebaseUser> unlink(
            @NonNull final FirebaseUser user, @NonNull final String provider) {
        return RxTask.single(new Callable<Task<AuthResult>>() {
            @Override
            public Task<AuthResult> call() throws Exception {
                return user.unlink(provider);
            }
        }).map(RxFirebaseAuth.authToUserFunction());
    }

    /**
     * @param user
     * @param email
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Completable updateEmail(
            @NonNull final FirebaseUser user, @NonNull final String email) {
        return RxTask.completes(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return user.updateEmail(email);
            }
        });
    }

    /**
     * @param user
     * @param password
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Completable updatePassword(
            @NonNull final FirebaseUser user, @NonNull final String password) {
        return RxTask.completes(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return user.updatePassword(password);
            }
        });
    }

    /**
     * @param user
     * @param request
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Completable updateProfile(
            @NonNull final FirebaseUser user, @NonNull final UserProfileChangeRequest request) {
        return RxTask.completes(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return user.updateProfile(request);
            }
        });
    }

    private RxFirebaseUser() {
        throw new AssertionError("No instances");
    }
}
