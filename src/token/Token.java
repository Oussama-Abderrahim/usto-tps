package token;

/**
 * Created by TOSHIBA on 17/11/2017.
 */
public abstract class Token
{
    private String text;
    private int line;
    private TokenType type;

    public Token(String text, int line, TokenType type)
    {
        this.text = text;
        this.line = line;
        this.type = type;
    }

    public int getLine()
    {
        return line;
    }

    public void setLine(int line)
    {
        this.line = line;
    }

    public String getText()
    {
        return text;
    }

    public TokenType getType()
    {
        return type;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (text != null ? !text.equals(token.text) : token.text != null) return false;
        return type == token.type;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + type.hashCode();
        return result;
    }
}
