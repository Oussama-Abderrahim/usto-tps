import java.awt.*;
import java.io.Serializable;

/**
 * Created by ${} on 13/11/2018.
 */
public class PaintEvent implements Serializable
{
    enum PaintEventType {
        PRESSED,
        RELEASED,
        DRAG,
        CLEAR
    }

    public PaintEventType type;
    public Point point;

    public PaintEvent(PaintEventType type, Point point)
    {
        this.type = type;
        this.point = point;
    }


}
