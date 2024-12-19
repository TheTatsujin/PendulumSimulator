package software.ulpgc.simulation.swingApp;

import software.ulpgc.simulation.core.view.Canvas;
import software.ulpgc.simulation.core.view.Circle;

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
        circleList.forEach(c -> drawCircle(g, c));
    }

    @Override
    public void addCircle(Circle circle) {
        circleList.add(circle);
    }

    @Override
    public Canvas clear() {
        this.circleList.clear();
        return this;
    }

    private void drawCircle(Graphics g, Circle circle) {
        g.setColor(circle.color());
        g.fillOval(circle.x(), circle.y(), circle.radius(), circle.radius());
    }
}
