package software.ulpgc.simulation.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Simulator{
    private List<Pendulum> pendulums;
    public static final double dt = 0.001;
    public static final double damping = 20;


    public Simulator() {
        this.pendulums = new ArrayList<>();
    }

    public Simulator add(Pendulum pendulum) {
        synchronized (pendulums) {
            this.pendulums.add(pendulum);
        }
        return this;
    }

    public void nextFrame() {
        synchronized (pendulums) {
            pendulums = pendulums.stream().map(this::nextState).collect(Collectors.toList());
       }
    }

    public void remove(int id) {
        synchronized (pendulums) {
            pendulums.removeIf(p -> p.id() == id);
        }
    }

    private Pendulum nextState(Pendulum pendulum) {
        return new Pendulum(
                pendulum.id(),
                pendulum.hangingCord(),
                nextTheta(pendulum),
                nextAngularVelocity(pendulum),
                pendulum.g(),
                pendulum.radius(),
                pendulum.color()
        );
    }

    private static double nextTheta(Pendulum pendulum) {
        return pendulum.theta() + nextAngularVelocity(pendulum) * dt;
    }

    private static double nextAngularVelocity(Pendulum pendulum) {
        return pendulum.omega() + nextAngularAcceleration(pendulum) * dt * damping;
    }

    private static double nextAngularAcceleration(Pendulum pendulum) {
        return -(pendulum.g() / pendulum.hangingCord().length()) * Math.sin(pendulum.theta());
    }
    
    public List<Pendulum> pendulums() {
        return new ArrayList<>(pendulums);
    }
}
