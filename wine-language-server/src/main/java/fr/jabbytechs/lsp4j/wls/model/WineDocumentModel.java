package fr.jabbytechs.lsp4j.wls.model;

import org.eclipse.lsp4j.TextDocumentPositionParams;

import com.jenkov.parsers.core.IndexBuffer;

import fr.jabbytechs.lsp4j.wls.parser.WineNavigator;
import fr.jabbytechs.lsp4j.wls.parser.WineParser;
import fr.jabbytechs.lsp4j.wls.parser.core.DataCharBuffer;

public class WineDocumentModel {

	private final WineNavigator navigator;
	private final WineParser parser;

	public WineDocumentModel(String text) {

		DataCharBuffer dataBuffer = new DataCharBuffer();
		dataBuffer.data = text.toCharArray();
		dataBuffer.length = dataBuffer.data.length;

		IndexBuffer tokenBuffer = new IndexBuffer(dataBuffer.data.length, true);
		IndexBuffer elementBuffer = new IndexBuffer(dataBuffer.data.length, true);

		parser = new WineParser(tokenBuffer, elementBuffer);
		navigator = new WineNavigator(dataBuffer, elementBuffer);
	}

	public WineNavigator getNavigator() {
		return navigator;
	}

	public WineParser getParser() {
		return parser;
	}

	public Attribute getAttribute(TextDocumentPositionParams position) {

		Attribute attribute = null;

		boolean found = false;
		while (navigator.hasNext() && !found) {

			navigator.next();
		}

		return attribute;
	}

	public String getDesignation(TextDocumentPositionParams position) {
		// TODO Auto-generated method stub
		return null;
	}

}
