import token.*;

import java.util.ArrayList;



public class SyntaxEngine
{
    private ArrayList<Token> tokenSource;
    private ArrayList<String> result;

    private int positionTeteLecture = -1;

    public SyntaxEngine()
    {
        tokenSource = null;
    }

    private Token nextToken()
    {
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

    private void performAnalysis()
    {
        if(S())
        {
            System.out.println("Reconnu ! ");
        }
        else
        {
            System.out.println("Non reconnue !");
        }
    }

    private boolean S()
    {
        if(nextToken().equals(KeywordToken.START_PROGRAM))
        {
            nextToken();
            Si();
            if(currentToken().equals(KeywordToken.END_PROGRAM))
                return true;
        }
        return false;
    }

    private boolean Si()
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
                            System.out.println("Affectation valeur a variable");
                            c = nextToken();
                            return Si();// next instruction
                        }
                    }
                }
            }
            return error();
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
                            System.out.println("Affect variable a variable");
                            c = nextToken();
                            return Si();// next instruction
                        }
                    }
                }

            }
            return error();
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
                        System.out.println("Affichage message");
                        c = nextToken();
                        return Si();  // next instruction
                    }
                }
            }
            return error();
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
                        System.out.println("Affichage Valeur");
                        c = nextToken();
                        return Si();  // next instruction
                    }
                }
            }
            return error();
        }
        else if(c.equals(KeywordToken.IF))
        {
            c = nextToken();
            if(c.equals(SymbolToken.DOUBLE_DASH))
            {
                c = nextToken();
                System.out.print("Should find a condition here... ");
                if(C()) // condition
                {
                    System.out.println("YES !");
                    c = currentToken();
                    if( c.equals(SymbolToken.DOUBLE_DASH))
                    {
                        c = nextToken();
                        return D();
                    }
                }
            }
            return error();
        }
        else if(c instanceof VarTypeToken)
        {
            c = nextToken();
            if(c.equals(SymbolToken.COLUMN))
            {
                c = nextToken();
                if(I())
                {
                    c = currentToken();
                    if(c.equals(KeywordToken.SEMI_COLON))
                    {
                        System.out.println("Declaration variable");
                        c = nextToken();
                        return Si();  // next instruction
                    }
                }
            }
            return error();
        }
        else
        {
            return true;    // mot vide
        }
    }

    private boolean I()
    {
        if(currentToken() instanceof IdToken)
        {
            Token c = nextToken();
            if(c.equals(SymbolToken.COMMA))
            {
                c = nextToken();
                return I();
            }
            else return true; // id seulment
        }
        return error();
    }

    private boolean C()
    {
        Token c;
        if(currentToken().equals(LogicalToken.NOT))
        {
            c = nextToken();
            if(C())
            {
                c = nextToken();
                return C2();
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
                    return C2();
                }
            }
        }

        return error();
    }

    private boolean C2()
    {
        if(currentToken() instanceof LogicalToken)
        {
            Token c = nextToken();
            if(C())
            {
                c = currentToken();
                if(C())
                {
                    return true;
                }
            }
        }
        else
        {
            return true; // mot vide
        }

        return error();
    }

    private boolean D()
    {
        Token c = currentToken();
        if(c.equals(KeywordToken.START))
        {
            c = nextToken();
            if(Si())
            {
                c = currentToken();
                if (c.equals(KeywordToken.FINISH))
                {
                    System.out.println("Finish");
                    c = nextToken();
                    return E();
                }
            }
        }
        return error();
    }


    private boolean E()
    {
        Token c = currentToken();

        if(c.equals(KeywordToken.ELSE))
        {
            System.out.println("Else");
            c = nextToken();
            if(D())
            {
                return true;
            }
            return false;
        }
        return true;
    }

    private boolean error()
    {
        System.out.println("ERROR");
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
    }
}
