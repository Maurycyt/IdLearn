package mimuw.idlearn.scoring;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD;

import mimuw.idlearn.core.Event;
import mimuw.idlearn.core.Listener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;

public class PointsManagerTest {
	@Test
	public void testBasic() {
		PointsManager.setPoints(0);
		assertEquals(0, PointsManager.showPoints());
		PointsManager.addPoints(100);
		assertEquals(100, PointsManager.showPoints());
		try {
			PointsManager.payPoints(60);
		}
		catch (NotEnoughException e) {
			fail();
		}
		assertEquals(40, PointsManager.showPoints());
	}

	@Test
	public void testException() {
		PointsManager.setPoints(0);
		try {
			PointsManager.payPoints(10);
			fail();
		}
		catch (NotEnoughException e) {
		}
		assertEquals(0, PointsManager.showPoints());
	}

	private static class LongWrapper {
		public long value = 0;
	}

	@Test
	public void testEmitter() {
		PointsManager.setPoints(0);
		final LongWrapper log = new LongWrapper();
		Listener listener = event -> log.value = (long)(event.value());
		PointsManager.connect(listener);

		PointsManager.addPoints(50);
		assertEquals(50, log.value);

		try {
			PointsManager.payPoints(40);
		}
		catch (NotEnoughException e) {
			fail();
		}
		assertEquals(10, log.value);
	}

}
