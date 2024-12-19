package software.ulpgc.simulation.core;

import software.ulpgc.simulation.core.model.Pendulum;
import software.ulpgc.simulation.core.view.Circle;
import software.ulpgc.simulation.core.view.Canvas;

import java.awt.*;

public class CanvasAdapter {
    private final Canvas canvas;

    public CanvasAdapter(Canvas canvas) {
        this.canvas = canvas;
    }

    public Circle pendulumToCircle(Pendulum pendulum) {
        System.out.println("pendulum: (" + calculateComponentX(pendulum) + ", " + calculateComponentY(pendulum) + ")");
        System.out.println("Theta: " + pendulum.theta());
        return new Circle(
                adaptCoordinateX(calculateComponentX(pendulum)),
                adaptCoordinateY(calculateComponentY(pendulum)),
                50,
                Color.PINK
        );
    }

    private int adaptCoordinateX(int x){
        return canvas.width() / 2 + x;
    }

    private int adaptCoordinateY(int y){
        return canvas.height() / 2 + y;
    }

    private int calculateComponentX(Pendulum pendulum) {
        return (int) (Math.sin(pendulum.theta()) * pendulum.hangingCord().length());
    }

    private int calculateComponentY(Pendulum pendulum) {
        return (int) (Math.cos(pendulum.theta()) * pendulum.hangingCord().length());
    }
}
