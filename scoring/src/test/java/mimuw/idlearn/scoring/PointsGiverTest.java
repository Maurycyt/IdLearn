package mimuw.idlearn.scoring;

import javafx.application.Platform;
import mimuw.idlearn.userdata.DataManager;
import mimuw.idlearn.userdata.PerkManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PointsGiverTest {
	private static void preparePlatform() {
		try {
			Platform.startup(() -> {
			});
		} catch (IllegalStateException e) {
			// Toolkit already initialized
		}
	}

	@Test
	public void testPointsGiver() throws IOException, InterruptedException {

		preparePlatform();
		DataManager.init();
		PerkManager.init();
		PerkManager.setAndApplyPerkLevel("Speed", 0);

		PointsGiver.setSolutionSpeed("a", 10, 0, 10);
		TimeUnit.MILLISECONDS.sleep(100);

		long points1 = DataManager.showPoints();
		assertTrue(points1 > 0);

		PointsGiver.setSolutionSpeed("a", 1, 1, 10);
		TimeUnit.MILLISECONDS.sleep(100);

		long points2 = DataManager.showPoints();
		assertTrue(points2 > 5 * points1); //FIXME add test for score overwriting which is less tight.

		PointsGiver.resetSolutions();
		DataManager.setPoints(0);

		PointsGiver.setSolutionSpeed("a", 10, 0, 10);
		TimeUnit.MILLISECONDS.sleep(100);

		PointsGiver.setSolutionSpeed("b", 1, 1, 10);
		TimeUnit.MILLISECONDS.sleep(100);

		long points3 = DataManager.showPoints();

		assertTrue(points3 > points2); //FIXME add test for score overwriting which is less tight.

		PointsGiver.resetSolutions();
		DataManager.resetData();
	}

	@Test
	public void testTimeStamps() throws IOException, InterruptedException {

		preparePlatform();

		PointsGiver.setSolutionSpeed("a", 1, 1, 10);
		PointsGiver.setSolutionSpeed("a", 1000, 0, 10);

		long points0 = DataManager.showPoints();

		TimeUnit.MILLISECONDS.sleep(200);

		assertTrue(DataManager.showPoints() - points0 > 200);

		PointsGiver.resetSolutions();
		DataManager.setPoints(0);

		PointsGiver.setSolutionSpeed("a", 1, 0, 10);
		PointsGiver.setSolutionSpeed("a", 1000, 1, 10);

		points0 = DataManager.showPoints();

		TimeUnit.MILLISECONDS.sleep(200);

		assertTrue(DataManager.showPoints() - points0 < 100); //FIXME add test for this which is less tight.

		PointsGiver.resetSolutions();
		DataManager.resetData();
	}

	@Test
	public void testLoadSpeeds() throws IOException, InterruptedException {

		preparePlatform();

		PointsGiver.setSolutionSpeed("a", 1, 10);
		PointsGiver.resetSolutions();

		DataManager.init();
		long points0 = DataManager.showPoints();
		PerkManager.init();
		PerkManager.setAndApplyPerkLevel("Speed", 0);
		PointsGiver.loadSpeeds();

		TimeUnit.MILLISECONDS.sleep(200);

		long points = DataManager.showPoints();
		assertTrue(points >= points0 + 1000);

	}
}

