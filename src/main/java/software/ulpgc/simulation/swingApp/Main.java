package software.ulpgc.simulation.swingApp;

import software.ulpgc.simulation.core.PendulumPresenter;
import software.ulpgc.simulation.core.model.Pendulum;
import software.ulpgc.simulation.core.model.Rope;
import software.ulpgc.simulation.core.model.Simulator;


public class Main {
    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        simulator.add(new Pendulum(
                new Rope(50,50, 100),
                Math.PI / 2,
                0,
                100.8
        ));
        SwingCanvas canvas = new SwingCanvas();

        PendulumPresenter presenter = new PendulumPresenter(simulator, canvas);
        MainFrame mainFrame = new MainFrame(canvas);
        presenter.runSimulation();
        mainFrame.setVisible(true);

    }
}
