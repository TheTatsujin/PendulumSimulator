package software.ulpgc.simulation.swingApp;

import software.ulpgc.simulation.core.model.Pendulum;
import software.ulpgc.simulation.core.view.CanvasAdapter;
import software.ulpgc.simulation.core.view.Circle;

import java.awt.*;

public class SwingCanvasAdapter implements CanvasAdapter {
    private final SwingCanvas swingCanvas;

    public SwingCanvasAdapter(SwingCanvas swingCanvas) {
        this.swingCanvas = swingCanvas;
    }

    @Override
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
        return swingCanvas.getWidth() / 2 + x;
    }

    private int adaptCoordinateY(int y){
        return swingCanvas.getHeight() / 2 + y;
    }

    private int calculateComponentX(Pendulum pendulum) {
        return (int) (Math.sin(pendulum.theta()) * pendulum.hangingCord().length());
    }

    private int calculateComponentY(Pendulum pendulum) {
        return (int) (Math.cos(pendulum.theta()) * pendulum.hangingCord().length());
    }
}
