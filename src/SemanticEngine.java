import instruction.Instruction;
import token.KeywordToken;
import token.Token;
import token.VarTypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${} on 18/11/2017.
 */
public class SemanticEngine
{

    private ArrayList<Instruction> instructionSource;
    private int currentInstrIndex = -1;

    private Map<String, VarTypeToken> idTable;

    private String result = "";

    public SemanticEngine()
    {
        clear();
    }

    private void clear()
    {
        idTable = new HashMap<>();
        result = "";
        currentInstrIndex = -1;
    }

    private Instruction nextInstruction()
    {
        currentInstrIndex++;
        if(currentInstrIndex < instructionSource.size())
            return null;
        return currentInstruction();
    }

    private Instruction currentInstruction()
    {
        return instructionSource.get(currentInstrIndex);
    }
    public String getResult()
    {
        performAnalysis();
        return result;
    }

    private void performAnalysis()
    {
        while (nextInstruction() != null)
        {
            switch (currentInstruction().getType())
            {
                case START_PROGRAM:
                    interpretStartProgram();
                    break;
                case END_PROGRAM:
                    interpretEndProgram();
                    break;
                case AFFECT_VALUE:
                    interpretAffectValue();
                    break;
                case AFFECT_VAR:
                    interpretAffectVar();
                    break;
                case SHOW_MSG:
                    interpretShowMsg();
                    break;
                case IF_STATEMENT:
                    interpretIfStatement();
                    break;
                case VAR_DECL:
                    interpretVarDecl();
                    break;
                case BEGIN:
                    interpretBegin();
                    break;
                case FINISH:
                    interpretFinish();
                    break;
                case ELSE_STATEMENT:
                    interpretElseStatement();
                    break;
                case SHOW_VAR:
                    interpretShowVar();
                    break;
            }
        }
    }

    private void interpretShowVar()
    {
        Instruction instr = currentInstruction();
    }

    private void interpretElseStatement()
    {
        Instruction instr = currentInstruction();
    }

    private void interpretFinish()
    {
        Instruction instr = currentInstruction();
    }

    private void interpretBegin()
    {
        Instruction instr = currentInstruction();
    }

    private void interpretVarDecl()
    {
        Instruction instr = currentInstruction();

        VarTypeToken type = (VarTypeToken) instr.getTokenList().get(0);

        int i = 1;  // start at first id
        do
        {
            i++;
            String id = instr.getTokenList().get(i).getText();
            if (idTable.containsKey(id))
            {
                logError("Variable already declared");
            }
            else
            {
                idTable.put(id, type);
            }
            i++;
        }while (!instr.getTokenList().get(i).equals(KeywordToken.SEMI_COLON));
    }

    private void logError(String error)
    {
        System.err.println("Error : " + error);
    }

    private void interpretIfStatement()
    {
        Instruction instr = currentInstruction();
    }

    private void interpretShowMsg()
    {
        Instruction instr = currentInstruction();
    }

    private void interpretAffectVar()
    {
        Instruction instr = currentInstruction();
    }

    private void interpretAffectValue()
    {
        Instruction instr = currentInstruction();
    }

    private void interpretEndProgram()
    {
        Instruction instr = currentInstruction();
    }

    private void interpretStartProgram()
    {
        Instruction instr = currentInstruction();
    }

    public void setInstructionSource(ArrayList<Instruction> instructionSource)
    {
        this.instructionSource = instructionSource;
    }
}
