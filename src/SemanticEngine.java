import instruction.Instruction;
import token.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


class SemanticEngine
{

    private ArrayList<Instruction> instructionSource;
    private int currentInstrIndex = -1;

    private Map<String, VarTypeToken> idTable;

    private String result = "";
    private String errors = "";

    SemanticEngine()
    {
        clear();
    }

    private void clear()
    {
        idTable = new HashMap<>();
        result = "";
        errors = "";
        currentInstrIndex = -1;
    }

    private Instruction nextInstruction()
    {
        currentInstrIndex++;
        if(currentInstrIndex >= instructionSource.size())
            return null;
        return currentInstruction();
    }

    private Instruction currentInstruction()
    {
        return instructionSource.get(currentInstrIndex);
    }
    String getResult()
    {
        clear();
        performAnalysis();
        return result;
    }

    String getErrors()
    {
        return errors;
    }

    private void performAnalysis()
    {
        System.out.println("Performing semantic analysis");
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

        for (String id : idTable.keySet())
        {
            System.out.println(id + " : " + idTable.get(id).getText());
        }
    }

    private void logError(String error)
    {
        errors += "Line " + currentInstruction().getTokenList().get(0).getLine() + " : " + error + "\n";
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
        IdToken id1 = (IdToken) instr.getTokenList().get(1);
        IdToken id2 = (IdToken) instr.getTokenList().get(3);

        if(idTable.containsKey(id1.getText()) && idTable.containsKey(id2.getText())
                && idTable.get(id1.getText()).getText().equals(idTable.get(id2.getText()).getText()))
        {
            System.out.println("Affectation true");
        }
        else
        {
            logError("Type mismatch");
        }
    }

    private void interpretAffectValue()
    {
        Instruction instr = currentInstruction();
        IdToken id1 = (IdToken) instr.getTokenList().get(1);
        DataToken id2 = (DataToken) instr.getTokenList().get(3);

        if(idTable.containsKey(id1.getText()))
        {
            if(idTable.get(id1.getText()).getText().equals(id2.getDataType().getText()))
            {
                System.out.println("Affectation true");
            }
            else
            {
                logError("Type mismatch");
            }
        }
        else
        {
            logError("Undeclared variable : " + id1.getText());
        }

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
