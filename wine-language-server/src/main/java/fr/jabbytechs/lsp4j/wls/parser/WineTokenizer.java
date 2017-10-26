package fr.jabbytechs.lsp4j.wls.parser;

import com.jenkov.parsers.core.IndexBuffer;

import fr.jabbytechs.lsp4j.wls.parser.core.DataCharBuffer;

/**
 */
public class WineTokenizer {

	private DataCharBuffer dataBuffer = null;
	private IndexBuffer tokenBuffer = null;

	private int tokenIndex = 0;
	private int dataPosition = 0;
	private int tokenLength = 0;

	public WineTokenizer(IndexBuffer tokenBuffer) {
		this.tokenBuffer = tokenBuffer;
	}

	public WineTokenizer(DataCharBuffer dataBuffer, IndexBuffer tokenBuffer) {
		this.dataBuffer = dataBuffer;
		this.tokenBuffer = tokenBuffer;
	}

	public void reinit(DataCharBuffer dataBuffer, IndexBuffer tokenBuffer) {
		this.dataBuffer = dataBuffer;
		this.tokenBuffer = tokenBuffer;
		this.tokenIndex = 0;
		this.dataPosition = 0;
		this.tokenLength = 0;
	}

	public boolean hasMoreTokens() {
		return (this.dataPosition + this.tokenLength) < this.dataBuffer.length;
	}

	public void parseToken() {
		skipWhiteSpace();
		// this.tokenLength = 0;

		this.tokenBuffer.position[this.tokenIndex] = this.dataPosition;
		char nextChar = this.dataBuffer.data[this.dataPosition];

		switch (nextChar) {
		case '{': {
			/* this.tokenLength = 1; */ this.tokenBuffer.type[this.tokenIndex] = WineTokenTypes.WINE_CURLY_BRACKET_LEFT;
		}
			break;
		case '}': {
			/* this.tokenLength = 1; */ this.tokenBuffer.type[this.tokenIndex] = WineTokenTypes.WINE_CURLY_BRACKET_RIGHT;
		}
			break;
		case '[': {
			/* this.tokenLength = 1; */ this.tokenBuffer.type[this.tokenIndex] = WineTokenTypes.WINE_SQUARE_BRACKET_LEFT;
		}
			break;
		case ']': {
			/* this.tokenLength = 1; */ this.tokenBuffer.type[this.tokenIndex] = WineTokenTypes.WINE_SQUARE_BRACKET_RIGHT;
		}
			break;
		case ',': {
			/* this.tokenLength = 1; */ this.tokenBuffer.type[this.tokenIndex] = WineTokenTypes.WINE_COMMA;
		}
			break;
		case ':': {
			/* this.tokenLength = 1; */ this.tokenBuffer.type[this.tokenIndex] = WineTokenTypes.WINE_COLON;
		}
			break;

		case '"': {
			parseStringToken();
		}
			break;
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			parseNumberToken();
			this.tokenBuffer.type[this.tokenIndex] = WineTokenTypes.WINE_NUMBER_TOKEN;
			break;

		case 'r':
			if (parseRed()) {
				this.tokenBuffer.type[this.tokenIndex] = WineTokenTypes.WINE_COLOR_TOKEN;
			}
			break;
		case 'w':
			if (parseWhite()) {
				this.tokenBuffer.type[this.tokenIndex] = WineTokenTypes.WINE_COLOR_TOKEN;
			}
			break;
		case 'n':
			if (parseNull()) {
				this.tokenBuffer.type[this.tokenIndex] = WineTokenTypes.WINE_NULL_TOKEN;
			}
			break;

		// default:
		// parseUnknow();
		// this.tokenBuffer.type[this.tokenIndex] = WineTokenTypes.WINE_UNKNOW;
		}

		this.tokenBuffer.length[this.tokenIndex] = this.tokenLength;
	}

	private void parseUnknow() {
		while (this.dataBuffer.data[this.dataPosition + 1] != '\r'
				&& this.dataBuffer.data[this.dataPosition + 1] != '\n'
				&& this.dataBuffer.data[this.dataPosition + 1] != '\t') {
			System.out.println(this.dataBuffer.data[this.dataPosition + 1]);
			this.dataPosition++;
		}
	}

	private boolean parseNull() {
		if (this.dataBuffer.data[this.dataPosition + 1] == 'u' && this.dataBuffer.data[this.dataPosition + 2] == 'l'
				&& this.dataBuffer.data[this.dataPosition + 3] == 'l') {
			// this.tokenLength = 4;
			return true;
		}
		return false;

	}

