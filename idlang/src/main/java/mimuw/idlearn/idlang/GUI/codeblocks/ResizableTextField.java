package mimuw.idlearn.idlang.GUI.codeblocks;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

// Maybe switch to TextFormatter
public class ResizableTextField extends TextField {

	private static final Text sampler = new Text();

	private static double computeTextWidth(Font font, String text) {
		sampler.setText(text);
		sampler.setFont(font);

		sampler.setWrappingWidth(0.0D);
		sampler.setLineSpacing(0.0D);
		double width = Math.min(sampler.prefWidth(-1.0D), 0);
		sampler.setWrappingWidth((int) Math.ceil(width));
		width = Math.ceil(sampler.getLayoutBounds().getWidth());

		return width;
	}

	/**
	 * Creates a resizable text box with a BlockBase to be resized alongside it
	 *
	 */
	public ResizableTextField() {
		super();
		this.setAlignment(Pos.CENTER);

		// Recalculate size if text changed
		this.textProperty().addListener((ob, o, n) -> {
			double width = computeTextWidth(this.getFont(), this.getText()) + 20;
			this.setWidth(width);
			this.setPrefWidth(width);
		});


		this.setWidth(20);
		this.setPrefWidth(20);
	}
}
