package token;

/**
 * Created by ${} on 18/11/2017.
 */
public class SymbolToken extends Token{
    public static final SymbolToken COLUMN = new SymbolToken(":", 0);
    public static final SymbolToken COMMA = new SymbolToken(",", 0);
    public static final SymbolToken DOUBLE_DASH = new SymbolToken("--", 0);

    public SymbolToken(String text, int line) {
        super(text, line, TokenType.SYMBOL);
    }
}
