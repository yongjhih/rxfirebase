package rxfirebase2.database.model;

import java.io.Serializable;

import io.reactivex.annotations.NonNull;

public abstract class DataValue<T> implements Serializable {

    private static Empty<?> empty;

    public abstract T get();

    public abstract boolean isPresent();

    @NonNull
    public static <T> DataValue<T> of(T value) {
        if (null != value) {
            return new Some<>(value);
        } else {
            return empty();
        }
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public static <T> DataValue<T> empty() {
        if (null == empty) {
            empty = new Empty<>();
        }
        return (DataValue<T>) empty;
    }
}
