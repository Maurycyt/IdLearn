package mimuw.idlearn.scoring;

import mimuw.idlearn.userdata.DataManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PointsGiverTest {
	@Test
	public void testPointsGiver() throws IOException, InterruptedException {

		PointsGiver.setSolutionSpeed("a", 10, 0, 10);
		TimeUnit.MILLISECONDS.sleep(20);

		long points1 = DataManager.showPoints();
		assertTrue(points1 > 0);

		PointsGiver.setSolutionSpeed("a", 1, 1, 10);
		TimeUnit.MILLISECONDS.sleep(20);

		long points2 = DataManager.showPoints();
		assertTrue(points2 > 5 * points1);

		PointsGiver.resetSolutions();
		DataManager.setPoints(0);

		PointsGiver.setSolutionSpeed("a", 10, 0, 10);
		TimeUnit.MILLISECONDS.sleep(20);

		PointsGiver.setSolutionSpeed("b", 1, 1, 10);
		TimeUnit.MILLISECONDS.sleep(20);

		long points3 = DataManager.showPoints();

		// assertTrue(points3 > points2); FIXME add test for score overwriting which is less tight.

		PointsGiver.resetSolutions();
		DataManager.resetData();
	}

	@Test
	public void testTimeStamps() throws IOException, InterruptedException {

		PointsGiver.setSolutionSpeed("a", 1, 1, 10);
		PointsGiver.setSolutionSpeed("a", 1000, 0, 10);

		long points0 = DataManager.showPoints();

		TimeUnit.MILLISECONDS.sleep(20);

		assertTrue(DataManager.showPoints() - points0 > 10);

		PointsGiver.resetSolutions();
		DataManager.setPoints(0);

		PointsGiver.setSolutionSpeed("a", 1, 0, 10);
		PointsGiver.setSolutionSpeed("a", 1000, 1, 10);

		points0 = DataManager.showPoints();

		TimeUnit.MILLISECONDS.sleep(20);

		assertTrue(DataManager.showPoints() - points0 < 5);

		PointsGiver.resetSolutions();
		DataManager.resetData();
	}

	@Test
	public void testLoadSpeeds() throws IOException, InterruptedException {
		PointsGiver.setSolutionSpeed("a", 1, 10);
		PointsGiver.resetSolutions();

		DataManager.init();
		long points0 = DataManager.showPoints();
		PointsGiver.loadSpeeds();

		TimeUnit.MILLISECONDS.sleep(20);

		long points = DataManager.showPoints();
		assertTrue(points >= points0 + 150);

	}
}

