package software.ulpgc.simulation.core;

import software.ulpgc.simulation.core.model.Pendulum;
import software.ulpgc.simulation.core.model.Simulator;
import software.ulpgc.simulation.core.view.Canvas;
import software.ulpgc.simulation.core.view.Circle;

import java.util.Timer;
import java.util.TimerTask;

public class PendulumPresenter {
    private final CanvasAdapter adapter;
    private final Simulator simulator;
    private final Canvas canvas;
    private Timer timer;
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


    public void runSimulation() {
        this.timer = new Timer();
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

    private void dragEvent(int x, int y) {
        if (draggingID == 0) return;

        canvas.removeCircle(draggingID);
        draggedPendulum = adapter.movePendulumTo(draggedPendulum, x, y);
        canvas.addCircle(adapter.pendulumToCircle(draggedPendulum));
        canvas.update();
    }

    private void releaseEvent(int x, int y) {
        if (draggingID == 0) return;

        simulator.add(draggedPendulum);
        draggedPendulum = Pendulum.Null();
        draggingID = 0;
        this.runSimulation();
    }

    private void pressEvent(int x, int y) {
        for (Pendulum pendulum : simulator) {
            if (isPressed(pendulum, x, y)){
                timer.cancel();
                draggedPendulum = pendulum;
                draggingID = pendulum.id();
                simulator.remove(pendulum);
            }
        }
    }

    private boolean isPressed(Pendulum pendulum, int x, int y) {
        Circle circle = adapter.pendulumToCircle(pendulum);
        return isInsideWidth(circle, x) && isInsideHeight(circle, y);
    }

    private boolean isInsideWidth(Circle circle, int x) {
        return circle.x() + circle.radius() / 2 >= x && circle.x() - circle.radius() / 2 <= x;
    }

    private boolean isInsideHeight(Circle circle, int y) {
        return circle.y() + circle.radius() / 2 >= y && circle.y() - circle.radius() / 2 <= y;
    }

}
