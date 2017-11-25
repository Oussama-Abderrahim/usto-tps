package token;


public class LogicalToken extends Token
{
    public static final LogicalToken NOT = new LogicalToken("!");

    public LogicalToken(String text) {
        super(text, TokenType.LOGICAL);
    }
}
