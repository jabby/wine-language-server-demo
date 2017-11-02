package fr.jabbytechs.lsp4j.wls.server;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.MarkedString;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

import fr.jabbytechs.lsp4j.wls.model.WineDocumentModel;

public class WineHoverProcessor {

	public static List<Either<String, MarkedString>> process(WineDocumentModel document,
			TextDocumentPositionParams position) {

		return document.getResolvedAttributes().stream().filter(line -> line.line == position.getPosition().getLine())
				.map(line -> line.key).map(WineHoverProcessor::getDefinition).map(WineHoverProcessor::getHoverContent)
				.collect(Collectors.toList());
	}

	public static String getDefinition(String key) {
		switch (key) {
		case "name":
			return "The name of your wine.";
		case "designation":
			return "The designation of your wine.";
		case "location":
			return "The location where your wine is made.";
		case "ground":
			return "The type of ground.";
		case "color":
			return "The color of your wine.";
		case "grapeVariety":
			return "The grape variety for your wine.";
		default:
			return null;
		}
	}

	public static Either<String, MarkedString> getHoverContent(String type) {
		return Either.forLeft(type);
	}

}
