package mimuw.idlearn.userdata;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import mimuw.idlearn.core.Listener;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManagerTest {
	@Test
	public void testBasicPoints() {
		DataManager.setPoints(0);
		assertEquals(0, DataManager.showPoints());
		DataManager.addPoints(100);
		assertEquals(100, DataManager.showPoints());
		try {
			DataManager.payPoints(60);
		}
		catch (NotEnoughPointsException e) {
			fail();
		}
		assertEquals(40, DataManager.showPoints());
	}

	@Test
	public void testBasicTasks() throws IOException {
		DataManager.setPoints(0);
		DataManager.resetUnlockedTasks();
		assertEquals(new ArrayList<>(), DataManager.getUnlockedTasks());
		DataManager.unlockTask("A");
		assertEquals(new ArrayList<>(List.of(new String[]{"A"})), DataManager.getUnlockedTasks());
		DataManager.resetUnlockedTasks();
		assertEquals(new ArrayList<>(), DataManager.getUnlockedTasks());
	}

	@Test
	public void testNotEnoughException() {
		DataManager.setPoints(0);
		try {
			DataManager.payPoints(10);
			fail();
		}
		catch (NotEnoughPointsException e) {
		}
		assertEquals(0, DataManager.showPoints());
	}

	private static class LongWrapper {
		public long value = 0;
	}

	@Test
	public void testPointsEmitter() {
		DataManager.setPoints(0);
		final LongWrapper log = new LongWrapper();
		Listener listener = event -> log.value = (long)(event.value());
		DataManager.connectToPoints(listener);

		DataManager.addPoints(50);
		assertEquals(50, log.value);

		try {
			DataManager.payPoints(40);
		}
		catch (NotEnoughPointsException e) {
			fail();
		}
		assertEquals(10, log.value);
	}

	@Test
	public void testLoad() throws IOException {
		DataManager.setPoints(0);
		DataManager.resetUnlockedTasks();
		DataManager.init();
		assertEquals(0, DataManager.showPoints());

		DataManager.addPoints(100);
		DataManager.unlockTask("A");

		DataManager.init();
		assertEquals(100, DataManager.showPoints());
		assertEquals("A", DataManager.getUnlockedTasks().get(0));

		DataManager.setPoints(0);
		DataManager.init();
		assertEquals(100, DataManager.showPoints());
	}

}
