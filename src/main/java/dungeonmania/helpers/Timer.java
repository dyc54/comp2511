package dungeonmania.helpers;

public class Timer {
    private int time;
    public Timer() {
        time = 0;
    }
    public void addTime() {
        time++;
    }
    public int getTime() {
        return time;
    }
}
