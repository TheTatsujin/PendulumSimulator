package software.ulpgc.simulation.core.model;

public record Rope(int x, int y, int length) {
    public static Rope Null() {
        return new Rope(0, 0, 1);
    }
}
