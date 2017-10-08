package fr.jabbytechs.lsp4j.wls.parser;

import com.jenkov.parsers.core.IndexBuffer;

import fr.jabbytechs.lsp4j.wls.parser.core.DataCharBuffer;
import fr.jabbytechs.lsp4j.wls.parser.core.ParserException;

/**
 *
 */
public class WineParser {

	private IndexBuffer tokenBuffer = null;
	private IndexBuffer elementBuffer = null;
	private int elementIndex = 0;
	private WineTokenizer wineTokenizer = null;

	public WineParser(IndexBuffer tokenBuffer, IndexBuffer elementBuffer) {
		this.tokenBuffer = tokenBuffer;
		this.wineTokenizer = new WineTokenizer(this.tokenBuffer);
		this.elementBuffer = elementBuffer;
	}

	public void parse(DataCharBuffer dataBuffer) {
		this.elementIndex = 0;

		this.wineTokenizer.reinit(dataBuffer, this.tokenBuffer);

		parseObject(this.wineTokenizer);

		this.elementBuffer.count = this.elementIndex;
	}

	private void parseObject(WineTokenizer tokenizer) {
		assertHasMoreTokens(tokenizer);
		tokenizer.parseToken();
		assertThisTokenType(tokenizer.tokenType(), WineTokenTypes.WINE_CURLY_BRACKET_LEFT);
		setElementData(tokenizer, WineElementTypes.WINE_OBJECT_START);

		tokenizer.nextToken();
		tokenizer.parseToken();
		byte tokenType = tokenizer.tokenType();

		while (tokenType != WineTokenTypes.WINE_CURLY_BRACKET_RIGHT) {
			assertThisTokenType(tokenType, WineTokenTypes.WINE_STRING_TOKEN);
			setElementData(tokenizer, WineElementTypes.WINE_PROPERTY_NAME);

			tokenizer.nextToken();
			tokenizer.parseToken();
			tokenType = tokenizer.tokenType();
			assertThisTokenType(tokenType, WineTokenTypes.WINE_COLON);

			tokenizer.nextToken();
			tokenizer.parseToken();
			tokenType = tokenizer.tokenType();

			switch (tokenType) {
			case WineTokenTypes.WINE_STRING_TOKEN: {
				setElementData(tokenizer, WineElementTypes.WINE_PROPERTY_VALUE_STRING);
			}
				break;
			case WineTokenTypes.WINE_STRING_ENC_TOKEN: {
				setElementData(tokenizer, WineElementTypes.WINE_PROPERTY_VALUE_STRING_ENC);
			}
				break;
			case WineTokenTypes.WINE_NUMBER_TOKEN: {
				setElementData(tokenizer, WineElementTypes.WINE_PROPERTY_VALUE_NUMBER);
			}
				break;
			case WineTokenTypes.WINE_COLOR_TOKEN: {
				setElementData(tokenizer, WineElementTypes.WINE_PROPERTY_VALUE_BOOLEAN);
			}
				break;
			case WineTokenTypes.WINE_NULL_TOKEN: {
				setElementData(tokenizer, WineElementTypes.WINE_PROPERTY_VALUE_NULL);
			}
				break;
			case WineTokenTypes.WINE_CURLY_BRACKET_LEFT: {
				parseObject(tokenizer);
			}
				break;
			case WineTokenTypes.WINE_SQUARE_BRACKET_LEFT: {
				parseArray(tokenizer);
			}
				break;
			}

			tokenizer.nextToken();
			tokenizer.parseToken();
			tokenType = tokenizer.tokenType();
			if (tokenType == WineTokenTypes.WINE_COMMA) {
				tokenizer.nextToken(); // skip , tokens if found here.
				tokenizer.parseToken();
				tokenType = tokenizer.tokenType();
			}

		}
		setElementData(tokenizer, WineElementTypes.WINE_OBJECT_END);
	}

	private void parseArray(WineTokenizer tokenizer) {
		setElementData(tokenizer, WineElementTypes.WINE_ARRAY_START);

		tokenizer.nextToken();
		tokenizer.parseToken();

		while (tokenizer.tokenType() != WineTokenTypes.WINE_SQUARE_BRACKET_RIGHT) {

			byte tokenType = tokenizer.tokenType(); // extracted only for debug purposes.

			switch (tokenType) {
			case WineTokenTypes.WINE_STRING_TOKEN: {
				setElementData(tokenizer, WineElementTypes.WINE_ARRAY_VALUE_STRING);
			}
				break;
			case WineTokenTypes.WINE_STRING_ENC_TOKEN: {
				setElementData(tokenizer, WineElementTypes.WINE_ARRAY_VALUE_STRING_ENC);
			}
				break;
			case WineTokenTypes.WINE_NUMBER_TOKEN: {
				setElementData(tokenizer, WineElementTypes.WINE_ARRAY_VALUE_NUMBER);
			}
				break;
			case WineTokenTypes.WINE_COLOR_TOKEN: {
				setElementData(tokenizer, WineElementTypes.WINE_ARRAY_VALUE_BOOLEAN);
			}
				break;
			case WineTokenTypes.WINE_NULL_TOKEN: {
				setElementData(tokenizer, WineElementTypes.WINE_ARRAY_VALUE_NULL);
			}
				break;
			case WineTokenTypes.WINE_CURLY_BRACKET_LEFT: {
				parseObject(tokenizer);
			}
				break;
			// todo add arrays in arrays support
			}

			tokenizer.nextToken();
			tokenizer.parseToken();
			tokenType = tokenizer.tokenType();
			if (tokenType == WineTokenTypes.WINE_COMMA) {
				tokenizer.nextToken();
				tokenizer.parseToken();
				tokenType = tokenizer.tokenType();
			}
		}

		setElementData(tokenizer, WineElementTypes.WINE_ARRAY_END);
	}

	private void setElementData(WineTokenizer tokenizer, byte elementType) {
		this.elementBuffer.position[this.elementIndex] = tokenizer.tokenPosition();
		this.elementBuffer.length[this.elementIndex] = tokenizer.tokenLength();
		this.elementBuffer.type[this.elementIndex] = elementType;
		this.elementIndex++;
	}

	private final void assertThisTokenType(byte tokenType, byte expectedTokenType) {
		if (tokenType != expectedTokenType) {
			throw new ParserException("Token type mismatch: Expected " + expectedTokenType + " but found " + tokenType);
		}
	}

	private void assertHasMoreTokens(WineTokenizer tokenizer) {
		if (!tokenizer.hasMoreTokens()) {
			throw new ParserException("Expected more tokens available in the tokenizer");
		}
	}
}
