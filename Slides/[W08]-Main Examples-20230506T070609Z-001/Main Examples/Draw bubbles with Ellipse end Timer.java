import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

class Surface extends JPanel implements ActionListener {
    private final Color colors[] = {
            Color.blue, Color.cyan, Color.green,
            Color.magenta, Color.orange, Color.pink,
            Color.red, Color.yellow, Color.lightGray, Color.white
    };

    private Ellipse2D.Float[] ellipses;
    private double esize[];
    private float estroke[];
    private double maxSize = 0;
    private final int NUMBER_OF_ELLIPSES = 25;
    private final int DELAY = 30;
    private final int INITIAL_DELAY = 150;
    private Timer timer;

    public Surface() {

        initSurface();
        initEllipses();
        initTimer();
    }

    private void initSurface() {

        setBackground(Color.black);
        ellipses = new Ellipse2D.Float[NUMBER_OF_ELLIPSES];
        esize = new double[ellipses.length];
        estroke = new float[ellipses.length];
    }

    private void initEllipses() {

        int w = 350;
        int h = 250;

        maxSize = w / 10;

        for (int i = 0; i < ellipses.length; i++) {

            ellipses[i] = new Ellipse2D.Float();
            posRandEllipses(i, maxSize * Math.random(), w, h);
        }
    }

    private void initTimer() {

        timer = new Timer(DELAY, this);
        timer.setInitialDelay(INITIAL_DELAY);
        timer.start();
    }

    private void posRandEllipses(int i, double size, int w, int h) {

        esize[i] = size;
        estroke[i] = 1.0f;
        double x = Math.random() * (w - (maxSize / 2));
        double y = Math.random() * (h - (maxSize / 2));
        ellipses[i].setFrame(x, y, size, size);
    }

    private void doStep(int w, int h) {

        for (int i = 0; i < ellipses.length; i++) {

            estroke[i] += 0.025f;
            esize[i]++;

            if (esize[i] > maxSize) {

                posRandEllipses(i, 1, w, h);
            } else {

                ellipses[i].setFrame(ellipses[i].getX(), ellipses[i].getY(),
                        esize[i], esize[i]);
            }
        }
    }

    private void drawEllipses(Graphics2D g2d) {

        for (int i = 0; i < ellipses.length; i++) {

            g2d.setColor(colors[i % colors.length]);
            g2d.setStroke(new BasicStroke(estroke[i]));
            g2d.draw(ellipses[i]);
        }
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();

        RenderingHints rh
                = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        Dimension size = getSize();
        doStep(size.width, size.height);
        drawEllipses(g2d);

        g2d.dispose();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
}

class TransparencyEx extends JFrame {

    public TransparencyEx() {
        initUI();
    }

    private void initUI() {
        final Surface surface = new Surface();
        add(surface);
        setTitle("Bubbles Example");


        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
class Example19 {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TransparencyEx ex = new TransparencyEx();
                ex.setVisible(true);
            }
        });
    }
}

