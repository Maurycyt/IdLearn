package mimuw.idlearn.idlang.GUI.codeblocks;

import javafx.application.Platform;
import javafx.scene.Group;
import mimuw.idlearn.idlang.GUI.CodeBox;
import mimuw.idlearn.idlang.GUI.codeblocks.CodeBlockSpawner;
import mimuw.idlearn.idlang.GUI.codeblocks.blocktypes.Assign;
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
		CodeBlockSpawner spawner = new CodeBlockSpawner(codeBox, Assign::new);

		assertEquals(1, spawner.getChildren().size());

		spawner.spawnBlock();

		assertEquals(2, spawner.getChildren().size());

	}
}
