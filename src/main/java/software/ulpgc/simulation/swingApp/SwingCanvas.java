package software.ulpgc.simulation.swingApp;

import software.ulpgc.simulation.core.view.Circle;
import software.ulpgc.simulation.core.view.Canvas;

import javax.swing.*;
import java.awt.Graphics;
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
        circleList.forEach(c -> drawCircle(g, c));
    }

    @Override
    public void addCircle(Circle circle) {
        circleList.add(circle);
    }

    @Override
    public software.ulpgc.simulation.core.view.Canvas clear() {
        this.circleList.clear();
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
        g.fillOval(circle.x(), circle.y(), circle.radius(), circle.radius());
    }
}
