package fr.jabbytechs.lsp4j.wls.model;

public class Tuple extends DocumentLine {
	public final String key;
	public final String value;

	public Tuple(int line, int charOffset, String text, String key, String value) {
		super(line, charOffset, text);
		this.key = key;
		this.value = value;
	}

	public Tuple(int line, int charOffset, String text, String key) {
		super(line, charOffset, text);
		this.key = key;
		this.value = null;
	}
}