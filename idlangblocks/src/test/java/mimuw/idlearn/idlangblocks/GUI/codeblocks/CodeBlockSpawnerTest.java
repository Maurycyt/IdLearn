package mimuw.idlearn.idlangblocks.GUI.codeblocks;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import mimuw.idlearn.idlangblocks.GUI.CodeBox;
import mimuw.idlearn.idlangblocks.GUI.codeblocks.blocktypes.Assign;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeBlockSpawnerTest {

	private static void preparePlatform() {
		try {
			Platform.startup(() -> {
			});
		} catch (IllegalStateException e) {
			// Toolkit already initialized
		}

	}

	@Test
	public void testSpawning() {
		preparePlatform();
		CodeBox codeBox = new CodeBox();
		Pane dragged = new AnchorPane();
		CodeBlockSpawner spawner = new CodeBlockSpawner(codeBox, Assign::new, dragged);

		assertEquals(1, spawner.getChildren().size());

		spawner.spawnBlock();

		assertEquals(2, spawner.getChildren().size());

	}
}
