package software.ulpgc.simulation.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Simulator implements Iterable<Pendulum>{
    private List<Pendulum> pendulums;
    public static final double dt = 0.001;
    public static final double damping = 20;


    public Simulator() {
        this.pendulums = new ArrayList<>();
    }

    public Simulator add(Pendulum pendulum) {
        this.pendulums.add(pendulum);
        return this;
    }

    public void nextFrame() {
        pendulums = pendulums.stream()
                .map(this::nextState)
                .collect(Collectors.toList());
    }



    public Pendulum getPendulum(int id) {
      return pendulums.stream()
              .filter(p -> p.id() == id)
              .findFirst()
              .orElse(Pendulum.Null());
    };

    public void remove(Pendulum target) {
        pendulums.remove(target);
    }

    private Pendulum nextState(Pendulum pendulum) {
        double newOmega = pendulum.omega() + getAngularAcceleration(pendulum) * dt * damping;

        double newTheta = pendulum.theta() + newOmega * dt;

        return new Pendulum(pendulum.id(), pendulum.hangingCord(), newTheta, newOmega, pendulum.g(), pendulum.radius());
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
