package mimuw.idlearn.idlangGui.codeblock;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlockBaseTest {
	@Test
	public void testCompilation() {
		BlockBase base = new BlockBase(50, Color.AQUA);
		Text t = new Text("IdLang");
		base.addChild(t);
		base.update();
	}

	@Test
	public void testSize() {
		BlockBase base = new BlockBase(50, Color.AQUA);
		Text t = new Text("IdLang");

		double size0 = base.update();

		base.addChild(t);

		double size1 = base.update();

		assertEquals(size0 + t.getLayoutBounds().getWidth(), size1);

		t.setText("IdLang is very cool");
		double size2 = base.update();

		assertTrue(size2 > size1);
	}
}
