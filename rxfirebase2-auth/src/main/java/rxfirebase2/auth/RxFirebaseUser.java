package rxfirebase2.auth;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserProfileChangeRequest;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import rxfirebase2.tasks.RxTask;


public final class RxFirebaseUser {

    @CheckResult
    @NonNull
    public static Completable delete(@NonNull final FirebaseUser user) {
        return RxTask.completes(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return user.delete();
            }
        });
    }

    @CheckResult
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

    @CheckResult
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

    @CheckResult
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

    @CheckResult
    @NonNull
    public static Completable reload(@NonNull final FirebaseUser user) {
        return RxTask.completes(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return user.reload();
            }
        });
    }

    @CheckResult
    @NonNull
    public static Completable sendEmailVerification(@NonNull final FirebaseUser user) {
        return RxTask.completes(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return user.sendEmailVerification();
            }
        });
    }

    @CheckResult
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

    @CheckResult
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

    @CheckResult
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

    @CheckResult
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
