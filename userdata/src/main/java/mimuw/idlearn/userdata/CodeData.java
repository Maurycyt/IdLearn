package mimuw.idlearn.userdata;

import java.util.ArrayList;

public class CodeData {
	public final ArrayList<CodeData> children = new ArrayList<>();
	public BlockType type;
	public final ArrayList<String> texts = new ArrayList<>();
}
