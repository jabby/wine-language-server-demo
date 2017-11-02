package fr.jabbytechs.lsp4j.wls.model;

public abstract class DocumentLine {
	public final int line;
	public final String text;
	public final int charOffset;

	protected DocumentLine(final int line, final int charOffset, final String text) {
		this.line = line;
		this.charOffset = charOffset;
		this.text = text;
	}
}