package rxfirebase2.tasks;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public final class RxTask {
    /**
     * @param task
     * @param <R>
     * @return
     */
    @CheckResult
    @NonNull
    public static <R> Single<R> single(@NonNull final Task<R> task) {
        return Single.create(new SingleOnSubscribe() {
            @Override
            public void subscribe(@NonNull final SingleEmitter emit) throws Exception {
                task.addOnCompleteListener(new OnCompleteListener<R>() {
                    @Override
                    public void onComplete(@NonNull final Task<R> task) {
                        if (task.isSuccessful()) {
                            emit.onSuccess(task.getResult());
                        } else {
                            emit.onError(task.getException());
                        }
                    }
                });
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
                        if (task.isSuccessful()) {
                            emit.onComplete();
                        } else {
                            emit.onError(task.getException());
                        }
                    }
                });
            }
        });
    }
}
