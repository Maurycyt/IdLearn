package mimuw.idlearn.scoring;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class PointsGiverTest {
	@Test
	public void testPointsGiver() {
		PointsGiver.resetSolutions();
		PointsManager.setPoints(0);

		PointsGiver.setSolutionSpeed("a", 10, 0);
		try {
			TimeUnit.MILLISECONDS.sleep(20);
		}
		catch (InterruptedException e) {
			fail();
		}
		long points1 = PointsManager.showPoints();
		assertTrue(points1 > 0);

		PointsGiver.setSolutionSpeed("a", 1, 1);
		try {
			TimeUnit.MILLISECONDS.sleep(20);
		}
		catch (InterruptedException e) {
			fail();
		}

		long points2 = PointsManager.showPoints();
		assertTrue(points2 > 5 * points1);

		PointsGiver.resetSolutions();
		PointsManager.setPoints(0);

		PointsGiver.setSolutionSpeed("a", 10, 0);
		try {
			TimeUnit.MILLISECONDS.sleep(20);
		}
		catch (InterruptedException e) {
			fail();
		}

		PointsGiver.setSolutionSpeed("b", 1, 1);
		try {
			TimeUnit.MILLISECONDS.sleep(20);
		}
		catch (InterruptedException e) {
			fail();
		}

		long points3 = PointsManager.showPoints();

		assertTrue(points3 > points2);
	}

	@Test
	public void testTimeStamps() {
		PointsGiver.resetSolutions();
		PointsManager.setPoints(0);

		PointsGiver.setSolutionSpeed("a", 1, 1);
		PointsGiver.setSolutionSpeed("a", 1000, 0);

		try {
			TimeUnit.MILLISECONDS.sleep(20);
		}
		catch (InterruptedException e) {
			fail();
		}

		assertTrue(PointsManager.showPoints() > 10);

		PointsGiver.resetSolutions();
		PointsManager.setPoints(0);

		PointsGiver.setSolutionSpeed("a", 1, 0);
		PointsGiver.setSolutionSpeed("a", 1000, 1);

		try {
			TimeUnit.MILLISECONDS.sleep(20);
		}
		catch (InterruptedException e) {
			fail();
		}

		assertTrue(PointsManager.showPoints() < 5);
	}
}
