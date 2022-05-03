package mimuw.idlearn.scoring;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class PointsGiverTest {
	@Test
	public void testPointsGiver() {
		PointsManager.setPoints(0);
		PointsGiver.setSolutionSpeed("a", 0.01);
		try {
			TimeUnit.MILLISECONDS.sleep(20);
		}
		catch (InterruptedException e) {
			fail();
		}
		long points1 = PointsManager.showPoints();
		assertTrue(points1 > 0);

		PointsGiver.setSolutionSpeed("a", 0.001);
		try {
			TimeUnit.MILLISECONDS.sleep(20);
		}
		catch (InterruptedException e) {
			fail();
		}

		long points2 = PointsManager.showPoints();
		assertTrue(points2 > 5 * points1);
	}
}
