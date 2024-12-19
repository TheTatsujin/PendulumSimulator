package software.ulpgc.simulation.swingApp;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame(SwingCanvas canvas) {
        this.add(canvas);
        init();
    }

    public void init() {
        this.setTitle("Pendulum Simulation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
    }

}
