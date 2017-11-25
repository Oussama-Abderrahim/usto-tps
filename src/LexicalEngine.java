import token.IdToken;
import token.Token;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by ${} on 18/11/2017.
 */
public class LexicalEngine
{
    private final String[] keywords = {
            "Start_Program",
            "Int_Number",
            "Real_Number",
            "Give",
            "Affect",
            "to",
            "If",
            "--",
            "Else",
            "Start",
            "Finish",
            ";;"
    };

    private final String[] symbols = {
            ":",
            ".",
            ","
    };
    private String sourceCode;
    private ArrayList<Token> tokenSource;

    private int positionTeteLecture = 0;

    public LexicalEngine()
    {
        sourceCode = "";
        tokenSource = new ArrayList<Token>();
    }

    private char nextChar()
    {
        positionTeteLecture++;
        return currentChar();
    }

    private char currentChar()
    {
        if(positionTeteLecture >= sourceCode.length())
            return '$';
        return sourceCode.charAt(positionTeteLecture);
    }

    public void setSourceCode(String src)
    {
        this.sourceCode = src;
    }
    public ArrayList<Token> getTokenSource()
    {
        performAnalysis();
        return tokenSource;
    }

    private boolean isKeyWord(String word)
    {
        return false;
    }

    private boolean isIdentifier(String word)
    {
        return false;
    }


    private ArrayList<String> reconstructLines(String sourceCode)
    {
        // divise en lignes et ignore les commentaires, if needed
        String[] lines = sourceCode.split("\\n");

        ArrayList<String> linesList = new ArrayList<>();

        for(String line : lines)
            if(!line.startsWith("//."))
                linesList.add(line);

        return linesList;
    }

    private void performAnalysis()
    {
        System.out.println("Performing lexical analysis");

        char c = currentChar();

        System.out.println("Found " + tokenSource.size() + " tokens");
    }
}
