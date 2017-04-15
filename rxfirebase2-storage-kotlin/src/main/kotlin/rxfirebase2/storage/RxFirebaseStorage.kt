@file:Suppress("NOTHING_TO_INLINE")

package rxfirebase2.storage

import android.net.Uri
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StreamDownloadTask
import com.google.firebase.storage.StreamDownloadTask.StreamProcessor
import com.google.firebase.storage.UploadTask
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import java.io.InputStream

inline fun StorageReference.getsBytes(maxDownloadSizeBytes: Long)
    : Single<ByteArray>
    = RxFirebaseStorage.getBytes(this, maxDownloadSizeBytes)

inline fun StorageReference.deletes()
    : Completable
    = RxFirebaseStorage.delete(this)

inline fun StorageReference.getsFile(uri: Uri)
    : Single<FileDownloadTask.TaskSnapshot>
    = RxFirebaseStorage.getFile(this, uri)

inline fun StorageReference.getsFile(file: File)
    : Single<FileDownloadTask.TaskSnapshot>
    = RxFirebaseStorage.getFile(this, file)

inline fun StorageReference.getsMetadata()
    : Single<StorageMetadata>
    = RxFirebaseStorage.getMetadata(this)

inline fun StorageReference.getsStream()
    : Single<StreamDownloadTask.TaskSnapshot>
    = RxFirebaseStorage.getStream(this)

inline fun StorageReference.getsStream(streamProcessor: StreamProcessor)
    : Single<StreamDownloadTask.TaskSnapshot>
    = RxFirebaseStorage.getStream(this, streamProcessor)

inline fun StorageReference.getsDownloadUrl()
    : Single<Uri>
    = RxFirebaseStorage.getDownloadUrl(this)

inline fun StorageReference.putsBytes(bytes: ByteArray)
    : Single<UploadTask.TaskSnapshot>
    = RxFirebaseStorage.putBytes(this, bytes)

inline fun StorageReference.putsBytes(bytes: ByteArray, storageMetadata: StorageMetadata)
    : Single<UploadTask.TaskSnapshot>
    = RxFirebaseStorage.putBytes(this, bytes, storageMetadata)

inline fun StorageReference.putsFile(uri: Uri)
    : Single<UploadTask.TaskSnapshot>
    = RxFirebaseStorage.putFile(this, uri)

inline fun StorageReference.putsFile(uri: Uri, storageMetadata: StorageMetadata)
    : Single<UploadTask.TaskSnapshot>
    = RxFirebaseStorage.putFile(this, uri, storageMetadata)

inline fun StorageReference.putsFile(uri: Uri, storageMetadata: StorageMetadata,
    existingUploadUri: Uri)
    : Single<UploadTask.TaskSnapshot>
    = RxFirebaseStorage.putFile(this, uri, storageMetadata, existingUploadUri)

inline fun StorageReference.putsStream(inputStream: InputStream)
    : Single<UploadTask.TaskSnapshot>
    = RxFirebaseStorage.putStream(this, inputStream)

inline fun StorageReference.putsStream(inputStream: InputStream, storageMetadata: StorageMetadata)
    : Single<UploadTask.TaskSnapshot>
    = RxFirebaseStorage.putStream(this, inputStream, storageMetadata)

inline fun StorageReference.updatesMetadata(storageMetadata: StorageMetadata)
    : Single<StorageMetadata>
    = RxFirebaseStorage.updateMetadata(this, storageMetadata)