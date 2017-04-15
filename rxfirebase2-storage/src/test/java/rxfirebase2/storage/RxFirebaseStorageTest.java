package rxfirebase2.storage;

import android.net.Uri;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RxFirebaseStorageTest {

  @Mock StorageReference mockStorageReference;

  @Mock Task<Void> mockTask;

  @Mock Task<byte[]> mockBytesTask;

  @Mock FileDownloadTask mockFileDownloadTask;

  @Mock FileDownloadTask.TaskSnapshot mockFileDownloadTaskSnapshot;

  @Mock Uri mockUri;

  @Mock File mockFile;

  @Mock StorageMetadata mockStorageMetadata;

  @Mock Task<StorageMetadata> mockStorageMetadataTask;

  @Mock StreamDownloadTask.TaskSnapshot mockStreamDownloadTaskSnapshot;

  @Mock StreamDownloadTask mockStreamDownloadTask;

  @Mock StreamDownloadTask.StreamProcessor mockStreamProcessor;

  @Mock Task<Uri> mockUriTask;

  @Mock UploadTask.TaskSnapshot mockUploadTaskSnapshot;

  @Mock UploadTask mockUploadTask;

  @Mock InputStream mockInputStream;

  private ArgumentCaptor<OnCompleteListener> onComplete;

  @Before public void setup() {
    MockitoAnnotations.initMocks(this);
    onComplete = ArgumentCaptor.forClass(OnCompleteListener.class);
  }

  @Test public void testGetBytes() {
    mockSuccessfulResultForTask(mockBytesTask, new byte[] { 1, 2, 3 });
    Mockito.when(mockStorageReference.getBytes(3)).thenReturn(mockBytesTask);

    TestObserver<byte[]> obs = TestObserver.create();

    RxFirebaseStorage.getBytes(mockStorageReference, 3).subscribe(obs);
    verifyAddOnCompleteListenerForTask(mockBytesTask);

    callOnComplete(mockBytesTask);
    obs.dispose();

    callOnComplete(mockBytesTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValue(new Predicate<byte[]>() {
      @Override public boolean test(byte[] bytes) throws Exception {
        return Arrays.equals(bytes, new byte[] { 1, 2, 3 });
      }
    });
  }

  @Test public void testGetBytes_notSuccessful() {
    mockNotSuccessfulResultForTask(mockBytesTask, new IllegalStateException());
    when(mockStorageReference.getBytes(3)).thenReturn(mockBytesTask);

    TestObserver<byte[]> obs = TestObserver.create();

    RxFirebaseStorage.getBytes(mockStorageReference, 3).subscribe(obs);

    verifyAddOnCompleteListenerForTask(mockBytesTask);

    callOnComplete(mockBytesTask);
    obs.dispose();

    callOnComplete(mockBytesTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testDelete() {
    mockSuccessfulResultForTask(mockTask);
    Mockito.when(mockStorageReference.delete()).thenReturn(mockTask);

    TestObserver<byte[]> obs = TestObserver.create();

    RxFirebaseStorage.delete(mockStorageReference).subscribe(obs);

    verifyAddOnCompleteListenerForTask(mockTask);

    callOnComplete(mockTask);
    obs.dispose();

    callOnComplete(mockTask);

    obs.assertNoErrors();
    obs.assertComplete();
  }

  @Test public void testDelete_notSuccessful() {
    mockNotSuccessfulResultForTask(mockTask, new IllegalStateException());
    when(mockStorageReference.delete()).thenReturn(mockTask);

    TestObserver obs = TestObserver.create();

    RxFirebaseStorage.delete(mockStorageReference).subscribe(obs);
    verifyAddOnCompleteListenerForTask(mockTask);

    callOnComplete(mockTask);
    obs.dispose();

    callOnComplete(mockTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @SuppressWarnings("Duplicates") @Test public void testGetFileUri() {
    mockSuccessfulResultForTask(mockFileDownloadTask, mockFileDownloadTaskSnapshot);
    when(mockStorageReference.getFile(mockUri)).thenReturn(mockFileDownloadTask);
    when(mockFileDownloadTaskSnapshot.getBytesTransferred()).thenReturn(1000L);
    when(mockFileDownloadTaskSnapshot.getTotalByteCount()).thenReturn(1000L);

    TestObserver<FileDownloadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.getFile(mockStorageReference, mockUri).subscribe(obs);

    verifyAddOnCompleteListenerForTask(mockFileDownloadTask);

    callOnComplete(mockFileDownloadTask);
    obs.dispose();

    callOnComplete(mockFileDownloadTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValue(new Predicate<FileDownloadTask.TaskSnapshot>() {
      @Override public boolean test(FileDownloadTask.TaskSnapshot taskSnapshot) throws Exception {
        return taskSnapshot.getBytesTransferred() == taskSnapshot.getTotalByteCount()
            && taskSnapshot.getTotalByteCount() == 1000;
      }
    });
  }

  @SuppressWarnings("Duplicates") @Test public void testGetFileUri_notSuccessful() {
    mockNotSuccessfulResultForTask(mockFileDownloadTask, new IllegalStateException());
    when(mockStorageReference.getFile(mockUri)).thenReturn(mockFileDownloadTask);

    TestObserver<FileDownloadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.getFile(mockStorageReference, mockUri).subscribe(obs);
    verifyAddOnCompleteListenerForTask(mockFileDownloadTask);

    callOnComplete(mockFileDownloadTask);
    obs.dispose();

    callOnComplete(mockFileDownloadTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @SuppressWarnings("Duplicates") @Test public void testGetFile() {
    mockSuccessfulResultForTask(mockFileDownloadTask, mockFileDownloadTaskSnapshot);
    when(mockStorageReference.getFile(mockFile)).thenReturn(mockFileDownloadTask);
    when(mockFileDownloadTaskSnapshot.getBytesTransferred()).thenReturn(1000L);
    when(mockFileDownloadTaskSnapshot.getTotalByteCount()).thenReturn(1000L);

    TestObserver<FileDownloadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.getFile(mockStorageReference, mockFile).subscribe(obs);

    verifyAddOnCompleteListenerForTask(mockFileDownloadTask);

    callOnComplete(mockFileDownloadTask);
    obs.dispose();

    callOnComplete(mockFileDownloadTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValue(new Predicate<FileDownloadTask.TaskSnapshot>() {
      @Override public boolean test(FileDownloadTask.TaskSnapshot taskSnapshot) throws Exception {
        return taskSnapshot.getBytesTransferred() == taskSnapshot.getTotalByteCount()
            && taskSnapshot.getTotalByteCount() == 1000;
      }
    });
  }

  @SuppressWarnings("Duplicates") @Test public void testGetFile_notSuccessful() {
    mockNotSuccessfulResultForTask(mockFileDownloadTask, new IllegalStateException());
    when(mockStorageReference.getFile(mockFile)).thenReturn(mockFileDownloadTask);

    TestObserver<FileDownloadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.getFile(mockStorageReference, mockFile).subscribe(obs);
    verifyAddOnCompleteListenerForTask(mockFileDownloadTask);

    callOnComplete(mockFileDownloadTask);
    obs.dispose();

    callOnComplete(mockFileDownloadTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testGetMetadata() {
    mockSuccessfulResultForTask(mockStorageMetadataTask, mockStorageMetadata);
    when(mockStorageReference.getMetadata()).thenReturn(mockStorageMetadataTask);
    when(mockStorageMetadata.getName()).thenReturn("Test");

    TestObserver<StorageMetadata> obs = TestObserver.create();

    RxFirebaseStorage.getMetadata(mockStorageReference).subscribe(obs);

    verifyAddOnCompleteListenerForTask(mockStorageMetadataTask);

    callOnComplete(mockStorageMetadataTask);
    obs.dispose();

    callOnComplete(mockStorageMetadataTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValue(new Predicate<StorageMetadata>() {
      @Override public boolean test(StorageMetadata storageMetadata) throws Exception {
        return "Test".equals(storageMetadata.getName());
      }
    });
  }

  @Test public void testGetMetaData_notSuccessful() {
    mockNotSuccessfulResultForTask(mockStorageMetadataTask, new IllegalStateException());
    when(mockStorageReference.getMetadata()).thenReturn(mockStorageMetadataTask);

    TestObserver<StorageMetadata> obs = TestObserver.create();

    RxFirebaseStorage.getMetadata(mockStorageReference).subscribe(obs);
    verifyAddOnCompleteListenerForTask(mockStorageMetadataTask);

    callOnComplete(mockStorageMetadataTask);
    obs.dispose();

    callOnComplete(mockStorageMetadataTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testGetStream() {
    mockSuccessfulResultForTask(mockStreamDownloadTask, mockStreamDownloadTaskSnapshot);
    when(mockStorageReference.getStream()).thenReturn(mockStreamDownloadTask);
    when(mockStreamDownloadTaskSnapshot.getTotalByteCount()).thenReturn(1000L);
    when(mockStreamDownloadTaskSnapshot.getBytesTransferred()).thenReturn(1000L);

    TestObserver<StreamDownloadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.getStream(mockStorageReference).subscribe(obs);

    verifyAddOnCompleteListenerForTask(mockStreamDownloadTask);

    callOnComplete(mockStreamDownloadTask);
    obs.dispose();

    callOnComplete(mockStreamDownloadTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValue(new Predicate<StreamDownloadTask.TaskSnapshot>() {
      @Override public boolean test(StreamDownloadTask.TaskSnapshot taskSnapshot) throws Exception {
        return taskSnapshot.getBytesTransferred() == taskSnapshot.getTotalByteCount()
            && taskSnapshot.getTotalByteCount() == 1000L;
      }
    });
  }

  @Test public void testGetStream_notSuccessful() {
    mockNotSuccessfulResultForTask(mockStreamDownloadTask, new IllegalStateException());
    when(mockStorageReference.getStream()).thenReturn(mockStreamDownloadTask);

    TestObserver<StreamDownloadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.getStream(mockStorageReference).subscribe(obs);
    verifyAddOnCompleteListenerForTask(mockStreamDownloadTask);

    callOnComplete(mockStreamDownloadTask);
    obs.dispose();

    callOnComplete(mockStreamDownloadTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testGetStreamWithProcessor() {
    mockSuccessfulResultForTask(mockStreamDownloadTask, mockStreamDownloadTaskSnapshot);
    when(mockStorageReference.getStream(mockStreamProcessor)).thenReturn(mockStreamDownloadTask);
    when(mockStreamDownloadTaskSnapshot.getTotalByteCount()).thenReturn(1000L);
    when(mockStreamDownloadTaskSnapshot.getBytesTransferred()).thenReturn(1000L);

    TestObserver<StreamDownloadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.getStream(mockStorageReference, mockStreamProcessor).subscribe(obs);

    verifyAddOnCompleteListenerForTask(mockStreamDownloadTask);

    callOnComplete(mockStreamDownloadTask);
    obs.dispose();

    callOnComplete(mockStreamDownloadTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValue(new Predicate<StreamDownloadTask.TaskSnapshot>() {
      @Override public boolean test(StreamDownloadTask.TaskSnapshot taskSnapshot) throws Exception {
        return taskSnapshot.getBytesTransferred() == taskSnapshot.getTotalByteCount()
            && taskSnapshot.getTotalByteCount() == 1000L;
      }
    });
  }

  @Test public void testGetStreamWithProcessor_notSuccessful() {
    mockNotSuccessfulResultForTask(mockStreamDownloadTask, new IllegalStateException());
    when(mockStorageReference.getStream(mockStreamProcessor)).thenReturn(mockStreamDownloadTask);

    TestObserver<StreamDownloadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.getStream(mockStorageReference, mockStreamProcessor).subscribe(obs);
    verifyAddOnCompleteListenerForTask(mockStreamDownloadTask);

    callOnComplete(mockStreamDownloadTask);
    obs.dispose();

    callOnComplete(mockStreamDownloadTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testGetDownloadUrl() {
    mockSuccessfulResultForTask(mockUriTask, mockUri);
    when(mockStorageReference.getDownloadUrl()).thenReturn(mockUriTask);
    when(mockUri.toString()).thenReturn("file:///test");

    TestObserver<Uri> obs = TestObserver.create();

    RxFirebaseStorage.getDownloadUrl(mockStorageReference).subscribe(obs);

    verifyAddOnCompleteListenerForTask(mockUriTask);

    callOnComplete(mockUriTask);
    obs.dispose();

    callOnComplete(mockUriTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValue(new Predicate<Uri>() {
      @Override public boolean test(Uri uri) throws Exception {
        return "file:///test".equals(uri.toString());
      }
    });
  }

  @Test public void testGetDownloadUrl_notSuccessful() {
    mockNotSuccessfulResultForTask(mockUriTask, new IllegalStateException());
    when(mockStorageReference.getDownloadUrl()).thenReturn(mockUriTask);

    TestObserver<Uri> obs = TestObserver.create();

    RxFirebaseStorage.getDownloadUrl(mockStorageReference).subscribe(obs);
    verifyAddOnCompleteListenerForTask(mockUriTask);

    callOnComplete(mockUriTask);
    obs.dispose();

    callOnComplete(mockUriTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testPutBytes() {
    mockSuccessfulResultForTask(mockUploadTask, mockUploadTaskSnapshot);
    when(mockStorageReference.putBytes(new byte[] { 1, 2, 3 })).thenReturn(mockUploadTask);
    when(mockUploadTaskSnapshot.getBytesTransferred()).thenReturn(1000L);
    when(mockUploadTaskSnapshot.getTotalByteCount()).thenReturn(1000L);

    TestObserver<UploadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.putBytes(mockStorageReference, new byte[] { 1, 2, 3 }).subscribe(obs);

    verifyAddOnCompleteListenerForTask(mockUploadTask);

    callOnComplete(mockUploadTask);
    obs.dispose();

    callOnComplete(mockUploadTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValue(new Predicate<UploadTask.TaskSnapshot>() {
      @Override public boolean test(UploadTask.TaskSnapshot taskSnapshot) throws Exception {
        return taskSnapshot.getBytesTransferred() == taskSnapshot.getTotalByteCount()
            && taskSnapshot.getTotalByteCount() == 1000L;
      }
    });
  }

  @Test public void testPutBytes_notSuccessful() {
    mockNotSuccessfulResultForTask(mockUploadTask, new IllegalStateException());
    when(mockStorageReference.putBytes(new byte[] { 1, 2, 3 })).thenReturn(mockUploadTask);

    TestObserver<UploadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.putBytes(mockStorageReference, new byte[] { 1, 2, 3 }).subscribe(obs);
    verifyAddOnCompleteListenerForTask(mockUploadTask);

    callOnComplete(mockUploadTask);
    obs.dispose();

    callOnComplete(mockUploadTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testPutBytesWithMetadata() {
    mockSuccessfulResultForTask(mockUploadTask, mockUploadTaskSnapshot);
    when(mockStorageReference.putBytes(new byte[] { 1, 2, 3 }, mockStorageMetadata)).thenReturn(
        mockUploadTask);
    when(mockUploadTaskSnapshot.getBytesTransferred()).thenReturn(1000L);
    when(mockUploadTaskSnapshot.getTotalByteCount()).thenReturn(1000L);

    TestObserver<UploadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.putBytes(mockStorageReference, new byte[] { 1, 2, 3 }, mockStorageMetadata)
        .subscribe(obs);

    verifyAddOnCompleteListenerForTask(mockUploadTask);

    callOnComplete(mockUploadTask);
    obs.dispose();

    callOnComplete(mockUploadTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValue(new Predicate<UploadTask.TaskSnapshot>() {
      @Override public boolean test(UploadTask.TaskSnapshot taskSnapshot) throws Exception {
        return taskSnapshot.getBytesTransferred() == taskSnapshot.getTotalByteCount()
            && taskSnapshot.getTotalByteCount() == 1000L;
      }
    });
  }

  @Test public void testPutBytesWithMetadata_notSuccessful() {
    mockNotSuccessfulResultForTask(mockUploadTask, new IllegalStateException());
    when(mockStorageReference.putBytes(new byte[] { 1, 2, 3 }, mockStorageMetadata)).thenReturn(
        mockUploadTask);

    TestObserver<UploadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.putBytes(mockStorageReference, new byte[] { 1, 2, 3 }, mockStorageMetadata)
        .subscribe(obs);
    verifyAddOnCompleteListenerForTask(mockUploadTask);

    callOnComplete(mockUploadTask);
    obs.dispose();

    callOnComplete(mockUploadTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testPutFile() {
    mockSuccessfulResultForTask(mockUploadTask, mockUploadTaskSnapshot);
    when(mockStorageReference.putFile(mockUri)).thenReturn(mockUploadTask);
    when(mockUploadTaskSnapshot.getBytesTransferred()).thenReturn(1000L);
    when(mockUploadTaskSnapshot.getTotalByteCount()).thenReturn(1000L);

    TestObserver<UploadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.putFile(mockStorageReference, mockUri).subscribe(obs);

    verifyAddOnCompleteListenerForTask(mockUploadTask);

    callOnComplete(mockUploadTask);
    obs.dispose();

    callOnComplete(mockUploadTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValue(new Predicate<UploadTask.TaskSnapshot>() {
      @Override public boolean test(UploadTask.TaskSnapshot taskSnapshot) throws Exception {
        return taskSnapshot.getBytesTransferred() == taskSnapshot.getTotalByteCount()
            && taskSnapshot.getTotalByteCount() == 1000L;
      }
    });
  }

  @Test public void testPutFile_notSuccessful() {
    mockNotSuccessfulResultForTask(mockUploadTask, new IllegalStateException());
    when(mockStorageReference.putFile(mockUri)).thenReturn(mockUploadTask);

    TestObserver<UploadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.putFile(mockStorageReference, mockUri).subscribe(obs);
    verifyAddOnCompleteListenerForTask(mockUploadTask);

    callOnComplete(mockUploadTask);
    obs.dispose();

    callOnComplete(mockUploadTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testPutFileWithMetadata() {
    mockSuccessfulResultForTask(mockUploadTask, mockUploadTaskSnapshot);
    when(mockStorageReference.putFile(mockUri, mockStorageMetadata)).thenReturn(mockUploadTask);
    when(mockUploadTaskSnapshot.getBytesTransferred()).thenReturn(1000L);
    when(mockUploadTaskSnapshot.getTotalByteCount()).thenReturn(1000L);

    TestObserver<UploadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.putFile(mockStorageReference, mockUri, mockStorageMetadata).subscribe(obs);

    verifyAddOnCompleteListenerForTask(mockUploadTask);

    callOnComplete(mockUploadTask);
    obs.dispose();

    callOnComplete(mockUploadTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValue(new Predicate<UploadTask.TaskSnapshot>() {
      @Override public boolean test(UploadTask.TaskSnapshot taskSnapshot) throws Exception {
        return taskSnapshot.getBytesTransferred() == taskSnapshot.getTotalByteCount()
            && taskSnapshot.getTotalByteCount() == 1000L;
      }
    });
  }

  @Test public void testPutFileWithMetadata_notSuccessful() {
    mockNotSuccessfulResultForTask(mockUploadTask, new IllegalStateException());
    when(mockStorageReference.putFile(mockUri, mockStorageMetadata)).thenReturn(mockUploadTask);

    TestObserver<UploadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.putFile(mockStorageReference, mockUri, mockStorageMetadata).subscribe(obs);
    verifyAddOnCompleteListenerForTask(mockUploadTask);

    callOnComplete(mockUploadTask);
    obs.dispose();

    callOnComplete(mockUploadTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testPutFileWithMetadataAndUri() {
    mockSuccessfulResultForTask(mockUploadTask, mockUploadTaskSnapshot);
    when(mockStorageReference.putFile(mockUri, mockStorageMetadata, mockUri)).thenReturn(
        mockUploadTask);
    when(mockUploadTaskSnapshot.getBytesTransferred()).thenReturn(1000L);
    when(mockUploadTaskSnapshot.getTotalByteCount()).thenReturn(1000L);

    TestObserver<UploadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.putFile(mockStorageReference, mockUri, mockStorageMetadata, mockUri)
        .subscribe(obs);

    verifyAddOnCompleteListenerForTask(mockUploadTask);

    callOnComplete(mockUploadTask);
    obs.dispose();

    callOnComplete(mockUploadTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValue(new Predicate<UploadTask.TaskSnapshot>() {
      @Override public boolean test(UploadTask.TaskSnapshot taskSnapshot) throws Exception {
        return taskSnapshot.getBytesTransferred() == taskSnapshot.getTotalByteCount()
            && taskSnapshot.getTotalByteCount() == 1000L;
      }
    });
  }

  @Test public void testPutFileWithMetadataAndUri_notSuccessful() {
    mockNotSuccessfulResultForTask(mockUploadTask, new IllegalStateException());
    when(mockStorageReference.putFile(mockUri, mockStorageMetadata, mockUri)).thenReturn(
        mockUploadTask);

    TestObserver<UploadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.putFile(mockStorageReference, mockUri, mockStorageMetadata, mockUri)
        .subscribe(obs);
    verifyAddOnCompleteListenerForTask(mockUploadTask);

    callOnComplete(mockUploadTask);
    obs.dispose();

    callOnComplete(mockUploadTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testPutStream() {
    mockSuccessfulResultForTask(mockUploadTask, mockUploadTaskSnapshot);
    when(mockStorageReference.putStream(mockInputStream)).thenReturn(mockUploadTask);
    when(mockUploadTaskSnapshot.getBytesTransferred()).thenReturn(1000L);
    when(mockUploadTaskSnapshot.getTotalByteCount()).thenReturn(1000L);

    TestObserver<UploadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.putStream(mockStorageReference, mockInputStream).subscribe(obs);

    verifyAddOnCompleteListenerForTask(mockUploadTask);

    callOnComplete(mockUploadTask);
    obs.dispose();

    callOnComplete(mockUploadTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValue(new Predicate<UploadTask.TaskSnapshot>() {
      @Override public boolean test(UploadTask.TaskSnapshot taskSnapshot) throws Exception {
        return taskSnapshot.getBytesTransferred() == taskSnapshot.getTotalByteCount()
            && taskSnapshot.getTotalByteCount() == 1000L;
      }
    });
  }

  @Test public void testPutStream_notSuccessful() {
    mockNotSuccessfulResultForTask(mockUploadTask, new IllegalStateException());
    when(mockStorageReference.putStream(mockInputStream)).thenReturn(mockUploadTask);

    TestObserver<UploadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.putStream(mockStorageReference, mockInputStream).subscribe(obs);
    verifyAddOnCompleteListenerForTask(mockUploadTask);

    callOnComplete(mockUploadTask);
    obs.dispose();

    callOnComplete(mockUploadTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testPutStreamWithMetadata() {
    mockSuccessfulResultForTask(mockUploadTask, mockUploadTaskSnapshot);
    when(mockStorageReference.putStream(mockInputStream, mockStorageMetadata)).thenReturn(
        mockUploadTask);
    when(mockUploadTaskSnapshot.getBytesTransferred()).thenReturn(1000L);
    when(mockUploadTaskSnapshot.getTotalByteCount()).thenReturn(1000L);

    TestObserver<UploadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.putStream(mockStorageReference, mockInputStream, mockStorageMetadata)
        .subscribe(obs);

    verifyAddOnCompleteListenerForTask(mockUploadTask);

    callOnComplete(mockUploadTask);
    obs.dispose();

    callOnComplete(mockUploadTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValue(new Predicate<UploadTask.TaskSnapshot>() {
      @Override public boolean test(UploadTask.TaskSnapshot taskSnapshot) throws Exception {
        return taskSnapshot.getBytesTransferred() == taskSnapshot.getTotalByteCount()
            && taskSnapshot.getTotalByteCount() == 1000L;
      }
    });
  }

  @Test public void testPutStreamWithMetadata_notSuccessful() {
    mockNotSuccessfulResultForTask(mockUploadTask, new IllegalStateException());
    when(mockStorageReference.putStream(mockInputStream, mockStorageMetadata)).thenReturn(
        mockUploadTask);

    TestObserver<UploadTask.TaskSnapshot> obs = TestObserver.create();

    RxFirebaseStorage.putStream(mockStorageReference, mockInputStream, mockStorageMetadata)
        .subscribe(obs);
    verifyAddOnCompleteListenerForTask(mockUploadTask);

    callOnComplete(mockUploadTask);
    obs.dispose();

    callOnComplete(mockUploadTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  @Test public void testUpdateMetadata() {
    when(mockStorageMetadata.getName()).thenReturn("metadata");
    mockSuccessfulResultForTask(mockStorageMetadataTask, mockStorageMetadata);

    TestObserver<StorageMetadata> obs = TestObserver.create();
    when(mockStorageReference.updateMetadata(mockStorageMetadata)).thenReturn(
        mockStorageMetadataTask);

    RxFirebaseStorage.updateMetadata(mockStorageReference, mockStorageMetadata).subscribe(obs);

    verifyAddOnCompleteListenerForTask(mockStorageMetadataTask);

    callOnComplete(mockStorageMetadataTask);
    obs.dispose();

    callOnComplete(mockStorageMetadataTask);

    obs.assertNoErrors();
    obs.assertComplete();
    obs.assertValue(new Predicate<StorageMetadata>() {
      @Override public boolean test(StorageMetadata metadata) throws Exception {
        return "metadata".equals(metadata.getName());
      }
    });
  }

  @Test public void testUpdateMetadata_notSuccessful() {
    mockNotSuccessfulResultForTask(mockStorageMetadataTask, new IllegalStateException());

    TestObserver<StorageMetadata> obs = TestObserver.create();
    when(mockStorageReference.updateMetadata(mockStorageMetadata)).thenReturn(
        mockStorageMetadataTask);
    RxFirebaseStorage.updateMetadata(mockStorageReference, mockStorageMetadata).subscribe(obs);

    verifyAddOnCompleteListenerForTask(mockStorageMetadataTask);

    callOnComplete(mockStorageMetadataTask);
    obs.dispose();

    callOnComplete(mockStorageMetadataTask);

    obs.assertError(IllegalStateException.class);
    obs.assertNoValues();
  }

  private <T> void mockSuccessfulResultForTask(Task<T> task, T result) {
    when(task.getResult()).thenReturn(result);
    mockSuccessfulResultForTask(task);
  }

  private void mockSuccessfulResultForTask(Task task) {
    when(task.isSuccessful()).thenReturn(true);

    //noinspection unchecked
    when(task.addOnCompleteListener(onComplete.capture())).thenReturn(task);
  }

  private void mockNotSuccessfulResultForTask(Task task, Exception exception) {
    when(task.isSuccessful()).thenReturn(false);

    when(task.getResult()).thenReturn(null);

    when(task.getException()).thenReturn(exception);
  }

  private void verifyAddOnCompleteListenerForTask(Task task) {
    //noinspection unchecked
    verify(task).addOnCompleteListener(onComplete.capture());
  }

  @SuppressWarnings("unchecked") private void callOnComplete(Task<?> task) {
    verify(task).addOnCompleteListener(onComplete.capture());
    onComplete.getValue().onComplete(task);
  }
}
