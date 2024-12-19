package software.ulpgc.simulation.swingApp;

import software.ulpgc.simulation.core.view.Circle;
import software.ulpgc.simulation.core.view.Canvas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SwingCanvas extends JPanel implements Canvas {
    private final List<Circle> circleList;

    public SwingCanvas() {
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
}
