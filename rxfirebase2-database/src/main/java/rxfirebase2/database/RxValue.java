package rxfirebase2.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Function;

public final class RxValue {
    /**
     * @param query
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static Single<DataSnapshot> single(@NonNull final Query query) {
        return Single.create(new SingleOnSubscribe<DataSnapshot>() {
            @Override
            public void subscribe(
                    @NonNull final SingleEmitter<DataSnapshot> emit) throws Exception {
                final ValueEventListener listener = listener(emit);

                emit.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        query.removeEventListener(listener);
                    }
                });

                query.addListenerForSingleValueEvent(listener);
            }
        });
    }

    /**
     * @param query
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static Observable<DataSnapshot> changes(@NonNull final Query query) {
        return Observable.create(new ObservableOnSubscribe<DataSnapshot>() {
            @Override
            public void subscribe(
                    @NonNull final ObservableEmitter<DataSnapshot> emit) throws Exception {
                final ValueEventListener listener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!emit.isDisposed()) {
                            emit.onNext(dataSnapshot);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError e) {
                        if (!emit.isDisposed()) {
                            emit.onError(e.toException());
                        }
                    }
                };

                emit.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        query.removeEventListener(listener);
                    }
                });

                query.addValueEventListener(listener);
            }
        });
    }

    /**
     * @param emit
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static ValueEventListener listener(@NonNull final SingleEmitter<DataSnapshot> emit) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!emit.isDisposed()) {
                    emit.onSuccess(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError e) {
                if (!emit.isDisposed()) {
                    emit.onError(e.toException());
                }
            }
        };
    }

    /**
     * @param ref
     * @param function
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static Single<DataSnapshot> transaction(
            @NonNull final DatabaseReference ref,
            @NonNull final Function<MutableData, Transaction.Result> function) {
        return Single.create(new SingleOnSubscribe<DataSnapshot>() {
            @Override
            public void subscribe(
                    @NonNull final SingleEmitter<DataSnapshot> emit) throws Exception {
                ref.runTransaction(transaction(emit, function));
            }
        });
    }

    /**
     * @param ref
     * @param function
     * @param fireLocalEvents
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static Single<DataSnapshot> transaction(
            @NonNull final DatabaseReference ref,
            @NonNull final Function<MutableData, Transaction.Result> function,
            final boolean fireLocalEvents) {
        return Single.create(new SingleOnSubscribe<DataSnapshot>() {
            @Override
            public void subscribe(
                    @NonNull final SingleEmitter<DataSnapshot> emit) throws Exception {
                ref.runTransaction(transaction(emit, function), fireLocalEvents);
            }
        });
    }

    //public interface TransactionFunction extends Function<MutableData, Transaction.Result> {
    //}

    /**
     * @param emitter
     * @param function
     * @return
     */
    @NonNull
    @CheckReturnValue
    public static Transaction.Handler transaction(
            @NonNull final SingleEmitter<DataSnapshot> emitter,
            @NonNull final Function<MutableData, Transaction.Result> function) {
        return new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                try {
                    return function.apply(mutableData);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError,
                                   boolean committed,
                                   @NonNull DataSnapshot dataSnapshot) {
                if (!emitter.isDisposed()) {
                    if (null == databaseError) {
                        emitter.onSuccess(dataSnapshot);
                    } else {
                        emitter.onError(databaseError.toException());
                    }
                }
            }
        };
    }
}
