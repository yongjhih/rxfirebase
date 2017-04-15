package rxfirebase2.storage;

import android.net.Uri;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;
import io.reactivex.Completable;
import io.reactivex.Single;
import rxtasks2.RxTask;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.Callable;

public final class RxFirebaseStorage {

    private RxFirebaseStorage() {
        throw new AssertionError("No instances");
    }

    /**
     * @see StorageReference#getBytes(long)
     */
    @CheckResult
    @NonNull
    public static Single<byte[]> getBytes(@NonNull final StorageReference ref,
                                          final long maxDownloadSizeBytes) {
        return RxTask.single(new Callable<Task<byte[]>>() {
            @Override
            public Task<byte[]> call() throws Exception {
                return ref.getBytes(maxDownloadSizeBytes);
            }
        });
    }

    /**
     * @see StorageReference#delete()
     */
    @CheckResult
    @NonNull
    public static Completable delete(@NonNull final StorageReference ref) {
        return RxTask.completes(new Callable<Task<Void>>() {
            @Override
            public Task<Void> call() throws Exception {
                return ref.delete();
            }
        });
    }

    /**
     * @see StorageReference#getFile(Uri)
     */
    @CheckResult
    @NonNull
    public static Single<FileDownloadTask.TaskSnapshot> getFile(@NonNull final StorageReference ref,
                                                                @NonNull final Uri uri) {
        return RxTask.single(new Callable<Task<FileDownloadTask.TaskSnapshot>>() {
            @Override
            public Task<FileDownloadTask.TaskSnapshot> call() throws Exception {
                return ref.getFile(uri);
            }
        });
    }

    /**
     * @see StorageReference#getFile(File)
     */
    @CheckResult
    @NonNull
    public static Single<FileDownloadTask.TaskSnapshot> getFile(@NonNull final StorageReference ref,
                                                                @NonNull final File file) {
        return RxTask.single(new Callable<Task<FileDownloadTask.TaskSnapshot>>() {
            @Override
            public Task<FileDownloadTask.TaskSnapshot> call() throws Exception {
                return ref.getFile(file);
            }
        });
    }

    /**
     * @see StorageReference#getMetadata()
     */
    @CheckResult
    @NonNull
    public static Single<StorageMetadata> getMetadata(@NonNull final StorageReference ref) {
        return RxTask.single(new Callable<Task<StorageMetadata>>() {
            @Override
            public Task<StorageMetadata> call() throws Exception {
                return ref.getMetadata();
            }
        });
    }

    /**
     * @see StorageReference#getStream()
     */
    @CheckResult
    @NonNull
    public static Single<StreamDownloadTask.TaskSnapshot> getStream(@NonNull final StorageReference ref) {
        return RxTask.single(new Callable<Task<StreamDownloadTask.TaskSnapshot>>() {
            @Override
            public Task<StreamDownloadTask.TaskSnapshot> call() throws Exception {
                return ref.getStream();
            }
        });
    }

    /**
     * @see StorageReference#getStream(StreamDownloadTask.StreamProcessor)
     */
    @CheckResult
    @NonNull
    public static Single<StreamDownloadTask.TaskSnapshot> getStream(
            @NonNull final StorageReference ref,
            @NonNull final StreamDownloadTask.StreamProcessor streamProcessor) {
        return RxTask.single(new Callable<Task<StreamDownloadTask.TaskSnapshot>>() {
            @Override
            public Task<StreamDownloadTask.TaskSnapshot> call() throws Exception {
                return ref.getStream(streamProcessor);
            }
        });
    }

    /**
     * @see StorageReference#getDownloadUrl()
     */
    @CheckResult
    @NonNull
    public static Single<Uri> getDownloadUrl(@NonNull final StorageReference ref) {
        return RxTask.single(new Callable<Task<Uri>>() {
            @Override
            public Task<Uri> call() throws Exception {
                return ref.getDownloadUrl();
            }
        });
    }

