package software.ulpgc.simulation.core.view;

public interface Canvas {
    void addCircle(Circle circle);
    Canvas clear();
    Canvas removeCircle(int id);
    int width();
    int height();
    void update();


    void on(Drag event);
    void on(Release event);
    void on(Press event);

    interface Drag {
        Drag Null = (_,_) -> {};
        void offset(int x, int y);
    }
    interface Release {
        Release Null = (_,_) -> {};
        void offset(int x, int y);
    }
    interface Press {
        Press Null = (_, _) -> {};
        void offset(int x, int y);
    }

}
