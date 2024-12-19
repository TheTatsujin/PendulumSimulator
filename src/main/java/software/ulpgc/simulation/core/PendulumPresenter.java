package software.ulpgc.simulation.core;

import software.ulpgc.simulation.core.model.Pendulum;
import software.ulpgc.simulation.core.model.Simulator;
import software.ulpgc.simulation.core.view.Canvas;
import software.ulpgc.simulation.core.view.CanvasAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class PendulumPresenter {
    private final CanvasAdapter adapter;
    private final Simulator simulator;
    private final Canvas canvas;


    public PendulumPresenter(CanvasAdapter adapter, Simulator simulator, Canvas canvas) {
        this.adapter = adapter;
        this.simulator = simulator;
        this.canvas = canvas;
    }

    public void runSimulation() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                canvas.clear();
                simulator.nextFrame();
                addCircles();
                canvas.update();
            }
        }, 0, (long) (Simulator.dt * 1000));
    }

    public void addCircles(){
        for (Pendulum pendulum : simulator) {
            canvas.addCircle(adapter.pendulumToCircle(pendulum));
        }
    }



}
