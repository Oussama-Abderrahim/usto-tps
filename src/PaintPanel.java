import theme.SButton;
import theme.SPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

    private Point lastDrawnPoint = null;

    private Stroke stroke = new BasicStroke(
            5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.7f);

    private SocketPeerConnection socketPeerConnection = null;

    private JPanel canvasPanel;
    private JPanel toolsPanel;

    public PaintPanel(SocketPeerConnection socketPeerConnection)
    {
        super();
        this.canvasPanel = initCanvasPanel();

        this.toolsPanel = initToolsPanel();
        ;

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setLayout(new BorderLayout());
        this.add(canvasPanel, BorderLayout.CENTER);
        this.add(toolsPanel, BorderLayout.EAST);

        this.socketPeerConnection = socketPeerConnection;

        this.canvasImage = new BufferedImage(PaintPanel.PAINT_WIDTH, PaintPanel.PAINT_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        this.clear();
        this.setEnabled(false);
    }

    private JPanel initCanvasPanel()
    {
        return new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                if (canvasImage == null) return;

                g.drawImage(canvasImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
    }

    private SPanel initToolsPanel()
    {
        SPanel toolsPanel = new SPanel();

        toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.Y_AXIS));

        SButton clearButton = new SButton("C");
        clearButton.addActionListener(e -> clear());
        clearButton.setSize(8, clearButton.getHeight());
        clearButton.setPreferredSize(new Dimension(32, clearButton.getHeight()));

        toolsPanel.add(clearButton);

        return toolsPanel;
    }

    public void start(SocketPeerConnection socketPeerConnection)
    {
        this.socketPeerConnection = socketPeerConnection;
        this.setEnabled(true);
        this.setupMouseListeners();
        this.clear();
    }

    private void setupMouseListeners()
    {
        this.canvasImage = new BufferedImage(canvasPanel.getWidth(), canvasPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        canvasPanel.addMouseMotionListener(new MouseMotionListener()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                onDrag(e.getPoint());
                socketPeerConnection.send(new PaintEvent(PaintEvent.PaintEventType.DRAG, e.getPoint()));
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
            }
        });
        canvasPanel.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                onPress(e.getPoint());
                socketPeerConnection.send(new PaintEvent(PaintEvent.PaintEventType.PRESSED, e.getPoint()));
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                onRelease(e.getPoint());
                socketPeerConnection.send(new PaintEvent(PaintEvent.PaintEventType.RELEASED, e.getPoint()));
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
        if (this.socketPeerConnection != null)
            socketPeerConnection.send(new PaintEvent(PaintEvent.PaintEventType.CLEAR, null));

        Graphics2D g = this.canvasImage.createGraphics();
        g.setColor(this.bgColor);
        g.setStroke(stroke);
        g.fillRect(0, 0, canvasImage.getWidth(), canvasImage.getHeight());

        g.dispose();
        this.repaint();
    }


    public void onPress(Point point)
    {
        if (point != null)
        {
            draw(point);
        }
    }

    public void onDrag(Point point)
    {
        if (point != null)
        {
            draw(point);
        }
    }

    public void onRelease(Point point)
    {
        if (point != null)
        {
            draw(point);
            lastDrawnPoint = null;
        }
    }

    public void draw(Point point)
    {
        Graphics2D g = this.canvasImage.createGraphics();
        g.setColor(this.color);
        g.setStroke(stroke);

        int x = point.x * canvasImage.getWidth() / canvasPanel.getWidth();
        int y = point.y * canvasImage.getHeight() / canvasPanel.getHeight();

        int x2 = x;
        int y2 = y;

        if (lastDrawnPoint != null)
        {
            x2 = lastDrawnPoint.x * canvasImage.getWidth() / canvasPanel.getWidth();
            y2 = lastDrawnPoint.y * canvasImage.getHeight() / canvasPanel.getHeight();
        }

        g.drawLine(x, y, x2, y2);

        g.dispose();
        canvasPanel.repaint();

        this.lastDrawnPoint = point;
    }
}