package rxfirebase2.config;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.functions.Action;
import rxfirebase2.tasks.RxTask;

public final class RxFirebaseRemoteConfig {
    @CheckResult
    @NonNull
    public static Completable fetches(@NonNull final FirebaseRemoteConfig config) {
        return RxTask.completes(
                config.fetch((config.getInfo().getConfigSettings().isDeveloperModeEnabled())
                        ? 0L : 3600L)).doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                config.activateFetched();
            }
        });
    }
}
