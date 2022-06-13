package mimuw.idlearn.idlangblocks.GUI.codeblocks;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
		new ResizableTextField();

		BlockBase base = new BlockBase(50, Color.AQUA);
		new ResizableTextField();
	}

	@Test
	public void testSize() {
		preparePlatform();
		ResizableTextField rtf1 = new ResizableTextField();
		double size0 = rtf1.getWidth();

		assertTrue(size0 > 0);

		rtf1.setText("IdLang");
		double size1 = rtf1.getWidth();


		double sizeTest = ResizableTextField.computeTextWidth(rtf1.getFont(), "IdLang");

		assertEquals(size1, size0 + sizeTest);


		BlockBase base = new BlockBase(50, Color.AQUA);
		ResizableTextField rtf2 = new ResizableTextField();
		base.addChild(rtf2);

		size0 = rtf2.getWidth();
		double size0Base = base.update();

		assertTrue(size0 > 0);
		assertTrue(size0Base > 0);

		rtf2.setText("IdLang");
		rtf2.autosize();
		size1 = rtf2.getWidth();
		double size1Base = base.update();

		sizeTest = ResizableTextField.computeTextWidth(rtf2.getFont(), "IdLang");

		assertEquals(size1, size0 + sizeTest);
		assertEquals(size1Base, size0Base + sizeTest);
	}
}