package software.ulpgc.simulation.core.model;

import java.util.List;
import java.util.stream.Collectors;

public class Simulator{
    public static final double dt = 0.001;
    public static final double damping = 20;

    private Simulator() {}

    public static Pendulum immobile(Pendulum p) {
        return new Pendulum(p.id(), p.hangingCord(), p.theta(), 0, 0, p.radius(), p.color());
    }

    public static List<Pendulum> nextFrame(List<Pendulum> pendulums) {
            return pendulums.stream().map(Simulator::nextState).collect(Collectors.toList());
    }

    private static Pendulum nextState(Pendulum p) {
        return new Pendulum(p.id(), p.hangingCord(), nextTheta(p), nextAngularVelocity(p), p.g(), p.radius(), p.color());
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
    
}
