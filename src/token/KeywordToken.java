package token;



public class KeywordToken extends Token
{

    public static final KeywordToken START_PROGRAM = new KeywordToken("Start_Program");
    public static final KeywordToken END_PROGRAM = new KeywordToken("End_Program");
    public static final KeywordToken SHOW_MESSAGE = new KeywordToken("ShowMes");
    public static final KeywordToken SHOW_VAL = new KeywordToken("ShowVal");
    public static final KeywordToken GIVE = new KeywordToken("Give");
    public static final KeywordToken AFFECT = new KeywordToken("Affect");
    public static final KeywordToken IF = new KeywordToken("If");
    public static final KeywordToken ELSE = new KeywordToken("Else");
    public static final KeywordToken SEMI_COLON = new KeywordToken(";;");
    public static final KeywordToken TO = new KeywordToken("to");
    public static final KeywordToken START = new KeywordToken("Start");
    public static final KeywordToken FINISH = new KeywordToken("Finish");

    public KeywordToken(String text)
    {
        super(text, TokenType.KEYWORD);
    }
}
