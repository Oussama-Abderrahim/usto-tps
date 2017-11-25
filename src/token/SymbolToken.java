package token;

/**
 * Created by ${} on 18/11/2017.
 */
public class SymbolToken extends Token{
    public static final SymbolToken COLUMN = new SymbolToken(":");
    public static final SymbolToken COMMA = new SymbolToken(",");
    public static final SymbolToken DOUBLE_DASH = new SymbolToken("--");

    public SymbolToken(String text) {
        super(text, TokenType.SYMBOL);
    }
}
