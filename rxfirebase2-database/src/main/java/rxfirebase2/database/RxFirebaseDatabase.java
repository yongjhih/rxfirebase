package rxfirebase2.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;

import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import rxfirebase2.database.model.DataValue;
import rxfirebase2.database.transformers.SingleTransformerOfClazz;
import rxfirebase2.database.transformers.SingleTransformerOfGenericTypeIndicator;
import rxfirebase2.database.transformers.TransformerOfClazz;
import rxfirebase2.database.transformers.TransformerOfGenericTypeIndicator;

import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import rxtasks2.RxTask;


public final class RxFirebaseDatabase {

    /**
     * @param query
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static Observable<ChildEvent> childEvents(@NonNull final Query query) {
        return Observable.create(new QueryChildEventsOnSubscribe(query));
    }

    /**
     * @param query
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static Single<DataSnapshot> data(@NonNull final Query query) {
        return RxValue.single(query);
    }

    /**
     * @param query
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static Observable<DataSnapshot> dataChanges(@NonNull final Query query) {
        return RxValue.changes(query);
    }

    /**
     * @param ref
     * @param clazz
     * @param <T>
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static <T> Observable<DataValue<T>> dataChangesOf(
            @NonNull final DatabaseReference ref, @NonNull final Class<T> clazz) {
        return dataChanges(ref).compose(new TransformerOfClazz<>(clazz));
    }

    /**
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static <T> Observable<DataValue<T>> dataChangesOf(
            @NonNull final Query query, @NonNull final Class<T> clazz) {
        return dataChanges(query).compose(new TransformerOfClazz<>(clazz));
    }

    /**
     * @param ref
     * @param typeIndicator
     * @param <T>
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static <T> Observable<DataValue<T>> dataChangesOf(
            @NonNull DatabaseReference ref, @NonNull GenericTypeIndicator<T> typeIndicator) {
        return dataChanges(ref)
                .compose(new TransformerOfGenericTypeIndicator<T>(typeIndicator));
    }

    /**
     * @param query
     * @param typeIndicator
     * @param <T>
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static <T> Observable<DataValue<T>> dataChangesOf(
            @NonNull Query query, @NonNull GenericTypeIndicator<T> typeIndicator) {
        return dataChanges(query)
                .compose(new TransformerOfGenericTypeIndicator<T>(typeIndicator));
    }

    /**
     * @param ref
     * @param clazz
     * @param <T>
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static <T> Single<T> dataOf(
            @NonNull DatabaseReference ref, @NonNull Class<T> clazz) {
        return data(ref).compose(new SingleTransformerOfClazz<>(clazz));
    }

    /**
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static <T> Single<T> dataOf(
            @NonNull Query query, @NonNull Class<T> clazz) {
        return data(query).compose(new SingleTransformerOfClazz<T>(clazz));
    }

    /**
     * @param ref
     * @param typeIndicator
     * @param <T>
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static <T> Single<T> dataOf(
            @NonNull DatabaseReference ref, @NonNull GenericTypeIndicator<T> typeIndicator) {
        return data(ref).compose(new SingleTransformerOfGenericTypeIndicator<T>(typeIndicator));
    }

    /**
     * @param query
     * @param typeIndicator
     * @param <T>
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static <T> Single<T> dataOf(
            @NonNull Query query, @NonNull GenericTypeIndicator<T> typeIndicator) {
        return data(query).compose(new SingleTransformerOfGenericTypeIndicator<T>(typeIndicator));
    }

    /**
     * @param ref
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static Completable removeValue(@NonNull final DatabaseReference ref) {
        return RxTask.completes(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return ref.removeValue();
            }
        });
    }

    /**
     * @param ref
     * @param priority
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static Completable setPriority(
            @NonNull final DatabaseReference ref, @NonNull final Object priority) {
        return RxTask.completes(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return ref.setPriority(priority);
            }
        });
    }

    /**
     * @param ref
     * @param value
     * @param <T>
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static <T> Completable setValue(
            @NonNull final DatabaseReference ref,
            @NonNull final T value) {
        return RxTask.completes(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return ref.setValue(value);
            }
        });
    }

    /**
     * @param ref
     * @param value
     * @param priority
     * @param <T>
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static <T> Completable setValue(
            @NonNull final DatabaseReference ref,
            @NonNull final T value,
            @NonNull final Object priority) {
        return RxTask.completes(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return ref.setValue(value, priority);
            }
        });
    }

    /**
     * @param ref
     * @param function
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static Single<DataSnapshot> runTransaction(
            @NonNull final DatabaseReference ref,
            @NonNull final Function<MutableData, Transaction.Result> function) {
        return RxValue.transaction(ref, function);
    }

    /**
     * @param ref
     * @param fireLocalEvents
     * @param function
     * @return
     * ref.runTransaction(handler, fireLocalEvents);
     */
    @NonNull
    @CheckReturnValue
    public static Single<DataSnapshot> runTransaction(
            @NonNull final DatabaseReference ref,
            @NonNull final Function<MutableData, Transaction.Result> function,
            final boolean fireLocalEvents) {
        return RxValue.transaction(ref, function, fireLocalEvents);
    }

    /**
     * @param ref
     * @param map
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static Completable updateChildren(
            @NonNull final DatabaseReference ref, @NonNull final Map<String, Object> map) {
        return RxTask.completes(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return ref.updateChildren(map);
            }
        });
    }

    private RxFirebaseDatabase() {
        throw new AssertionError("No instances");
    }
}
