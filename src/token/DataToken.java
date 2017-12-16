package token;

/**
 * Created by ${} on 18/11/2017.
 */
public class DataToken extends Token
{
    public DataToken(String text, int line)
    {
        super(text, line, TokenType.DATA);
    }
}
