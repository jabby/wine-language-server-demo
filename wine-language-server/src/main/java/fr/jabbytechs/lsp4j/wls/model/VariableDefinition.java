package fr.jabbytechs.lsp4j.wls.model;

public class VariableDefinition extends DocumentLine {
	public final String variableName;
	public final String variableValue;

	public VariableDefinition(int lineNumber, int charOffset, String text, String variableName, String value) {
		super(lineNumber, charOffset, text);
		this.variableName = variableName;
		variableValue = value;
	}

}
