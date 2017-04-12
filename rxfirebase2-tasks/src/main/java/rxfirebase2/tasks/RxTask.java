package rxfirebase2.tasks;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public final class RxTask {
    /**
     * @param callable
     * @param <R>
     * @return
     */
    @CheckResult
    @NonNull
    public static <R> Single<R> single(@NonNull final Callable<Task<R>> callable) {
        return Single.fromCallable(callable).flatMap(new Function<Task<R>,
                SingleSource<? extends R>>() {
            @Override
            public SingleSource<? extends R> apply(Task<R> task) throws Exception {
                return single(task);
            }
        });
    }

    /**
     * @param task
     * @param <R>
     * @return
     */
    @CheckResult
    @NonNull
    public static <R> Single<R> single(@NonNull final Task<R> task) {
        return Single.create(new SingleOnSubscribe<R>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<R> emit) throws Exception {
                task.addOnCompleteListener(new OnCompleteListener<R>() {
                    @Override
                    public void onComplete(@NonNull final Task<R> task) {
                        // TODO isDisposed
                        if (task.isSuccessful()) {
                            emit.onSuccess(task.getResult());
                        } else {
                            Exception e = task.getException();
                            emit.onError(e != null ? e : new RuntimeException());
                        }
                    }
                });
            }
        });
    }

    @CheckResult
    @NonNull
    public static <R> Completable completes(@NonNull final Callable<Task<R>> callable) {
        return Single.fromCallable(callable).flatMapCompletable(
                new Function<Task<R>, Completable>() {
            @Override
            public Completable apply(Task<R> task) throws Exception {
                return completes(task);
            }
        });
    }

    /**
     * @param task
     * @param <R> Usually &lt;Void&gt;
     * @return
     */
    @CheckResult
    @NonNull
    public static <R> Completable completes(@NonNull final Task<R> task) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(@NonNull final CompletableEmitter emit) throws Exception {
                task.addOnCompleteListener(new OnCompleteListener<R>() {
                    @Override
                    public void onComplete(@NonNull final Task<R> task) {
                        // TODO isDisposed
                        if (task.isSuccessful()) {
                            emit.onComplete();
                        } else {
                            Exception e = task.getException();
                            emit.onError(e != null ? e : new RuntimeException());
                        }
                    }
                });
            }
        });
    }

    /**
     * @param callable
     * @param <R>
     * @return
     */
    @CheckResult
    @NonNull
    public static <R> Maybe<R> maybe(@NonNull final Callable<Task<R>> callable) {
        return Single.fromCallable(callable).flatMapMaybe(
                new Function<Task<R>, MaybeSource<? extends R>>() {
            @Override
            public MaybeSource<? extends R> apply(Task<R> task) throws Exception {
                return maybe(task);
            }
        });
    }

    /**
     * @param task
     * @param <R>
     * @return
     */
    @CheckResult
    @NonNull
    public static <R> Maybe<R> maybe(@NonNull final Task<R> task) {
        return Maybe.create(new MaybeOnSubscribe<R>() {
            @Override
            public void subscribe(@NonNull final MaybeEmitter<R> emit) throws Exception {
                task.addOnCompleteListener(new OnCompleteListener<R>() {
                    @Override
                    public void onComplete(@NonNull final Task<R> task) {
                        // TODO isDisposed
                        if (task.isSuccessful()) {
                            R result = task.getResult();
                            if (result != null) {
                                emit.onSuccess(result);
                            }
                            emit.onComplete();
                        } else {
                            Exception e = task.getException();
                            emit.onError(e != null ? e : new RuntimeException());
                        }
                    }
                });
            }
        });
    }

    ///**
    // * @param callable
    // * @param <R>
    // * @return
    // */
    //@CheckResult
    //@NonNull
    //public static <R> Observable<R> observe(
    // @NonNull final Callable<Task<R>> callable, @NonNull final Action onDispose) {
    //    return Single.fromCallable(callable).flatMapObservable(
    // new Function<Task<R>, ObservableSource<? extends R>>() {
    //        @Override
    //        public ObservableSource<? extends R> apply(Task<R> task) throws Exception {
    //            return observe(task, onDispose);
    //        }
    //    });
    //}

    ///**
    // * @param task
    // * @param <R>
    // * @return
    // */
    //@CheckResult
    //@NonNull
    //public static <R> Observable<R> observe(
    // @NonNull final Task<R> task, @NonNull final Action onDispose) {
    //    return Observable.create(new ObservableOnSubscribe<R>() {
    //        @Override
    //        public void subscribe(@NonNull final ObservableEmitter<R> emit) throws Exception {
    //            emit.setDisposable(Disposables.fromAction(onDispose));
    //            task.addOnCompleteListener(new OnCompleteListener<R>() {
    //                @Override
    //                public void onComplete(@NonNull final Task<R> task) {
    //                    // TODO isDisposed
    //                    if (task.isSuccessful()) {
    //                        R result = task.getResult();
    //                        if (result != null) emit.onNext(result);
    //                    } else {
    //                        Exception e = task.getException();
    //                        emit.onError(e != null ? e : new RuntimeException());
    //                    }
    //                }
    //            });
    //        }
    //    });
    //}
}
