package software.ulpgc.simulation.swingApp;

import software.ulpgc.simulation.core.view.Circle;
import software.ulpgc.simulation.core.view.Canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class SwingCanvas extends JPanel implements Canvas {
    private final List<Circle> circleList;
    private Drag drag = Drag.Null;
    private Release release = Release.Null;
    private Press press = Press.Null;


    public SwingCanvas() {
        this.addMouseListener(mouseListener());
        this.addMouseMotionListener(mouseMotionListener());
        circleList = new ArrayList<>();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        synchronized (circleList) {
            for (Circle circle : circleList) {
                drawLine(g, circle);
                drawCircle(g, circle);
            }
        }
    }

    @Override
    public void addCircle(Circle circle) {
        synchronized (circleList) {
            circleList.add(circle);
        }
    }

    @Override
    public Canvas clear() {
        synchronized (circleList) {
            this.circleList.clear();
        }
        return this;
    }

    @Override
    public Canvas removeCircle(int id) {
        synchronized (circleList) {
            circleList.removeIf(circle -> circle.id() == id);
        }
        return this;
    }

    @Override
    public int width() {
        return this.getWidth();
    }

    @Override
    public int height() {
        return this.getHeight();
    }

    @Override
    public void update() {
        this.repaint();
    }

    @Override
    public void on(Drag event) {
        this.drag = event != null ? event : Drag.Null;
    }

    @Override
    public void on(Release event) {
        this.release = event != null ? event : Release.Null;
    }

    @Override
    public void on(Press event) {
        this.press = event != null ? event : Press.Null;
    }


    private void drawCircle(Graphics g, Circle circle) {
        g.setColor(circle.color());
        g.fillOval(
                circle.x(),
                circle.y(),
                circle.radius()/2,
                circle.radius()/2
        );
    }

    private void drawLine(Graphics g, Circle circle) {
        g.setColor(Color.WHITE);
        g.drawLine(
                circle.hangingX(),
                circle.hangingY(),
                circle.x() + circle.radius()/4,
                circle.y() + circle.radius()/4
        );

    }

    private MouseListener mouseListener(){
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                press.offset(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                release.offset(
                        e.getX(),
                        e.getY()
                );
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    private MouseMotionListener mouseMotionListener(){
        return new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                drag.offset(
                        e.getX(),
                        e.getY()
                );
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        };
    }
}
