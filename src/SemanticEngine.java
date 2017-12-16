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
    private ArrayList<String> initialisedVariables;

    private String compiledCode = "";
    private String errors = "";

    SemanticEngine()
    {
        clear();
    }

    private void clear()
    {
        initialisedVariables = new ArrayList<>();
        idTable = new HashMap<>();
        compiledCode = "";
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
        if(errors.isEmpty())
            return (new ProgramRunner(compiledCode).getConsoleOutput());
        //return compiledCode;
        return "";
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
        compiledCode += "System.out.println(\"\"+"+instr.getTokenList().get(2).getText()+");";
    }

    private void interpretElseStatement()
    {
        compiledCode += "else\n";
    }

    private void interpretFinish()
    {
        compiledCode += "}\n";
    }

    private void interpretBegin()
    {
        compiledCode += "{\n";
    }

    private void interpretVarDecl()
    {
        Instruction instr = currentInstruction();

        VarTypeToken type = (VarTypeToken) instr.getTokenList().get(0);
        String javaType = "int";
        switch(type.getText())
        {
            case "Real_Number": javaType = "double";
            break;
            case "String": javaType = "String";
                break;
            case "Int_Number": javaType = "int";
                break;
        }


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
                compiledCode += javaType + " " + id + ";\n";

            }
            i++;
        }while (!instr.getTokenList().get(i).equals(KeywordToken.SEMI_COLON));

    }

    private void logError(String error)
    {
        errors += "Line " + currentInstruction().getTokenList().get(0).getLine() + " : " + error + "\n";
    }

    private void interpretIfStatement()
    {
        Instruction instr = currentInstruction();
        int i = 2;
        String cond = "";
        VarTypeToken type = null;
        while (!instr.getTokenList().get(i).equals(SymbolToken.DOUBLE_DASH))
        {
            Token t = instr.getTokenList().get(i);
            cond += t.getText();
            i++;

            if(t instanceof IdToken)
            {
                if(!idTable.containsKey(t.getText()))
                    logError("Undeclared variable " + t.getText());
                else if(!initialisedVariables.contains(t.getText()))
                    logError("Uninitialised variable " + t.getText());
                else if(type == null)
                    type = idTable.get(t.getText());
                else if(!idTable.get(t.getText()).equals(type))
                    logError("Type mismatch");
            }
            if(t instanceof DataToken)
            {
                if(type == null)
                    type = ((DataToken) t).getDataType();
                if(!((DataToken) t).getDataType().equals(type))
                    logError("Type mismatch");
            }
        }
        compiledCode += "if (" + cond + ") \n";
    }

    private void interpretShowMsg()
    {
        Instruction instr = currentInstruction();

        compiledCode += "System.out.println("+instr.getTokenList().get(2).getText()+");\n";
    }

    private void interpretAffectVar()
    {
        Instruction instr = currentInstruction();
        IdToken id1 = (IdToken) instr.getTokenList().get(1);
        IdToken id2 = (IdToken) instr.getTokenList().get(3);

        if(idTable.containsKey(id1.getText()))
        {
            if(idTable.containsKey(id2.getText()))
            {
                if(idTable.get(id1.getText()).equals(idTable.get(id2.getText())))
                {
                    if(initialisedVariables.contains(id1.getText()))
                    {
                        initialisedVariables.add(id2.getText());
                        compiledCode += id2.getText() + "=" + id1.getText() + ";\n";
                    }
                    else
                    {
                        logError("Uninitialised variable " + id1.getText());
                    }
                }
                else
                {
                    logError("Type mismatch");
                }
            }
            else
            {
                logError("Undeclared Variable " + id2.getText());
            }
        }
        else
        {
            logError("Undeclared Variable " + id1.getText());
        }
    }

    private void interpretAffectValue()
    {
        Instruction instr = currentInstruction();
        IdToken id1 = (IdToken) instr.getTokenList().get(1);
        DataToken id2 = (DataToken) instr.getTokenList().get(3);

        if(idTable.containsKey(id1.getText()))
        {
            if(idTable.get(id1.getText()).equals(id2.getDataType()))
            {
                initialisedVariables.add(id1.getText());
                compiledCode += id1.getText() + "=" + id2.getText() + ";\n";
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
        compiledCode += "}\n}\n";
    }

    private void interpretStartProgram()
    {
        compiledCode += "class Main {" +
                "\n\n" +
                "public static void main(String[] args) \n{\n";
    }

    public void setInstructionSource(ArrayList<Instruction> instructionSource)
    {
        this.instructionSource = instructionSource;
    }
}
