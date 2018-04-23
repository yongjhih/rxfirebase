package rxfirebase2.database;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.tasks.OnCompleteListener;
import com.google.firebase.tasks.Task;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.functions.Function;
import io.reactivex.observers.TestObserver;
import rxfirebase2.admin.database.RxDatabaseReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RxDatabaseReferenceTest {

    @Mock
    DatabaseReference mockDatabaseReference;

    @Mock
    Query mockQuery;

    @Mock
    DataSnapshot mockDataSnapshot;

    @Mock
    DatabaseError mockDatabaseError;

    @Mock
    MutableData mockMutableData;

    @Mock
    Function<MutableData, Transaction.Result> mockTransactionTask;

    @Mock
    Task<Void> mockTask;

    private ArgumentCaptor<ChildEventListener> childEventListener;

    private ArgumentCaptor<ValueEventListener> valueEventListener;

    private ArgumentCaptor<Transaction.Handler> transactionHandler;

    private ArgumentCaptor<OnCompleteListener> onCompleteListener;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        childEventListener = ArgumentCaptor.forClass(ChildEventListener.class);
        valueEventListener = ArgumentCaptor.forClass(ValueEventListener.class);
        transactionHandler = ArgumentCaptor.forClass(Transaction.Handler.class);
        onCompleteListener = ArgumentCaptor.forClass(OnCompleteListener.class);
    }

    @Test
    public void testDataChanges_DataReference() {
        TestObserver<DataSnapshot> sub = TestObserver.create();

        RxDatabaseReference.changes(mockDatabaseReference)
                .subscribe(sub);

        verifyDataReferenceAddValueEventListener();
        callValueEventOnDataChange("Foo");

        sub.assertNotComplete();
        sub.assertValueCount(1);

        sub.dispose();

        callValueEventOnDataChange("Foo");

        // Ensure no more values are emitted after unsubscribe
        sub.assertValueCount(1);
    }

    @Test
    public void testData_DataReference() {
        TestObserver<DataSnapshot> sub = TestObserver.create();

        RxDatabaseReference.single(mockDatabaseReference)
                .subscribe(sub);

        verifyDataReferenceAddListenerForSingleValueEvent();
        callValueEventOnDataChange("Foo");
        sub.dispose();

        // Ensure no more values are emitted after unsubscribe
        callValueEventOnDataChange("Foo");

        sub.assertNoErrors();
        sub.assertComplete();
        sub.assertValueCount(1);
    }

    @Test
    public void testData_DataReference_onCancelled() {
        TestObserver<DataSnapshot> sub = TestObserver.create();

        RxDatabaseReference.single(mockDatabaseReference)
                .subscribe(sub);

        verifyDataReferenceAddListenerForSingleValueEvent();
        callValueEventOnCancelled(new DatabaseException("foo"));

        sub.assertError(DatabaseException.class);
        sub.assertNoValues();

        sub.dispose();

        callValueEventOnCancelled(new DatabaseException("foo"));

        // Ensure no more values are emitted after unsubscribe
        assertThat(sub.errorCount())
                .isEqualTo(1);
    }

    private void verifyDataReferenceAddChildEventListener() {
        verify(mockDatabaseReference)
                .addChildEventListener(childEventListener.capture());
    }

    private void verifyDataReferenceAddListenerForSingleValueEvent() {
        verify(mockDatabaseReference)
                .addListenerForSingleValueEvent(valueEventListener.capture());
    }

    private void verifyDataReferenceAddValueEventListener() {
        verify(mockDatabaseReference)
                .addValueEventListener(valueEventListener.capture());
    }

    private void verifyQueryAddChildEventListener() {
        verify(mockQuery)
                .addChildEventListener(childEventListener.capture());
    }

    private void verifyQueryAddListenerForSingleValueEvent() {
        verify(mockQuery)
                .addListenerForSingleValueEvent(valueEventListener.capture());
    }

    private void verifyQueryAddValueEventListener() {
        verify(mockQuery)
                .addValueEventListener(valueEventListener.capture());
    }

    private void verifyRunTransaction() {
        verify(mockDatabaseReference)
                .runTransaction(transactionHandler.capture());
    }

    private void verifyRunTransactionLocal() {
        verify(mockDatabaseReference)
                .runTransaction(transactionHandler.capture(), anyBoolean());
    }

    private void verifyTransactionTaskCall() throws Exception {
        verify(mockTransactionTask)
                .apply(mockMutableData);
    }

    private void verifyAddOnCompleteListenerForTask() {
        //noinspection unchecked
        verify(mockTask)
                .addOnCompleteListener(onCompleteListener.capture());
    }

    private void callOnChildAdded(String previousChildName) {
        childEventListener.getValue().onChildAdded(mockDataSnapshot, previousChildName);
    }

    private void callOnChildChanged(String previousChildName) {
        childEventListener.getValue().onChildChanged(mockDataSnapshot, previousChildName);
    }

    private void callOnChildMoved(String previousChildName) {
        childEventListener.getValue().onChildMoved(mockDataSnapshot, previousChildName);
    }

    private void callOnChildRemoved() {
        childEventListener.getValue().onChildRemoved(mockDataSnapshot);
    }

    private void callChildOnCancelled() {
        childEventListener.getValue().onCancelled(mockDatabaseError);
    }

    private <T> void callValueEventOnDataChange(T value) {
        when(mockDataSnapshot.exists())
                .thenReturn(true);
        when(mockDataSnapshot.getValue((Class<T>) value.getClass()))
                .thenReturn(value);

        valueEventListener.getValue().onDataChange(mockDataSnapshot);
    }

    private <T> void callValueEventOnDataChange(GenericTypeIndicator<T> type, T value) {
        when(mockDataSnapshot.exists())
                .thenReturn(true);
        when(mockDataSnapshot.getValue(type))
                .thenReturn(value);

        valueEventListener.getValue().onDataChange(mockDataSnapshot);
    }

    private void callValueEventOnCancelled(DatabaseException mockedException) {
        when(mockDatabaseError.toException())
                .thenReturn(mockedException);

        valueEventListener.getValue().onCancelled(mockDatabaseError);
    }

    private void callTransactionOnComplete() {
        transactionHandler.getValue().doTransaction(mockMutableData);
        transactionHandler.getValue().onComplete(null, true, mockDataSnapshot);
    }

    private void callTransactionOnCompleteWithError(DatabaseException mockedException) {
        when(mockDatabaseError.toException())
                .thenReturn(mockedException);

        transactionHandler.getValue().onComplete(mockDatabaseError, false, mockDataSnapshot);
    }

    private void callTaskOnComplete() {
        when(mockTask.isSuccessful())
                .thenReturn(true);

        //noinspection unchecked
        onCompleteListener.getValue().onComplete(mockTask);
    }

    private void callTaskOnCompleteWithError(Exception e) {
        when(mockTask.isSuccessful())
                .thenReturn(false);

        when(mockTask.getException())
                .thenReturn(e);

        //noinspection unchecked
        onCompleteListener.getValue().onComplete(mockTask);
    }
}
