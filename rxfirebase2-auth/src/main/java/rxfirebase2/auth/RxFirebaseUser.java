package com.androidhuman.rxfirebase2.auth;

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
        })
        .map(new Function<AuthResult, FirebaseUser>() {
            @Override
            public FirebaseUser apply(@NonNull AuthResult authResult) throws Exception {
                return authResult.getUser();
            }
        });
    }

    @CheckResult
    @NonNull
    public static Completable reauthenticate(
            @NonNull FirebaseUser user, @NonNull AuthCredential credential) {
        return Completable.create(new UserReauthenticateOnSubscribe(user, credential));
    }

    @CheckResult
    @NonNull
    public static Completable reload(@NonNull FirebaseUser user) {
        return Completable.create(new UserReloadOnSubscribe(user));
    }

    @CheckResult
    @NonNull
    public static Completable sendEmailVerification(@NonNull FirebaseUser user) {
        return Completable.create(new UserSendEmailVerificationOnSubscribe(user));
    }

    @CheckResult
    @NonNull
    public static Single<FirebaseUser> unlink(
            @NonNull FirebaseUser user, @NonNull String provider) {
        return Single.create(new UserUnlinkOnSubscribe(user, provider));
    }

    @CheckResult
    @NonNull
    public static Completable updateEmail(
            @NonNull FirebaseUser user, @NonNull String email) {
        return Completable.create(new UserUpdateEmailOnSubscribe(user, email));
    }

    @CheckResult
    @NonNull
    public static Completable updatePassword(
            @NonNull FirebaseUser user, @NonNull String password) {
        return Completable.create(new UserUpdatePasswordOnSubscribe(user, password));
    }

    @CheckResult
    @NonNull
    public static Completable updateProfile(
            @NonNull FirebaseUser user, @NonNull UserProfileChangeRequest request) {
        return Completable.create(new UserUpdateProfileOnSubscribe(user, request));
    }

    private RxFirebaseUser() {
        throw new AssertionError("No instances");
    }
}
