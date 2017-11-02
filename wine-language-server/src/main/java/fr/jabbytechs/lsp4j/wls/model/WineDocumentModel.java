package fr.jabbytechs.lsp4j.wls.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.lsp4j.TextDocumentPositionParams;

public class WineDocumentModel {

	private final List<DocumentLine> lines = new ArrayList<>();
	private final List<Tuple> attributes = new ArrayList<>();
	private final Map<String, VariableDefinition> variables = new HashMap<>();

	public WineDocumentModel(String text) {
		try (Reader r = new StringReader(text); BufferedReader reader = new BufferedReader(r);) {
			String lineText;
			int lineNumber = 0;
			while ((lineText = reader.readLine()) != null) {
				DocumentLine line = null;

				// FIXME add comment support

				line = parseLine(lineNumber, lineText);
				if (line != null) {
					lines.add(line);
				}
				lineNumber++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private DocumentLine parseLine(int lineNumber, String lineText) {
		DocumentLine tuple = null;

		String[] segments = lineText.split("=");
		if (segments.length == 2) {
			tuple = new Tuple(lineNumber, 0, lineText, segments[0], segments[1]);
			attributes.add((Tuple) tuple);
		} else if (segments.length == 1) {
			tuple = new Tuple(lineNumber, 0, lineText, segments[0]);
			attributes.add((Tuple) tuple);
		}
		return tuple;
	}

	public List<Tuple> getResolvedAttributes() {
		return Collections.unmodifiableList(this.attributes);
	}

	public Tuple getAttribute(int line) {
		for (Tuple attribute : getResolvedAttributes()) {
			if (attribute.line == line) {
				return attribute;
			}
		}
		return null;
	}

	public int getDefinitionLine(String variable) {
		if (this.variables.containsKey(variable)) {
			return this.variables.get(variable).line;
		}
		return -1;
	}

	public List<DocumentLine> getResolvedLines() {
		return Collections.unmodifiableList(this.lines);
	}

	public Attribute getTypeAttribute(TextDocumentPositionParams position) {
		if (lines.size() > position.getPosition().getLine()) {
			DocumentLine line = lines.get(position.getPosition().getLine());
			for (Attribute attribute : Attribute.values()) {
				if (line instanceof Tuple) {
					Tuple tuple = (Tuple) line;

					if (tuple.key.toLowerCase().equals(attribute.name().toLowerCase())) {
						return attribute;
					}
				}
			}
		}
		return null;
	}

	public String getDesignation() {
		return getValue(Attribute.DESIGNATION);
	}

	public String getValue(Attribute attribute) {
		for (DocumentLine line : lines) {
			if (line instanceof Tuple) {
				Tuple tuple = (Tuple) line;

				if (tuple.key.toLowerCase().equals(attribute.name().toLowerCase())) {
					return tuple.value;
				}
			}
		}
		return null;
	}

	public Tuple getTuple(Attribute attribute) {
		for (DocumentLine line : lines) {
			if (line instanceof Tuple) {
				Tuple tuple = (Tuple) line;

				if (tuple.key.toLowerCase().equals(attribute.name().toLowerCase())) {
					return tuple;
				}
			}
		}
		return null;
	}

}
