package token;

/**
 * Created by ${} on 24/11/2017.
 */
public class VarTypeToken extends Token
{
    public static final VarTypeToken INT_NUMBER = new VarTypeToken("Int_Number", 0);
    public static final VarTypeToken REAL_NUMBER = new VarTypeToken("Real_Number", 0);

    public VarTypeToken(String text, int line)
    {
        super(text, line, TokenType.TYPE);
    }
}
