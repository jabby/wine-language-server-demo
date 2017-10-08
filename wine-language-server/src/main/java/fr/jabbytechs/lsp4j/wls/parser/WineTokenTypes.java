package fr.jabbytechs.lsp4j.wls.parser;

/**
 */
public class WineTokenTypes {

    public static final byte WINE_CURLY_BRACKET_LEFT   = 1;   // {
    public static final byte WINE_CURLY_BRACKET_RIGHT  = 2;   // }
    public static final byte WINE_SQUARE_BRACKET_LEFT  = 3;   // [
    public static final byte WINE_SQUARE_BRACKET_RIGHT = 4;   // ]
    public static final byte WINE_STRING_TOKEN         = 5;   //
    public static final byte WINE_STRING_ENC_TOKEN     = 6;   //
    public static final byte WINE_NUMBER_TOKEN         = 7;   //
    public static final byte WINE_COLOR_TOKEN        = 8;   //
    public static final byte WINE_NULL_TOKEN           = 9;   // the , between properties
    public static final byte WINE_COLON                = 10;  // :
    public static final byte WINE_COMMA                = 11;  // the , between properties


}
