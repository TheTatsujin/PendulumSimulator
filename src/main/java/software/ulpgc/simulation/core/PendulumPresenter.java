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
        for (Pendulum pendulum : simulator.pendulums()) {
            canvas.addCircle(adapter.pendulumToCircle(pendulum));
        }
    }

    private void dragEvent(int x, int y) {
        if (draggingID == 0) return;
        canvas.removeCircle(draggingID);
        grabPendulum(draggedPendulum, x, y);
        canvas.addCircle(adapter.pendulumToCircle(draggedPendulum));
    }

    private void releaseEvent(int x, int y) {
        if (draggingID == 0) return;
        simulator.remove(draggingID);
        simulator.add(draggedPendulum);
        draggedPendulum = Pendulum.Null();
        draggingID = 0;
    }

    private void pressEvent(int x, int y) {

        for (Pendulum pendulum : simulator.pendulums()) {
            if (isPressed(pendulum, x, y)){
                draggedPendulum = pendulum;
                draggingID = pendulum.id();
                grabPendulum(pendulum, x, y);
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

    private void grabPendulum(Pendulum pendulum, int x, int y) {
        simulator.remove(pendulum.id());
        draggedPendulum = adapter.movePendulumTo(pendulum, x, y);
        simulator.add(new Pendulum(
                draggedPendulum.id(),
                draggedPendulum.hangingCord(),
                draggedPendulum.theta(),
                0,
                0,
                draggedPendulum.radius(),
                pendulum.color()
        ));
    }

}
