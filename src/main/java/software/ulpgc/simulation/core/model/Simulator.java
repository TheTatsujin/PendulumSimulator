package software.ulpgc.simulation.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Simulator implements Iterable<Pendulum>{
    private final List<Pendulum> pendulums;
    public static final double dt = 0.001;

    public Simulator() {
        this.pendulums = new ArrayList<>();
    }

    public Simulator add(Pendulum pendulum) {
        this.pendulums.add(pendulum);
        return this;
    }

    public void nextFrame() {
        pendulums.forEach(g -> g = nextState(g));
    }

    private Pendulum nextState(Pendulum pendulum) {
        double speed = pendulum.speed() + getAngularAcceleration(pendulum) * dt;
        return new Pendulum(pendulum.hangingCord(), pendulum.theta() + speed * dt, speed, pendulum.g());
    }

    private static double getAngularAcceleration(Pendulum pendulum) {
        return -(pendulum.g() / pendulum.hangingCord().length()) * Math.sin(pendulum.theta());
    }

    @Override
    public Iterator<Pendulum> iterator() {
        return new Iterator<>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < pendulums.size();
            }

            @Override
            public Pendulum next() {
                return pendulums.get(index++);
            }
        };
    }

}
