package rxfirebase2.config;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import io.reactivex.Completable;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import rxtasks2.RxTask;

public final class RxFirebaseRemoteConfig {
    /**
     * @param config
     * @return
     */
    @CheckReturnValue
    @NonNull
    public static Completable fetches(@NonNull final FirebaseRemoteConfig config) {
        return RxTask.completes(config.fetch()).doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                config.activateFetched();
            }
        });
    }

    @CheckReturnValue
    @NonNull
    public static Completable fetches(@NonNull final FirebaseRemoteConfig config,
                                      final long timeout) {
        return RxTask.completes(config.fetch(timeout)).doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                config.activateFetched();
            }
        });
    }

    @CheckReturnValue
    @NonNull
    public static Completable fetchesDev(@NonNull final FirebaseRemoteConfig config) {
        return fetches(config, (config.getInfo().getConfigSettings().isDeveloperModeEnabled())
                ? 0L : 3600L);
    }
}
