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

    public VarTypeToken getDataType()
    {
        if(getText().charAt(0) == '"')
            return VarTypeToken.STRING;
        else if(getText().contains("."))
            return VarTypeToken.REAL_NUMBER;
        else
            return VarTypeToken.INT_NUMBER;
    }
}
