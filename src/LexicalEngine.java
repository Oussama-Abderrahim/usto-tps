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
    private String sourceCode;
    private ArrayList<Token> tokenSource;

    public LexicalEngine()
    {
        sourceCode = "";
        tokenSource = new ArrayList<Token>();
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

        Pattern stringPattern = Pattern.compile("\".*\"");
        Pattern idPattern = Pattern.compile("([a-z]|[A-Z])([a-z]|[A-Z]|[0-9])*");

        ArrayList<String> lines = reconstructLines(sourceCode);

        for(String line : lines)
        {
            // traiter chaque ligne lettre par lettre
        }

        System.out.println("Found " + tokenSource.size() + " tokens");
    }
}
