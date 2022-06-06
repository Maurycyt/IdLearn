package mimuw.idlearn.idlangblocks.GUI.codeblocks;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

// Maybe switch to TextFormatter
public class ResizableTextField extends TextField {

	private static final double BASE_WIDTH = 20;

	private static final Text sampler = new Text();

	public static double computeTextWidth(Font font, String text) {
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
			double width = computeTextWidth(this.getFont(), this.getText()) + BASE_WIDTH;
			this.setPrefWidth(width);
			this.setMinWidth(width);
			this.setMaxWidth(width);
			this.setWidth(width);
		});

		this.setPrefWidth(BASE_WIDTH);
		this.setMinWidth(BASE_WIDTH);
		this.setMaxWidth(BASE_WIDTH);
		this.setWidth(BASE_WIDTH);
	}
}
