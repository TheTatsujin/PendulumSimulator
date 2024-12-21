package software.ulpgc.simulation.swingApp;

import software.ulpgc.simulation.core.PendulumPresenter;
import software.ulpgc.simulation.core.model.Pendulum;
import software.ulpgc.simulation.core.model.Rope;
import software.ulpgc.simulation.core.model.Simulator;

import java.awt.*;


public class Main {
    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        simulator.add(new Pendulum(
                2,
                new Rope(0,0, 350),
                0,
                0,
                50.8,
                85,
                Color.PINK
        ));

        simulator.add(new Pendulum(
                3,
                new Rope(0, 0, 100),
                0,
                0,
                50.8,
                80,
                Color.lightGray
        ));

        SwingCanvas canvas = new SwingCanvas();

        PendulumPresenter presenter = new PendulumPresenter(simulator, canvas);
        MainFrame mainFrame = new MainFrame(canvas);
        presenter.runSimulation();
        mainFrame.setVisible(true);
    }
}
