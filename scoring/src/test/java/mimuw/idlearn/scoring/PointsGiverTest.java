package mimuw.idlearn.scoring;

import mimuw.idlearn.userdata.DataManager;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class PointsGiverTest {
	@Test
	public void testPointsGiver() {
		PointsGiver.resetSolutions();
		DataManager.setPoints(0);

		PointsGiver.setSolutionSpeed("a", 10, 0);
		try {
			TimeUnit.MILLISECONDS.sleep(20);
		}
		catch (InterruptedException e) {
			fail();
		}
		long points1 = DataManager.showPoints();
		assertTrue(points1 > 0);

		PointsGiver.setSolutionSpeed("a", 1, 1);
		try {
			TimeUnit.MILLISECONDS.sleep(20);
		}
		catch (InterruptedException e) {
			fail();
		}

		long points2 = DataManager.showPoints();
		assertTrue(points2 > 5 * points1);

		PointsGiver.resetSolutions();
		DataManager.setPoints(0);

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

		long points3 = DataManager.showPoints();

		assertTrue(points3 > points2);

		PointsGiver.resetSolutions();
		DataManager.setPoints(0);
	}

	@Test
	public void testTimeStamps() {
		PointsGiver.resetSolutions();
		DataManager.setPoints(0);

		PointsGiver.setSolutionSpeed("a", 1, 1);
		PointsGiver.setSolutionSpeed("a", 1000, 0);

		try {
			TimeUnit.MILLISECONDS.sleep(20);
		}
		catch (InterruptedException e) {
			fail();
		}

		assertTrue(DataManager.showPoints() > 10);

		PointsGiver.resetSolutions();
		DataManager.setPoints(0);

		PointsGiver.setSolutionSpeed("a", 1, 0);
		PointsGiver.setSolutionSpeed("a", 1000, 1);

		try {
			TimeUnit.MILLISECONDS.sleep(20);
		}
		catch (InterruptedException e) {
			fail();
		}

		assertTrue(DataManager.showPoints() < 5);

		PointsGiver.resetSolutions();
		DataManager.setPoints(0);
	}
}
