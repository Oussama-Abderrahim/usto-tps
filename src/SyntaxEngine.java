import token.*;

import java.util.ArrayList;



public class SyntaxEngine
{
    private ArrayList<Token> tokenSource;
    private ArrayList<String> result = new ArrayList<>();
    private ArrayList<Token> buffer = new ArrayList<>();

    private int positionTeteLecture = 0;

    public SyntaxEngine()
    {
        tokenSource = null;
    }

    private Token nextToken()
    {
        buffer.add(currentToken());
        positionTeteLecture++;
        return currentToken();
    }

    private Token currentToken()
    {
        if(positionTeteLecture >= tokenSource.size())
            return new SymbolToken("$");

        return tokenSource.get(positionTeteLecture);
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

    private void clear()
    {
        result.clear();
        buffer.clear();
        positionTeteLecture = 0;
    }

    private void performAnalysis()
    {
        clear();
        if(checkStartEnd())
        {
            System.out.println("Reconnu ! ");
        }
        else
        {
            System.out.println("Non reconnue !");
        }
    }

    private boolean checkStartEnd()
    {
        if(currentToken().equals(KeywordToken.START_PROGRAM))
        {
            nextToken();
            validSyntax("Debut programme");
            followedByBlockInstruction();

            if(currentToken().equals(KeywordToken.END_PROGRAM))
            {
                validSyntax("Fin programme correctement");
                return true;
            }
        }
        return false;
    }

    private boolean followedByBlockInstruction()
    {
        Token c = currentToken();

        if( c.equals(KeywordToken.GIVE))
        {
            c = nextToken();
            if(c instanceof IdToken)
            {
                c = nextToken();
                if(c.equals(SymbolToken.COLUMN))
                {
                    c = nextToken();
                    if(c instanceof DataToken)
                    {
                        c = nextToken();
                        if(c.equals(KeywordToken.SEMI_COLON))
                        {
                            c = nextToken();
                            validSyntax("Donner valeur à variable");
                            return followedByBlockInstruction();// next instruction
                        }
                    }
                }
            }
            return logError();
        }
        else if(c.equals(KeywordToken.AFFECT))
        {
            c = nextToken();
            if(c instanceof IdToken)
            {
                c = nextToken();
                if(c.equals(KeywordToken.TO))
                {
                    c = nextToken();
                    if(c instanceof IdToken)
                    {
                        c = nextToken();
                        if(c.equals(KeywordToken.SEMI_COLON))
                        {
                            c = nextToken();
                            validSyntax("Affect variable a variable");
                            return followedByBlockInstruction();// next instruction
                        }
                    }
                }

            }
            return logError();
        }
        else if(c.equals(KeywordToken.SHOW_MESSAGE))
        {
            c = nextToken();
            if(c.equals(SymbolToken.COLUMN))
            {
                c = nextToken();
                if(c instanceof DataToken)
                {
                    c = nextToken();
                    if(c.equals(KeywordToken.SEMI_COLON))
                    {
                        c = nextToken();
                        validSyntax("Affichage message");
                        return followedByBlockInstruction();  // next instruction
                    }
                }
            }
            return logError();
        }
        else if(c.equals(KeywordToken.SHOW_VAL))
        {
            c = nextToken();
            if(c.equals(SymbolToken.COLUMN))
            {
                c = nextToken();
                if(c instanceof DataToken)
                {
                    c = nextToken();
                    if(c.equals(KeywordToken.SEMI_COLON))
                    {
                        c = nextToken();
                        validSyntax("Affichage Valeur");
                        return followedByBlockInstruction();  // next instruction
                    }
                }
            }
            return logError();
        }
        else if(c.equals(KeywordToken.IF))
        {
            c = nextToken();
            if(c.equals(SymbolToken.DOUBLE_DASH))
            {
                c = nextToken();
                if(followedByCondition()) // condition
                {
                    c = currentToken();
                    if( c.equals(SymbolToken.DOUBLE_DASH))
                    {
                        c = nextToken();
                        validSyntax("If statement");
                        return followedByStartFinishBlock();
                    }
                }
            }
            return logError();
        }
        else if(c instanceof VarTypeToken)
        {
            c = nextToken();
            if(c.equals(SymbolToken.COLUMN))
            {
                c = nextToken();
                if(followedByIdentifiers())
                {
                    c = currentToken();
                    if(c.equals(KeywordToken.SEMI_COLON))
                    {
                        c = nextToken();
                        validSyntax("Declaration variable");
                        return followedByBlockInstruction();  // next instruction
                    }
                }
            }
            return logError();
        }
        else
        {
            return true;    // mot vide
        }
    }

    private boolean followedByIdentifiers()
    {
        if(currentToken() instanceof IdToken)
        {
            Token c = nextToken();
            if(c.equals(SymbolToken.COMMA))
            {
                c = nextToken();
                return followedByIdentifiers();
            }
            else return true; // id seulment
        }
        return logError();
    }

    private boolean followedByCondition()
    {
        Token c;
        if(currentToken().equals(LogicalToken.NOT))
        {
            c = nextToken();
            if(followedByCondition())
            {
                c = nextToken();
                return followedByCondition2();
            }
        }
        else if(currentToken() instanceof IdToken)
        {
            c = nextToken();
            if(c instanceof ArithmeticToken)
            {
                c = nextToken();
                if(c instanceof IdToken)
                {
                    c = nextToken();
                    return followedByCondition2();
                }
            }
        }

        return logError();
    }

    private boolean followedByCondition2()
    {
        if(currentToken() instanceof LogicalToken)
        {
            Token c = nextToken();
            if(followedByCondition())
            {
                c = currentToken();
                if(followedByCondition())
                {
                    return true;
                }
            }
        }
        else
        {
            return true; // mot vide
        }

        return logError();
    }

    private boolean followedByStartFinishBlock()
    {
        Token c = currentToken();
        if(c.equals(KeywordToken.START))
        {
            c = nextToken();
            validSyntax("Debut block instr");
            if(followedByBlockInstruction())
            {
                c = currentToken();
                if (c.equals(KeywordToken.FINISH))
                {
                    c = nextToken();
                    validSyntax("Fin block instr");
                    return followedByElseStatement();
                }
            }
        }
        return logError();
    }

    private boolean followedByElseStatement()
    {
        Token c = currentToken();

        if(c.equals(KeywordToken.ELSE))
        {
            c = nextToken();
            validSyntax("Else");
            if(followedByStartFinishBlock())
            {
                return true;
            }
            return false;
        }
        return true;
    }


    private void validSyntax(String s)
    {
        String instr = "";
        for(Token t : buffer)
        {
            instr = instr + t.getText() + " ";
        }

        result.add(instr + "\t-->  " + s);
        buffer.clear();
    }

    private boolean logError()
    {
        String instr = "";
        for(Token t : buffer)
        {
            instr = instr + t.getText() + " ";
        }

        System.out.println("ERROR ! instruction non valide : " + instr);
        buffer.clear();
        return false;
    }

    public static void main(String[] args)
    {
        SyntaxEngine syntaxEngine = new SyntaxEngine();

        ArrayList<Token> tokenSource = new ArrayList<>();

        tokenSource.add(new KeywordToken("Start_Program"));
        tokenSource.add(new KeywordToken("Give"));
        tokenSource.add(new IdToken("TesVar"));
        tokenSource.add(new SymbolToken(":"));
        tokenSource.add(new DataToken("125"));
        tokenSource.add(new KeywordToken(";;"));
        tokenSource.add(new KeywordToken("Affect"));
        tokenSource.add(new IdToken("i"));
        tokenSource.add(new KeywordToken("to"));
        tokenSource.add(new IdToken("j"));
        tokenSource.add(new KeywordToken(";;"));

        /*
            If -- i<j --
            Start
                Give Aft_5 : 10 ;;
            Finish
            Else
              Start
                Affect i to j ;;
                Give Af34_2 : 123.54 ;;
              Finish
        */

        tokenSource.add(new KeywordToken("If"));
        tokenSource.add(new SymbolToken("--"));
        tokenSource.add(new IdToken("i"));
        tokenSource.add(new ArithmeticToken("<"));
        tokenSource.add(new IdToken("j"));
        tokenSource.add(new SymbolToken("--"));
        tokenSource.add(new KeywordToken("Start"));
        tokenSource.add(new KeywordToken("Give"));
        tokenSource.add(new IdToken("Aft_5"));
        tokenSource.add(new SymbolToken(":"));
        tokenSource.add(new DataToken("10"));
        tokenSource.add(new KeywordToken(";;"));
        tokenSource.add(new KeywordToken("Finish"));

        tokenSource.add(new KeywordToken("Else"));
        tokenSource.add(new KeywordToken("Start"));
        tokenSource.add(new KeywordToken("Affect"));
        tokenSource.add(new IdToken("i"));
        tokenSource.add(new KeywordToken("to"));
        tokenSource.add(new IdToken("j"));
        tokenSource.add(new KeywordToken(";;"));
        tokenSource.add(new KeywordToken("Give"));
        tokenSource.add(new IdToken("Af34_2"));
        tokenSource.add(new SymbolToken(":"));
        tokenSource.add(new DataToken("123.54"));
        tokenSource.add(new KeywordToken(";;"));
        tokenSource.add(new KeywordToken("Finish"));


        tokenSource.add(new KeywordToken("End_Program"));

        syntaxEngine.setTokenSource(tokenSource);

        syntaxEngine.performAnalysis();

        for (String s : syntaxEngine.result)
        {
            System.out.println(s);
        }
    }
}
