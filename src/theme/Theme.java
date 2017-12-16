package theme;

import java.awt.*;

/**
 */
public class Theme
{
    // Font
    public static final Font FONT_DEFAULT           = new Font("Cambria", Font.PLAIN, 18);
    public static final Font FONT_DEFAULT_BOLD      = new Font("Cambria", Font.BOLD, 16);
    public static final Font FONT_DEFAULT_MEDIUM    = new Font("Cambria", Font.PLAIN, 18);
    public static final Font FONT_DEFAULT_LARGE     = new Font("Cambria", Font.PLAIN, 32);
    public static final Font FONT_DEFAULT_BIG       = new Font("Cambria", Font.PLAIN, 48);

    // Custom Colors
    public static final Color BACKGROUND_COLOR = new Color(10, 10, 10);
    public static final Color CHIBHBLACK = new Color(33, 33, 33);
    public static final Color GREEN_EMERALD = new Color(39, 174, 96);


    private static final int THEME = 0; // 0 plain, 1 Dracula
    // Font Colors
    public static final Color FONT_DEFAULT_COLOR = (THEME == 1)? Color.WHITE : Color.WHITE;
    public static final Color FONT_INPUT_COLOR = (THEME == 1)? Color.WHITE : Color.BLACK;
    public static final Color FONT_WARNING_COLOR = (THEME == 1)? Color.RED : Color.RED;
    public static final Color FONT_SUCCESS_COLOR = (THEME == 1)? Color.GREEN : Color.GREEN;

    // Text Area
    public static final Color INPUT_BACKGROUND_COLOR = (THEME == 1)? new Color(43,43,43) : Color.WHITE;
    public static final Color FONT_KEYWORD_COLOR = (THEME == 1)? new Color(204, 120, 50) : Color.RED;
    public static final Color FONT_TYPE_COLOR = (THEME == 1)? FONT_KEYWORD_COLOR : Color.GREEN;
    public static final Color FONT_SYMBOL_COLOR = (THEME == 1)? Color.RED : Color.RED;

    // Layout
    public static final int WINDOW_WIDTH = 900;
    public static final int WINDOW_HEIGHT = 700;


    // Custom Button properties
    public static final Font BTN_DEFAULT_FONT = FONT_DEFAULT;
    public static final Color BTN_DEFAULT_COLOR = GREEN_EMERALD;
    public static final Color BTN_DEFAULT_TEXT_COLOR = FONT_DEFAULT_COLOR;
    public static final int BTN_DEFAULT_HEIGHT = 28;
    public static final int BTN_DEFAULT_WIDTH = 200;

    public static final int BORDER_PLUS = 4;// border color
    public static final int HOVER_PLUS = 8; // color change when hovering

    public static final int ICON_HEIGHT = 64;
    public static final int ICON_WIDTH = 64;

    // TextInputs
    public static final Font INPUT_TEXT_FONT = FONT_DEFAULT_MEDIUM;
    public static final int LABELED_MARGIN = 2;


    // Custom Settings
}
