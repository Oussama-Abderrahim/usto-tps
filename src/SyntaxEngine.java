import instruction.Instruction;
import instruction.InstructionType;
import token.*;

import java.util.ArrayList;



public class SyntaxEngine
{
    private ArrayList<Token> tokenSource;
    private ArrayList<Instruction> result = new ArrayList<>();
    private ArrayList<Token> buffer = new ArrayList<>();

    private int positionTeteLecture = 0;
    private String errors = "";
    private int lineCount = 1;

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
            return new SymbolToken("$", 0);

        return tokenSource.get(positionTeteLecture);
    }

    public void setTokenSource(ArrayList<Token> tokenSource)
    {
        this.tokenSource = tokenSource;
    }

    public ArrayList<Instruction> getResult()
    {
        performAnalysis();
        return result;
    }

    public String getErrors()
    {
        return errors;
    }
    private void clear()
    {
        lineCount = 1;
        errors = "";
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
            validSyntax(InstructionType.START_PROGRAM);
            followedByBlockInstruction();
            if(currentToken().equals(KeywordToken.END_PROGRAM))
            {
                nextToken();
                validSyntax(InstructionType.END_PROGRAM);
                return true;
            }
        }
        return logError("Build failed");
    }

    /// TODO : Add S' for repeating instructions
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
                            validSyntax(InstructionType.AFFECT_VALUE);
                            return followedByBlockInstruction();// next instruction
                        }
                    }
                }
            }
            return logError("Instruction non valide");
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
                            validSyntax(InstructionType.AFFECT_VAR);
                            return followedByBlockInstruction();// next instruction
                        }
                    }
                }

            }
            return logError("Instruction non valide");
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
                        validSyntax(InstructionType.SHOW_MSG);
                        return followedByBlockInstruction();  // next instruction
                    }
                }
            }
            return logError("Instruction non valide");
        }
        else if(c.equals(KeywordToken.SHOW_VAL))
        {
            c = nextToken();
            if(c.equals(SymbolToken.COLUMN))
            {
                c = nextToken();
                if(c instanceof IdToken)
                {
                    c = nextToken();
                    if(c.equals(KeywordToken.SEMI_COLON))
                    {
                        c = nextToken();
                        validSyntax(InstructionType.SHOW_VAR);
                        return followedByBlockInstruction();  // next instruction
                    }
                }
            }
            return logError("Instruction non valide");
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
                        validSyntax(InstructionType.IF_STATEMENT);
                        if(followedByStartFinishBlock())
                        {
                            return followedByBlockInstruction();
                        }
                    }
                }
            }
            return logError("Instruction non valide");
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
                        validSyntax(InstructionType.VAR_DECL);
                        return followedByBlockInstruction();  // next instruction
                    }
                }
            }
            return logError("Instruction non valide");
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
        return logError("Instruction non valide");
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

        return logError("Instruction non valide");
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

        return logError("Instruction non valide");
    }

    private boolean followedByStartFinishBlock()
    {
        Token c = currentToken();
        if(c.equals(KeywordToken.START))
        {
            c = nextToken();
            validSyntax(InstructionType.BEGIN);
            if(followedByBlockInstruction())
            {
                c = currentToken();
                if (c.equals(KeywordToken.FINISH))
                {
                    c = nextToken();
                    validSyntax(InstructionType.FINISH);
                    return followedByElseStatement();
                }
            }
        }
        return logError("Instruction non valide");
    }

    private boolean followedByElseStatement()
    {
        Token c = currentToken();

        if(c.equals(KeywordToken.ELSE))
        {
            c = nextToken();
            validSyntax(InstructionType.ELSE_STATEMENT);
            if(followedByStartFinishBlock())
            {
                return true;
            }
            return false;
        }
        return true;
    }


    private void validSyntax(InstructionType type)
    {
        result.add(new Instruction(new ArrayList<>(buffer), type));
        lineCount++;
        buffer.clear();
    }

    private boolean logError(String s)
    {
        String instr = "";
        for(Token t : buffer)
        {
            instr = instr + t.getText() + " ";
        }

        errors += "line " + lineCount + " : " + s + " at " + instr + '\n';
        buffer.clear();
        return true;
    }

    public static void main(String[] args)
    {
        SyntaxEngine syntaxEngine = new SyntaxEngine();

        ArrayList<Token> tokenSource = new ArrayList<>();

        tokenSource.add(new KeywordToken("Start_Program", 0));
        tokenSource.add(new KeywordToken("Give", 0));
        tokenSource.add(new IdToken("TesVar", 0));
        tokenSource.add(new SymbolToken(":", 0));
        tokenSource.add(new DataToken("125", 0));
        tokenSource.add(new KeywordToken(";;", 0));
        tokenSource.add(new KeywordToken("Affect", 0));
        tokenSource.add(new IdToken("i", 0));
        tokenSource.add(new KeywordToken("to", 0));
        tokenSource.add(new IdToken("j", 0));
        tokenSource.add(new KeywordToken(";;", 0));

        tokenSource.add(new KeywordToken("ShowMes", 0));
        tokenSource.add(new SymbolToken(":", 0));
        tokenSource.add(new DataToken("\"Ceci est un message\"", 0));
        tokenSource.add(new KeywordToken(";;", 0));

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

        tokenSource.add(new KeywordToken("If", 0));
        tokenSource.add(new SymbolToken("--", 0));
        tokenSource.add(new IdToken("i", 0));
        tokenSource.add(new ArithmeticToken("<", 0));
        tokenSource.add(new IdToken("j", 0));
        tokenSource.add(new SymbolToken("--", 0));
        tokenSource.add(new KeywordToken("Start", 0));
        tokenSource.add(new KeywordToken("Give", 0));
        tokenSource.add(new IdToken("Aft_5", 0));
        tokenSource.add(new SymbolToken(":", 0));
        tokenSource.add(new DataToken("10", 0));
        tokenSource.add(new KeywordToken(";;", 0));
        tokenSource.add(new KeywordToken("Finish", 0));

        tokenSource.add(new KeywordToken("Else", 0));
        tokenSource.add(new KeywordToken("Start", 0));
        tokenSource.add(new KeywordToken("Affect", 0));
        tokenSource.add(new IdToken("i", 0));
        tokenSource.add(new KeywordToken("to", 0));
        tokenSource.add(new IdToken("j", 0));
        tokenSource.add(new KeywordToken(";;", 0));
        tokenSource.add(new KeywordToken("Give", 0));
        tokenSource.add(new IdToken("Af34_2", 0));
        tokenSource.add(new SymbolToken(":", 0));
        tokenSource.add(new DataToken("123.54", 0));
        tokenSource.add(new KeywordToken(";;", 0));
        tokenSource.add(new KeywordToken("Finish", 0));

        tokenSource.add(new KeywordToken("End_Program", 0));

        syntaxEngine.setTokenSource(tokenSource);

        syntaxEngine.performAnalysis();
    }
}
