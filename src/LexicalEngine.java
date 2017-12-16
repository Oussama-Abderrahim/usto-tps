import token.*;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class LexicalEngine
{
    private final Pattern idPattern = Pattern.compile("[a-z]|[A-Z](_?([A-Za-z]|[0-9])+)*");
    Pattern dataPattern = Pattern.compile("(\\\".*\\\")|([0-9]+(\\.[0-9]+)?)");
    Pattern numberPatter = Pattern.compile("([0-9]+(\\.[0-9]+)?)");


    private final String[] keywords = {
            "Start_Program",
            "End_Program",
            "ShowMes",
            "ShowVal",
            "Give",
            "Affect",
            "If",
            "Else",
            ";;",
            "to",
            "Start",
            "Finish"
    };

    private final String[] varTypes = {
            "Int_Number",
            "Real_Number",
            "String"
    };

    private final String[] symbols = {
            ":",
            "--",
            ","
    };

    private final String[] arithmeticOp = {
            ">",
            "<",
            "==",
            "<=",
            ">="
    };

    private String sourceCode;
    private ArrayList<Token> tokenSource = new ArrayList<>();
    private String errors = "";
    private String buffer = "";

    private int positionTeteLecture = 0;
    private int lineCount;

    public LexicalEngine()
    {
        sourceCode = "";
        buffer = "";
        clear();
    }

    private boolean EOF()
    {
        return positionTeteLecture >= sourceCode.length();
    }

    private char nextChar()
    {
        buffer += currentChar();
        positionTeteLecture++;
        return currentChar();
    }

    private char currentChar()
    {
        if (positionTeteLecture >= sourceCode.length())
            return '$';
        return sourceCode.charAt(positionTeteLecture);
    }

    public void setSourceCode(String src)
    {
        positionTeteLecture = 0;
        this.sourceCode = src;
    }

    public ArrayList<Token> getTokenSource()
    {
        performAnalysis();
        System.out.println("Line count : " + lineCount);
        return tokenSource;
    }

    public String getErrors()
    {
        return errors;
    }

    public void clear()
    {
        lineCount = 0;
        tokenSource.clear();
        errors = "";
        buffer = "";
        positionTeteLecture = 0;
    }

    /// TODO : Add line count
    private boolean performAnalysis()
    {
        clear();
        System.out.println("Performing lexical analysis");

        lineCount = 0;
        char c = currentChar();
        while (!EOF())
        {
            boolean proceed = true; // si vrai lire le prochain char à la fin

            // verifier commentaires
            if(!skipComments())
                return false;
            c = currentChar();
            // lecture
            if (isSeparator(c))
            {
                // Check previous
                if (buffer.isEmpty())
                {
                    // nothing
                }
                else if (isKeyword(buffer))
                {
                    tokenSource.add(new KeywordToken(buffer, lineCount));
                }
                else if (isArithmeticOp(buffer))
                {
                    tokenSource.add(new ArithmeticToken(buffer, lineCount));
                }
                else if (isVarType(buffer))
                {
                    tokenSource.add(new VarTypeToken(buffer, lineCount));
                }
                else if (isNumber(buffer))
                {
                    tokenSource.add(new DataToken(buffer, lineCount));
                }
                else if (isIdentifier(buffer))
                {
                    tokenSource.add(new IdToken(buffer, lineCount));
                }
                else
                {
                    return logError("Unidentified token " + buffer);
                }

                // Now check the separator
                switch (c)
                {
                    case '<':
                    case '>':
                    case '=':
                        if (!readArithmeticOp())
                            return logError("Illegal operator");
                        proceed = false;
                        break;
                    case ':':
                        tokenSource.add(new SymbolToken(":", lineCount));
                        break;
                    case ';':
                        c = nextChar();
                        if (c == ';')
                            tokenSource.add(new KeywordToken(";;", lineCount));
                        else
                            return logError("Unknown token ';' found");
                        break;
                    case '-':
                        c = nextChar();
                        if (c == '-')
                            tokenSource.add(new SymbolToken("--", lineCount));
                        else
                            return logError("Unknown token '-'"+c+" found");
                        break;
                    case ',':
                        tokenSource.add(new SymbolToken(",", lineCount));
                        break;
                    case '"':
                        if (!readString())
                            return logError("'\"' Expected ");
                        proceed = false;
                        break;
                    case '\n':
                        lineCount++;
                        break;
                    default:    // whitespace ignored
                }
                if (proceed)
                    c = nextChar();
                else
                    c = currentChar();
                buffer = ""; // clear buffer
            }
            else
            {
                c = nextChar();
            }
        }

        return true; // correct
    }

    private boolean skipComments()
    {
        char c = currentChar();
        if(c == '/')
        {
            c = nextChar();
            if(c == '/')
            {
                c = nextChar();
                if(c == '.')
                {
                    // skip to nextLine
                    while (nextChar() != '\n');
                    nextChar();
                    buffer = "";
                    lineCount++;
                    return true;
                }
            }
        }
        else
        {
            return true;
        }

        return logError("Illegal caracter");
    }

    private boolean readArithmeticOp()
    {
        char c = currentChar();
        if (c == '<' || c == '>' || c == '=')
        {
            char next_c = nextChar();
            if (next_c == '=')
            {
                next_c = nextChar();
                tokenSource.add(new ArithmeticToken(c + "=", lineCount));
            }
            else
            {
                tokenSource.add(new ArithmeticToken("" + c, lineCount));
            }
            return true;
        }
        return false;   // should be impossible ...
    }

    private boolean readString()
    {
        char c;

        do
        {
            c = nextChar();
        } while (c != '"' && !EOF());
        nextChar();
        tokenSource.add(new DataToken(buffer, lineCount));
        buffer = "";
        return true;
    }

    private boolean isArithmeticOp(String buffer)
    {
        return TextProcessor.compare(buffer, ">") ||
                TextProcessor.compare(buffer, "<") ||
                TextProcessor.compare(buffer, "==") ||
                TextProcessor.compare(buffer, "<=") ||
                TextProcessor.compare(buffer, ">=");
    }

    private boolean isIdentifier(String buffer)
    {
        return (idPattern.matcher(buffer).matches());
    }

    private boolean isVarType(String buffer)
    {
        return TextProcessor.compare(buffer, "Int_Number")
                || TextProcessor.compare(buffer, "Real_Number");
    }

    private boolean isKeyword(String buffer)
    {
        return TextProcessor.compare(buffer, "Start_Program")
                || TextProcessor.compare(buffer, "End_Program")
                || TextProcessor.compare(buffer, "ShowMes")
                || TextProcessor.compare(buffer, "ShowVal")
                || TextProcessor.compare(buffer, "Give")
                || TextProcessor.compare(buffer, "Affect")
                || TextProcessor.compare(buffer, "If")
                || TextProcessor.compare(buffer, "Else")
                || TextProcessor.compare(buffer, ";;")
                || TextProcessor.compare(buffer, "to")
                || TextProcessor.compare(buffer, "Start")
                || TextProcessor.compare(buffer, "Finish");
    }

    private boolean isSymbol(String buffer)
    {
        return false;
    }

    private boolean isDigit(char c)
    {
        return c == '0' ||
                c == '1' ||
                c == '2' ||
                c == '3' ||
                c == '4' ||
                c == '5' ||
                c == '6' ||
                c == '7' ||
                c == '8' ||
                c == '9';
    }

    private boolean isNumber(String buffer)
    {
        return numberPatter.matcher(buffer).matches();
//        for (int i = 0; i < buffer.length(); i++)
//        {
//            if (!isDigit(buffer.charAt(i)))
//            {
//                return false;
//            }
//        }
//        return true;
    }

    private boolean isSeparator(char c)
    {
        return (c == ' '
                || c == '\n'
                || c == '\t'
                || c == ','
                || c == ':'
                || c == ';'
                || c == '-'
                || c == '>'
                || c == '<'
                || c == '='
                || c == '"');
    }


    private boolean logError(String s)
    {
        /// TODO : add error to result
        errors += "Line " + lineCount + " : " + s + "\n";

        char c = currentChar();
        while (!EOF() && c != '\n') c = nextChar();

        return true;    // continue after error
    }

}
