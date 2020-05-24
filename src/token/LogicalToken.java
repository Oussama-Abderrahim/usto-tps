package token;


public class LogicalToken extends Token
{
    public static final LogicalToken NOT = new LogicalToken("!", 0);

    public LogicalToken(String text, int line) {
        super(text, line, TokenType.LOGICAL);
    }
}
