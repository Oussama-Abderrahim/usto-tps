import token.Token;

import java.util.ArrayList;

/**
 * Created by ${} on 18/11/2017.
 */
public class SyntaxEngine
{
    private ArrayList<Token> tokenSource;
    private ArrayList<String> result;

    public SyntaxEngine()
    {
        tokenSource = null;
    }

    public void setTokenSource(ArrayList<Token> tokenSource)
    {
        this.tokenSource = tokenSource;
    }
    public ArrayList<String> getResult()
    {
        performAnalysis();
        return result;
    }

    private void performAnalysis()
    {

    }
}
