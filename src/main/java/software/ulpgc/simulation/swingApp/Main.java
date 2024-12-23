package software.ulpgc.simulation.swingApp;

import software.ulpgc.simulation.core.PendulumPresenter;
import software.ulpgc.simulation.core.model.Pendulum;
import software.ulpgc.simulation.core.model.Rope;
import software.ulpgc.simulation.core.model.Simulator;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Simulator simulator = new Simulator()
                .add(getPendulum(2, 0, 0, 520, 50.8, 50, Color.CYAN))
                .add(getPendulum(3, 0, 0, 100, 50.8, 50, Color.PINK));

        SwingCanvas canvas = new SwingCanvas();
        /*
        I'm forced to declare it as SwingCanvas, since the MainFrame
        needs it to have paintComponent() method on the definition.
        */
        new MainFrame(canvas).setVisible(true);
        new PendulumPresenter(simulator, canvas).runSimulation();
    }

    public static Pendulum getPendulum(int id, int x, int y, int cordLength, double g, int r, Color color) {
        return new Pendulum(id, new Rope(x, y, cordLength), 0, 0, g, r, color);
    }
}
