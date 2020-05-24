package token;


public class ArithmeticToken extends Token
{
    public ArithmeticToken(String text, int line)
    {
        super(text, line, TokenType.ARITHMETIC);
    }
}
