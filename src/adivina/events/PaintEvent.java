package adivina.events;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by ${} on 13/11/2018.
 */
public class PaintEvent implements Serializable
{

    public enum PaintEventType {
        PRESSED,
        RELEASED,
        DRAG,
        CLEAR,
        COLOR_CHANGE
    }
    public PaintEventType type;
    public Point point;
    public int index;

    public PaintEvent(PaintEventType type, Point point)
    {
        this.type = type;
        this.point = point;
    }

    public PaintEvent(PaintEventType type, int index)
    {
        this.type = type;
        this.index = index;
    }


}
