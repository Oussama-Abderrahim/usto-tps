package instruction;

import token.Token;

import java.util.ArrayList;

/**
 * Created by ${} on 15/12/2017.
 */
public class Instruction
{
    private ArrayList<Token> tokenList;
    private int line;
    private InstructionType type;

    public Instruction(ArrayList<Token> tokenList, InstructionType type)
    {
        line = 0;
        this.tokenList = tokenList;
        this.type = type;
    }

    public InstructionType getType()
    {
        return type;
    }

    public ArrayList<Token> getTokenList()
    {
        return tokenList;
    }
}
