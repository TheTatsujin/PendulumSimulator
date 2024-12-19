package software.ulpgc.simulation.core.view;

import java.awt.*;

public record Circle(int x, int y, int radius, Color color, int hangingX, int hangingY) {
    @Override
    public String toString() {
        return "Circle{" +
                "x=" + x +
                ", y=" + y +
                ", radius=" + radius +
                ", color=" + color +
                '}';
    }
}