    /**
     * @see StorageReference#putBytes(byte[])
     */
    @CheckResult
    @NonNull
    public static Single<UploadTask.TaskSnapshot> putBytes(@NonNull final StorageReference ref,
                                                           @NonNull final byte[] bytes) {
        return RxTask.single(new Callable<Task<UploadTask.TaskSnapshot>>() {
            @Override
            public Task<UploadTask.TaskSnapshot> call() throws Exception {
                return ref.putBytes(bytes);
            }
        });
    }

    /**
     * @see StorageReference#putBytes(byte[], StorageMetadata)
     */
    @CheckResult
    @NonNull
    public static Single<UploadTask.TaskSnapshot> putBytes(
            @NonNull final StorageReference ref,
            @NonNull final byte[] bytes,
            @NonNull final StorageMetadata storageMetadata) {
        return RxTask.single(new Callable<Task<UploadTask.TaskSnapshot>>() {
            @Override
            public Task<UploadTask.TaskSnapshot> call() throws Exception {
                return ref.putBytes(bytes, storageMetadata);
            }
        });
    }

    /**
     * @see StorageReference#putFile(Uri)
     */
    @CheckResult
    @NonNull
    public static Single<UploadTask.TaskSnapshot> putFile(@NonNull final StorageReference ref,
                                                          @NonNull final Uri uri) {
        return RxTask.single(new Callable<Task<UploadTask.TaskSnapshot>>() {
            @Override
            public Task<UploadTask.TaskSnapshot> call() throws Exception {
                return ref.putFile(uri);
            }
        });
    }

    /**
     * @see StorageReference#putFile(Uri, StorageMetadata)
     */
    @CheckResult
    @NonNull
    public static Single<UploadTask.TaskSnapshot> putFile(
            @NonNull final StorageReference ref,
            @NonNull final Uri uri,
            @NonNull final StorageMetadata storageMetadata) {
        return RxTask.single(new Callable<Task<UploadTask.TaskSnapshot>>() {
            @Override
            public Task<UploadTask.TaskSnapshot> call() throws Exception {
                return ref.putFile(uri, storageMetadata);
            }
        });
    }

    /**
     * @see StorageReference#putFile(Uri, StorageMetadata, Uri)
     */
    @CheckResult
    @NonNull
    public static Single<UploadTask.TaskSnapshot> putFile(
            @NonNull final StorageReference ref,
            @NonNull final Uri uri,
            @Nullable final StorageMetadata storageMetadata,
            @Nullable final Uri existingUploadUri) {
        return RxTask.single(new Callable<Task<UploadTask.TaskSnapshot>>() {
            @Override
            public Task<UploadTask.TaskSnapshot> call() throws Exception {
                return ref.putFile(uri, storageMetadata, existingUploadUri);
            }
        });
    }

    /**
     * @see StorageReference#putStream(InputStream)
     */
    @CheckResult
    @NonNull
    public static Single<UploadTask.TaskSnapshot> putStream(
            @NonNull final StorageReference ref,
            @NonNull final InputStream inputStream) {
        return RxTask.single(new Callable<Task<UploadTask.TaskSnapshot>>() {
            @Override
            public Task<UploadTask.TaskSnapshot> call() throws Exception {
                return ref.putStream(inputStream);
            }
        });
    }

    /**
     * @see StorageReference#putStream(InputStream, StorageMetadata)
     */
    @CheckResult
    @NonNull
    public static Single<UploadTask.TaskSnapshot> putStream(
            @NonNull final StorageReference ref,
            @NonNull final InputStream inputStream,
            @NonNull final StorageMetadata storageMetadata) {
        return RxTask.single(new Callable<Task<UploadTask.TaskSnapshot>>() {
            @Override
            public Task<UploadTask.TaskSnapshot> call() throws Exception {
                return ref.putStream(inputStream, storageMetadata);
            }
        });
    }

    /**
     * @see StorageReference#updateMetadata(StorageMetadata)
     */
    @CheckResult
    @NonNull
    public static Single<StorageMetadata> updateMetadata(
            @NonNull final StorageReference ref,
            @NonNull final StorageMetadata storageMetadata) {
        return RxTask.single(new Callable<Task<StorageMetadata>>() {
            @Override
            public Task<StorageMetadata> call() throws Exception {
                return ref.updateMetadata(storageMetadata);
            }
        });
    }
}
