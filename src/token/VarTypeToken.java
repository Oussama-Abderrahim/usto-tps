package token;

/**
 * Created by ${} on 24/11/2017.
 */
public class VarTypeToken extends Token
{
    public static final VarTypeToken INT_NUMBER = new VarTypeToken("Int_Number");
    public static final VarTypeToken REAL_NUMBER = new VarTypeToken("Real_Number");

    public VarTypeToken(String text)
    {
        super(text, TokenType.TYPE);
    }
}
