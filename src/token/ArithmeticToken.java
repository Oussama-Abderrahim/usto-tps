package token;


public class ArithmeticToken extends Token
{
    public ArithmeticToken(String text)
    {
        super(text, TokenType.ARITHMETIC);
    }
}
