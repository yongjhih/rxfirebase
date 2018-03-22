package rxfirebase2.admin.cloud;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Transaction;

import io.reactivex.annotations.CheckReturnValue;

import java.util.List;

import javax.annotation.Nonnull;

import io.reactivex.Single;


public final class RxFirestore {
    @Nonnull
    @CheckReturnValue
    public static <T> Single<T> runTransaction(@Nonnull final Firestore firestore, @Nonnull final Transaction.Function<T> updateFunction) {
        return Single.fromFuture(firestore.runTransaction(updateFunction));
    }

    @Nonnull
    @CheckReturnValue
    public static Single<List<DocumentSnapshot>> getAll(@Nonnull final Firestore firestore, final DocumentReference... documentReferences) {
        return Single.fromFuture(firestore.getAll(documentReferences));
    }

    private RxFirestore() {
        throw new AssertionError("No instances");
    }
}
