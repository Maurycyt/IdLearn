package mimuw.idlearn.GUI.coding.codeblock;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResizableTextFieldTest {

	private static void preparePlatform() {
		try {
			Platform.startup(() -> {
			});
		} catch (IllegalStateException e) {
			// Toolkit already initialized
		}
	}

	@Test
	public void testCompile() {
		preparePlatform();
		new ResizableTextField(null);

		BlockBase base = new BlockBase(50, Color.AQUA);
		new ResizableTextField(base);
	}

	@Test
	public void testSize() {
		preparePlatform();
		ResizableTextField rtf1 = new ResizableTextField(null);
		double size0 = rtf1.getWidth();

		assertTrue(size0 > 0);

		rtf1.setText("IdLang");
		double size1 = rtf1.getWidth();

		Text test = new Text("IdLang");
		double sizeTest = test.getLayoutBounds().getWidth();

		assertEquals(size1, size0 + sizeTest);


		BlockBase base = new BlockBase(50, Color.AQUA);
		ResizableTextField rtf2 = new ResizableTextField(base);
		base.addChild(rtf2);

		size0 = rtf2.getWidth();
		double size0Base = base.update();

		assertTrue(size0 > 0);
		assertTrue(size0Base > 0);

		rtf2.setText("IdLang");
		size1 = rtf2.getWidth();
		double size1Base = base.update();

		test = new Text("IdLang");
		sizeTest = test.getLayoutBounds().getWidth();

		assertEquals(size1, size0 + sizeTest);
		assertEquals(size1Base, size0Base + sizeTest);
	}
}