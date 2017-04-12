package rxfirebase2.tasks;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RxTaskTest {
    @Mock
    Task<String> mockHelloTask;

    private ArgumentCaptor<OnCompleteListener> onComplete;

    @Before
    public void setup() {
        onComplete = ArgumentCaptor.forClass(OnCompleteListener.class);
    }

    @Test
    public void testSingleSuccessful() {
        final String hello = "Hello, world!";
        when(mockHelloTask.getResult())
                .thenReturn(hello);
        when(mockHelloTask.isSuccessful())
                .thenReturn(true);

        RxTask.single(mockHelloTask)
                .test()
                .assertValue(hello)
                .assertNoErrors()
                .assertComplete();
    }

    @Test
    public void testSingleFailure() {
        final String hello = "Hello, world!";
        when(mockHelloTask.isSuccessful())
                .thenReturn(false);
        when(mockHelloTask.getException())
                .thenReturn(new RuntimeException(hello));
        RxTask.single(mockHelloTask)
                .test()
                .assertError(RuntimeException.class)
                .assertErrorMessage(hello);
    }

    @Test
    public void testCompleteSuccessful() {
        final String hello = "Hello, world!";
        when(mockHelloTask.getResult())
                .thenReturn(hello);
        when(mockHelloTask.isSuccessful())
                .thenReturn(true);

        RxTask.completes(mockHelloTask)
                .test()
                .assertNoErrors()
                .assertComplete();
    }

    @Test
    public void testCompleteFailure() {
        final String hello = "Hello, world!";
        when(mockHelloTask.isSuccessful())
                .thenReturn(false);
        when(mockHelloTask.getException())
                .thenReturn(new RuntimeException(hello));

        RxTask.completes(mockHelloTask)
                .test()
                .assertError(RuntimeException.class)
                .assertErrorMessage(hello);
    }
}
