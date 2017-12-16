package token;

/**
 * Created by ${} on 18/11/2017.
 */
public class IdToken extends Token {

    public IdToken(String text, int line)
    {
        super(text, line, TokenType.ID);
    }

}
