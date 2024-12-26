package software.ulpgc.simulation.core;

import software.ulpgc.simulation.core.model.Pendulum;
import software.ulpgc.simulation.core.model.Simulator;
import software.ulpgc.simulation.core.view.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PendulumPresenter {
    private List<Pendulum> pendulums;
    private final CanvasAdapter adapter;
    private final Canvas canvas;
    private Pendulum draggedPendulum = Pendulum.Null();
    private int draggingID = 0;


    public PendulumPresenter(Canvas canvas) {
        pendulums = new ArrayList<>();
        this.adapter = new CanvasAdapter(canvas);
        this.canvas = canvas;
        this.canvas.on((Canvas.Drag) this::dragEvent);
        this.canvas.on((Canvas.Press) this::pressEvent);
        this.canvas.on((Canvas.Release) this::releaseEvent);
    }


    public void runSimulation() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                canvas.clear();
                pendulums = Simulator.nextFrame(new ArrayList<>(pendulums));
                addCircles();
                canvas.update();
            }
        }, 0, (long) (Simulator.dt * 1000));
    }

    public void addCircles(){
        for (Pendulum pendulum : pendulums)
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
        this.remove(draggingID);
        this.add(draggedPendulum);
        this.updateDraggedPendulum(0, Pendulum.Null());
    }

    private void pressEvent(int x, int y) {
        for (Pendulum pendulum : pendulums){
            if (adapter.isPressed(pendulum, x, y)) {
                updateDraggedPendulum(pendulum.id(), pendulum);
                movePendulumTo(pendulum, x, y);
                break;
            }
        }
    }

    private void updateDraggedPendulum(int id, Pendulum pendulum) {
        this.draggedPendulum = pendulum;
        this.draggingID = id;
    }

    private void movePendulumTo(Pendulum pendulum, int x, int y) {
        this.remove(pendulum.id());
        draggedPendulum = adapter.movePendulumTo(pendulum, x, y);
        this.add(Simulator.immobile(draggedPendulum));
    }

    public PendulumPresenter add(Pendulum pendulum) {
        pendulums.add(pendulum);
        return this;
    }

    public void remove(int id) {
        pendulums.removeIf(p -> p.id() == id);
    }
}
