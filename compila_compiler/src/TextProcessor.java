/**
 * Created by ${} on 18/11/2017.
 */
public class TextProcessor
{

    public static boolean compare(String s1, String s2)
    {
        return s1.equals(s2);
    }

    public static boolean isAlphaNum(String character)
    {
        return false;
    }

    public static boolean isDigit(char c)
    {
        return c == '1' ||
                c == '2' ||
                c == '3' ||
                c == '4' ||
                c == '5' ||
                c == '6' ||
                c == '7' ||
                c == '8' ||
                c == '9' ||
                c == '0';
    }

    public static boolean isUpperCaseLetter(char c)
    {
        return Character.getNumericValue(c) >= Character.getNumericValue('A')
                && Character.getNumericValue(c) <= Character.getNumericValue('Z');
    }

    public static boolean isLowerCaseLetter(char c)
    {
        return Character.getNumericValue(c) >= Character.getNumericValue('a')
                && Character.getNumericValue(c) <= Character.getNumericValue('z');
    }
}
