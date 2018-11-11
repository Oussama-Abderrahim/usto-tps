import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class PaintPanel extends JPanel
{
    public final static int PAINT_WIDTH = 400;
    public final static int PAINT_HEIGHT = 400;

    private Point selectionStartPoint = new Point(0, 0);
    private Point selectionEndPoint = new Point(0, 0);

    private BufferedImage canvasImage;

    private Color color = Color.BLUE;
    private Color bgColor = Color.white;

    private Stroke stroke = new BasicStroke(
            5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.7f);

    private SocketPeerConnection socketPeerConnection;

    public PaintPanel(SocketPeerConnection socketPeerConnection)
    {
        super();
        this.socketPeerConnection = socketPeerConnection;

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.canvasImage = new BufferedImage(PaintPanel.PAINT_WIDTH, PaintPanel.PAINT_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        this.setupMouseListeners();
        this.clear();
//        this.setEnabled(false);
    }

    private void setupMouseListeners()
    {
        this.addMouseMotionListener(new MouseMotionListener()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
//                endPoint = e.getPoint();
//                repaint();
                draw(e.getPoint());
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {

            }
        });
        this.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

            }

            @Override
            public void mousePressed(MouseEvent e)
            {
//                startPoint = e.getPoint();
//                endPoint = e.getPoint();
//                repaint();

                draw(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
//                selectionEndPoint = e.getPoint();
                draw(e.getPoint());
                lastPoint = null;
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {

            }

            @Override
            public void mouseExited(MouseEvent e)
            {

            }
        });

    }

    public void clear()
    {
        Graphics2D g = this.canvasImage.createGraphics();
        g.setColor(this.bgColor);
        g.setStroke(stroke);
        g.fillRect(0, 0, canvasImage.getWidth(), canvasImage.getHeight());

        g.dispose();
        this.repaint();
    }

    private Point lastPoint = null;

    public void draw(Point point)
    {
        if (!this.isEnabled()) return;

        Graphics2D g = this.canvasImage.createGraphics();
        g.setColor(this.color);
        g.setStroke(stroke);

        int x = point.x * canvasImage.getWidth() / this.getWidth();
        int y = point.y * canvasImage.getHeight() / this.getHeight();

        int x2 = x;
        int y2 = y;

        if (lastPoint != null)
        {
            x2 = lastPoint.x * canvasImage.getWidth() / this.getWidth();
            y2 = lastPoint.y * canvasImage.getHeight() / this.getHeight();
        }

        g.drawLine(x, y, x2, y2);

        g.dispose();
        this.repaint();

        this.lastPoint = point;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (canvasImage == null) return;

        g.drawImage(canvasImage, 0, 0, getWidth(), getHeight(), null);

//        g.drawOval(
//                (int) Math.min(startPoint.getX(), endPoint.getX()),
//                (int) Math.min(startPoint.getY(), endPoint.getY()),
//                (int) Math.abs(endPoint.getX() - startPoint.getX()),
//                (int) Math.abs(endPoint.getY() - startPoint.getY())
//        );
    }
}