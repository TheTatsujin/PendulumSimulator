package software.ulpgc.simulation.core.model;

import java.awt.*;

public record Pendulum(int id, Rope hangingCord, double theta, double omega, double g, int radius) {
    public static Pendulum Null(){
        return new Pendulum(0, Rope.Null(), 0, 0, 0, 0);
    }
}
