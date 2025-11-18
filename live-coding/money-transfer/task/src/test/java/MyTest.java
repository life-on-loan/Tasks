import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.LinkedList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Execution(ExecutionMode.CONCURRENT)
public class MyTest {

	@Test
	public void name() {
		LinkedList<Integer> mockedList = mock(LinkedList.class);

		when(mockedList.getFirst()).thenReturn(2);
		
		Assertions.assertEquals(2, mockedList.getFirst());
	}
}
