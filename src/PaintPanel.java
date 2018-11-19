import theme.SButton;
import theme.SPanel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
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

    private static final Color[] availableColors = {
            Color.BLACK,
            Color.WHITE,
            Color.BLUE,
            Color.RED,
            Color.GREEN
    };
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
        final int TOOLS_WIDTH = 64;
        SPanel toolsPanel = new SPanel();

        toolsPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1,1));
        toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.PAGE_AXIS));

        SButton clearButton = new SButton("C");
        clearButton.addActionListener(e -> clear());
        clearButton.setPreferredSize(new Dimension(TOOLS_WIDTH, TOOLS_WIDTH/2));
        toolsPanel.add(clearButton);

        JPanel colorsPanel = initColorPanel(TOOLS_WIDTH-1, TOOLS_WIDTH/2);

        toolsPanel.add(colorsPanel);

        return toolsPanel;
    }

    private JPanel initColorPanel(int width, int height)
    {
        JPanel colorsPanel = new JPanel();

        colorsPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        colorsPanel.setPreferredSize(new Dimension(width, height*3));
        colorsPanel.setMaximumSize(new Dimension(width, height*3));
        colorsPanel.setLayout(new GridLayout(3, 2));

        SButton[] colorButtons = new SButton[availableColors.length];
        for (int i = 0; i < colorButtons.length; i++)
        {
            final int index = i;
            colorButtons[i] = new SButton("");
            colorButtons[i].addActionListener(e ->
            {
                this.color = availableColors[index];
                this.socketPeerConnection.send(new events.PaintEvent(events.PaintEvent.PaintEventType.COLOR_CHANGE, index));
            });
            colorButtons[i].setBgColor(availableColors[index]);
            colorButtons[i].setPreferredSize(new Dimension(width/2, height));

            colorsPanel.add(colorButtons[i]);
        }

        return colorsPanel;
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
                socketPeerConnection.send(new events.PaintEvent(events.PaintEvent.PaintEventType.DRAG, e.getPoint()));
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
                socketPeerConnection.send(new events.PaintEvent(events.PaintEvent.PaintEventType.PRESSED, e.getPoint()));
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                onRelease(e.getPoint());
                socketPeerConnection.send(new events.PaintEvent(events.PaintEvent.PaintEventType.RELEASED, e.getPoint()));
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
            socketPeerConnection.send(new events.PaintEvent(events.PaintEvent.PaintEventType.CLEAR, null));

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

    public void setColor(int colorIndex)
    {
        this.color = availableColors[colorIndex];
    }
}