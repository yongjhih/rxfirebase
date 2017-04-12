package rxfirebase2.tasks;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RxTaskTest {
    @Mock
    Task<String> mockHelloTask;

    private ArgumentCaptor<OnCompleteListener> onComplete;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        onComplete = ArgumentCaptor.forClass(OnCompleteListener.class);
    }

    @Test
    public void testSingleSuccessful() {
        final String hello = "Hello, world!";
        when(mockHelloTask.getResult())
                .thenReturn(hello);
        when(mockHelloTask.isSuccessful())
                .thenReturn(true);
        when(mockHelloTask.addOnCompleteListener(onComplete.capture()))
                .thenReturn(mockHelloTask);

        TestObserver<String> obs = TestObserver.create();
        RxTask.single(mockHelloTask).subscribe(obs);
        verify(mockHelloTask).addOnCompleteListener(onComplete.capture());
        onComplete.getValue().onComplete(mockHelloTask);

        obs.assertValue(hello)
           .assertNoErrors()
           .assertComplete();

        verify(mockHelloTask).addOnCompleteListener(any(OnCompleteListener.class));
        verify(mockHelloTask).getResult();
    }

    @Test
    public void testSingleFailure() {
        final String hello = "Hello, world!";
        when(mockHelloTask.isSuccessful())
                .thenReturn(false);
        when(mockHelloTask.getException())
                .thenReturn(new RuntimeException(hello));
        when(mockHelloTask.addOnCompleteListener(onComplete.capture()))
                .thenReturn(mockHelloTask);

        TestObserver<String> obs = TestObserver.create();
        RxTask.single(mockHelloTask).subscribe(obs);
        verify(mockHelloTask).addOnCompleteListener(onComplete.capture());
        onComplete.getValue().onComplete(mockHelloTask);
        obs.assertError(RuntimeException.class)
           .assertErrorMessage(hello)
           .dispose();

        verify(mockHelloTask).addOnCompleteListener(any(OnCompleteListener.class));
        verify(mockHelloTask).getException();
    }

    @Test
    public void testCompleteSuccessful() {
        final String hello = "Hello, world!";
        when(mockHelloTask.getResult())
                .thenReturn(hello);
        when(mockHelloTask.isSuccessful())
                .thenReturn(true);
        when(mockHelloTask.addOnCompleteListener(onComplete.capture()))
                .thenReturn(mockHelloTask);

        TestObserver<String> obs = TestObserver.create();
        RxTask.completes(mockHelloTask).subscribe(obs);
        verify(mockHelloTask).addOnCompleteListener(onComplete.capture());
        onComplete.getValue().onComplete(mockHelloTask);
        obs.assertNoErrors()
           .assertComplete();

        verify(mockHelloTask).addOnCompleteListener(any(OnCompleteListener.class));
    }

    @Test
    public void testCompleteFailure() {
        final String hello = "Hello, world!";
        when(mockHelloTask.isSuccessful())
                .thenReturn(false);
        when(mockHelloTask.getException())
                .thenReturn(new RuntimeException(hello));
        when(mockHelloTask.addOnCompleteListener(onComplete.capture()))
                .thenReturn(mockHelloTask);

        TestObserver<String> obs = TestObserver.create();

        RxTask.completes(mockHelloTask)
              .subscribe(obs);

        verify(mockHelloTask).addOnCompleteListener(onComplete.capture());
        onComplete.getValue().onComplete(mockHelloTask);

        obs.dispose();

        obs.assertError(RuntimeException.class)
           .assertErrorMessage(hello);

        verify(mockHelloTask).addOnCompleteListener(any(OnCompleteListener.class));
        verify(mockHelloTask).getException();
    }
}
