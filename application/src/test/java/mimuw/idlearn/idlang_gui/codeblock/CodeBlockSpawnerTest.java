package mimuw.idlearn.idlang_gui.codeblock;

import javafx.application.Platform;
import javafx.scene.Group;
import mimuw.idlearn.idlang_gui.CodeBox;
import mimuw.idlearn.idlang_gui.codeblock.CodeBlockSpawner;
import mimuw.idlearn.idlang_gui.codeblock.blocktypes.Assign;
import org.junit.jupiter.api.BeforeAll;
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
		Group dragged = new Group();
		CodeBlockSpawner spawner = new CodeBlockSpawner(codeBox, dragged, Assign::new);

		assertEquals(1, spawner.getChildren().size());

		spawner.spawnBlock();

		assertEquals(2, spawner.getChildren().size());

	}
}
