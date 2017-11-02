package fr.jabbytechs.lsp4j.wls.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

import fr.jabbytechs.lsp4j.wls.model.Attribute;
import fr.jabbytechs.lsp4j.wls.model.DocumentLine;
import fr.jabbytechs.lsp4j.wls.model.Tuple;
import fr.jabbytechs.lsp4j.wls.model.WineDescription;
import fr.jabbytechs.lsp4j.wls.model.WineDocumentModel;

public class WineValidator {

	public static List<Diagnostic> validate(WineDocumentModel model) {
		List<Diagnostic> diagnostics = new ArrayList<>();

		diagnostics.addAll(missingAttributes(model));
		// diagnostics.addAll(validateDocument(model));
		return diagnostics;
	}

	private static Collection<? extends Diagnostic> validateDocument(WineDocumentModel model) {

		List<Diagnostic> diagnostics = new ArrayList<>();

		String designation = model.getDesignation();

		Optional<WineDescription> first = WineCompletionProcessor.wineDescriptions.stream()
				.filter(w -> w.getDesignation().equals(designation)).findFirst();

		if (first.isPresent()) {
			WineDescription wineDescription = first.get();

			if (model.getTuple(Attribute.COLOR) != null
					&& !wineDescription.getColors().contains(model.getValue(Attribute.COLOR))) {

				Tuple tuple = model.getTuple(Attribute.COLOR);

				diagnostics.add(new Diagnostic(
						new Range(new Position(tuple.line, tuple.text.indexOf('=')),
								new Position(tuple.line, tuple.text.indexOf('=') + tuple.text.length())),
						"Wrong value for attribute \"color\". The value should be in the following proposals : "
								+ wineDescription.getColors().stream().collect(Collectors.joining(", "))));
			}
		}
		return diagnostics;
	}

	private static List<Diagnostic> missingAttributes(WineDocumentModel model) {

		List<Diagnostic> diagnostics = new ArrayList<>();

		for (Attribute attribute : Attribute.values()) {

			boolean found = false;

			for (DocumentLine line : model.getResolvedLines()) {
				if (line instanceof Tuple) {
					Tuple tuple = (Tuple) line;

					if (tuple.key.toLowerCase().equals(attribute.name().toLowerCase())) {
						found = true;
						if (tuple.value == null) {
							diagnostics.add(new Diagnostic(
									new Range(new Position(tuple.line, tuple.charOffset),
											new Position(tuple.line, tuple.charOffset + 1)),
									"Missing value for attribute \"" + attribute.name() + "\""));
						} else {

						}
					}
				}
			}

			if (!found) {
				diagnostics.add(new Diagnostic(new Range(new Position(0, 0), new Position(0, 0)),
						"Missing attribute : " + attribute.name()));
			}
		}
		return diagnostics;
	}

}
