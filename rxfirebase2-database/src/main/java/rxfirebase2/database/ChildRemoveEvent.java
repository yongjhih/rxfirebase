package rxfirebase2.database;

import com.google.firebase.database.DataSnapshot;

import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;

public final class ChildRemoveEvent extends ChildEvent {

    @CheckReturnValue
    @NonNull
    public static ChildRemoveEvent create(DataSnapshot dataSnapshot) {
        return new ChildRemoveEvent(dataSnapshot);
    }

    private ChildRemoveEvent(DataSnapshot dataSnapshot) {
        super(dataSnapshot);
    }

    @Override
    public String toString() {
        return "ChildRemoveEvent{"
                + "dataSnapshot=" + dataSnapshot
                + '}';
    }
}
