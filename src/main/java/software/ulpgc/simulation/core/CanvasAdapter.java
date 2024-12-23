package software.ulpgc.simulation.core;

import software.ulpgc.simulation.core.model.Pendulum;
import software.ulpgc.simulation.core.view.Circle;
import software.ulpgc.simulation.core.view.Canvas;


public class CanvasAdapter {
    private final Canvas canvas;

    public CanvasAdapter(Canvas canvas) {
        this.canvas = canvas;
    }

    public Circle pendulumToCircle(Pendulum pendulum) {
        return new Circle(
                pendulum.id(),
                coordinateToPixelX(calculateComponentX(pendulum), pendulum.radius()),
                coordinateToPixelY(calculateComponentY(pendulum), pendulum.radius()),
                pendulum.radius(),
                pendulum.color(),
                coordinateToPixelX(pendulum.hangingCord().x(), 0),
                coordinateToPixelY(pendulum.hangingCord().y(), 0)
        );
    }
    private int coordinateToPixelX(int x, int radius){
        return  (canvas.width() / 2) + x - radius / 4;
    }

    private int coordinateToPixelY(int y, int radius){
        return (canvas.height() / 20) + y - radius / 4;
    }

    private int pixelToCoordinateX(int pixelX, int radius){
        return pixelX - (canvas.width() / 2) + radius / 4;
    }

    private int pixelToCoordinateY(int pixelY, int radius){
        return pixelY - (canvas.height() / 20) + radius / 4;
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
                findTheta(x, y, pendulum.radius()),
                0,
                pendulum.g(),
                pendulum.radius(),
                pendulum.color()
        );
    }

    private double findTheta(int x, int y, int radius) {
        return Math.atan2(
                pixelToCoordinateX(x, radius),
                pixelToCoordinateY(y, radius)
        );
    }


    public boolean isPressed(Pendulum pendulum, int x, int y) {
        Circle circle = pendulumToCircle(pendulum);
        return isInsideCircleWidth(circle, x) && isInsideCircleHeight(circle, y);
    }

    private boolean isInsideCircleWidth(Circle circle, int x) {
        return circle.x() + circle.radius() / 2 >= x && circle.x() - circle.radius() / 2 <= x;
    }

    private boolean isInsideCircleHeight(Circle circle, int y) {
        return circle.y() + circle.radius() / 2 >= y && circle.y() - circle.radius() / 2 <= y;
    }


}
