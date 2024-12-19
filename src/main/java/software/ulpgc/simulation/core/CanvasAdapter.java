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
        return new Circle(
                pendulum.id(),
                adaptCoordinateX(calculateComponentX(pendulum), pendulum.radius()),
                adaptCoordinateY(calculateComponentY(pendulum), pendulum.radius()),
                pendulum.radius(),
                Color.PINK,
                adaptCoordinateX(pendulum.hangingCord().x(), 0),
                adaptCoordinateY(pendulum.hangingCord().y(), 0)
        );
    }
    private int adaptCoordinateX(int x, int radius){
        return  (canvas.width() / 2) + x - radius / 4;
    }

    private int adaptCoordinateY(int y, int radius){
        return canvas.height() / 20 + y - radius / 4;
    }

    private int calculateComponentX(Pendulum pendulum) {
        return (int) (Math.sin(pendulum.theta()) * pendulum.hangingCord().length());
    }

    private int calculateComponentY(Pendulum pendulum) {
        return (int) (Math.cos(pendulum.theta()) * pendulum.hangingCord().length());
    }


    public Pendulum movePendulumTo(Pendulum pendulum, int x, int y) {
        return new Pendulum(
                pendulum.id(),
                pendulum.hangingCord(),
                Math.atan2(x, y),
                0,
                pendulum.g(),
                pendulum.radius()
        );
    }




}
