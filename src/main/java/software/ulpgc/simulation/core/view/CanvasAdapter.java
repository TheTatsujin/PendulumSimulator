package software.ulpgc.simulation.core.view;

import software.ulpgc.simulation.core.model.Pendulum;


public interface CanvasAdapter {
    Circle pendulumToCircle(Pendulum pendulum);
}
