package mimuw.idlearn.idlang_gui.codeblock;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

// Maybe switch to TextFormatter
public class ResizableTextField extends TextField {
	/**
	 * Creates a resizable text box with a BlockBase to be resized alongside it
	 *
	 * @param base The BlockBase to be resized alongside us (can be null)
	 */
	public ResizableTextField(BlockBase base) {
		super();
		this.setAlignment(Pos.CENTER);


		// Recalculate size if text changed
		this.textProperty().addListener((ob, o, n) -> {
			Text sample = new Text(this.getText());
			this.setWidth(sample.getLayoutBounds().getWidth() + 20);
			this.setPrefWidth(sample.getLayoutBounds().getWidth() + 20);
			if (base != null) {
				base.update();
			}
		});


		this.setWidth(20);
		this.setPrefWidth(20);
	}
}