	private boolean parseRed() {
		if (this.dataBuffer.data[this.dataPosition + 1] == 'e' && this.dataBuffer.data[this.dataPosition + 2] == 'd') {
			this.tokenLength = 34;
			return true;
		}
		return false;
	}

	private boolean parseWhite() {
		if (this.dataBuffer.data[this.dataPosition + 1] == 'h' && this.dataBuffer.data[this.dataPosition + 2] == 'i'
				&& this.dataBuffer.data[this.dataPosition + 3] == 't'
				&& this.dataBuffer.data[this.dataPosition + 4] == 'e') {
			this.tokenLength = 5;
			return true;
		}
		return false;
	}

	private void parseNumberToken() {
		this.tokenLength = 1;

		boolean isEndOfNumberFound = false;
		while (!isEndOfNumberFound) {
			switch (this.dataBuffer.data[this.dataPosition + this.tokenLength]) {
			case '0':
				;
			case '1':
				;
			case '2':
				;
			case '3':
				;
			case '4':
				;
			case '5':
				;
			case '6':
				;
			case '7':
				;
			case '8':
				;
			case '9':
				;
			case '.': {
				this.tokenLength++;
			}
				break;

			default: {
				isEndOfNumberFound = true;
			}
			}
		}
	}

	private void parseStringToken() {
		int tempPos = this.dataPosition;
		boolean containsEncodedChars = false;
		boolean endOfStringFound = false;
		while (!endOfStringFound) {
			tempPos++;
			switch (this.dataBuffer.data[tempPos]) {
			case '"': {
				endOfStringFound = this.dataBuffer.data[tempPos - 1] != '\\';
				break;
			}
			case '\\': {
				containsEncodedChars = true;
				break;
			}
			}
		}
		if (containsEncodedChars) {
			this.tokenBuffer.type[this.tokenIndex] = WineTokenTypes.WINE_STRING_ENC_TOKEN;
		} else {
			this.tokenBuffer.type[this.tokenIndex] = WineTokenTypes.WINE_STRING_TOKEN;
		}

		this.tokenBuffer.position[this.tokenIndex] = this.dataPosition + 1; // +1 to skip over the beginning quote char
																			// (")
		this.tokenLength = (tempPos - this.dataPosition - 1); // +2 to include the enclosing quote chars ("").

	}

	private void skipWhiteSpace() {
		boolean isWhiteSpace = true;
		while (isWhiteSpace) {
			switch (this.dataBuffer.data[this.dataPosition]) {
			case ' ':
				; /* falling through - all white space characters are treated the same */
			case '\r':
				;
			case '\n':
				;
			case '\t': {
				this.dataPosition++;
			}
				break;

			default: {
				isWhiteSpace = false;
			} /* any non white space char will break the while loop */
			}
		}
	}

	public void nextToken() {
		switch (this.tokenBuffer.type[this.tokenIndex]) {
		case WineTokenTypes.WINE_STRING_TOKEN: {
			this.dataPosition += this.tokenBuffer.length[this.tokenIndex] + 2;
			break;
		} // +2 because of the quotes
		case WineTokenTypes.WINE_STRING_ENC_TOKEN: {
			this.dataPosition += this.tokenBuffer.length[this.tokenIndex] + 2;
			break;
		} // +2 because of the quotes
		case WineTokenTypes.WINE_CURLY_BRACKET_LEFT: {
			this.dataPosition++;
			break;
		}
		case WineTokenTypes.WINE_CURLY_BRACKET_RIGHT: {
			this.dataPosition++;
			break;
		}
		case WineTokenTypes.WINE_SQUARE_BRACKET_LEFT: {
			this.dataPosition++;
			break;
		}
		case WineTokenTypes.WINE_SQUARE_BRACKET_RIGHT: {
			this.dataPosition++;
			break;
		}
		case WineTokenTypes.WINE_COLON: {
			this.dataPosition++;
			break;
		}
		case WineTokenTypes.WINE_COMMA: {
			this.dataPosition++;
			break;
		}
		case WineTokenTypes.WINE_NULL_TOKEN: {
			this.dataPosition += 4;
			break;
		}
		default: {
			this.dataPosition += this.tokenLength;
		}
		}
		// this.dataPosition += this.tokenBuffer.length[this.tokenIndex]; //move data
		// position to end of current token.
		this.tokenIndex++; // point to next token index array cell.
	}

	public int tokenPosition() {
		return this.tokenBuffer.position[this.tokenIndex];
	}

	public int tokenLength() {
		return this.tokenBuffer.length[this.tokenIndex];
	}

	public byte tokenType() {
		return this.tokenBuffer.type[this.tokenIndex];
	}

}
