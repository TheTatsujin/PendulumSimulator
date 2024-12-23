package software.ulpgc.simulation.core;

import software.ulpgc.simulation.core.model.Pendulum;
import software.ulpgc.simulation.core.model.Simulator;
import software.ulpgc.simulation.core.view.Canvas;

import java.util.Timer;
import java.util.TimerTask;

public class PendulumPresenter {
    private final CanvasAdapter adapter;
    private final Simulator simulator;
    private final Canvas canvas;
    private Pendulum draggedPendulum = Pendulum.Null();
    private int draggingID = 0;


    public PendulumPresenter(Simulator simulator, Canvas canvas) {
        this.adapter = new CanvasAdapter(canvas);
        this.simulator = simulator;
        this.canvas = canvas;
        this.canvas.on((Canvas.Drag) this::dragEvent);
        this.canvas.on((Canvas.Press) this::pressEvent);
        this.canvas.on((Canvas.Release) this::releaseEvent);
    }


    public PendulumPresenter runSimulation() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                canvas.clear();
                simulator.nextFrame();
                addCircles();
                canvas.update();
            }
        }, 0, (long) (Simulator.dt * 1000));
        return this;
    }

    public void addCircles(){
        for (Pendulum pendulum : simulator.pendulums())
            canvas.addCircle(adapter.pendulumToCircle(pendulum));
    }

    private void dragEvent(int x, int y) {
        if (draggingID == 0) return;
        canvas.removeCircle(draggingID);
        movePendulumTo(draggedPendulum, x, y);
        canvas.addCircle(adapter.pendulumToCircle(draggedPendulum));
    }

    private void releaseEvent(int x, int y) {
        if (draggingID == 0) return;
        simulator.remove(draggingID);
        simulator.add(draggedPendulum);
        updateDraggedPendulum(0, Pendulum.Null());
    }

    private void pressEvent(int x, int y) {
        simulator.pendulums().stream()
                .filter(p -> adapter.isPressed(p, x, y))
                .forEach(p -> {
                    updateDraggedPendulum(p.id(), p);
                    movePendulumTo(p, x, y);
                });
    }

    private void updateDraggedPendulum(int id, Pendulum pendulum) {
        this.draggedPendulum = pendulum;
        this.draggingID = id;
    }

    private void movePendulumTo(Pendulum pendulum, int x, int y) {
        simulator.remove(pendulum.id());
        draggedPendulum = adapter.movePendulumTo(pendulum, x, y);
        simulator.add(Simulator.immobile(draggedPendulum));
    }


}
