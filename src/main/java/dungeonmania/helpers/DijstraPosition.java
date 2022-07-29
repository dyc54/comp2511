package dungeonmania.helpers;

public class DijstraPosition {
    private final int mazeX;
    private final int mazeY;
    private final int realX;
    private final int realY;
    private final int cost;
    private boolean reachable;
    private DijstraPosition pre;

    public DijstraPosition(Location real,int cost,boolean reachable,int x,int y) {
        this.realX = real.getX();
        this.realY = real.getY();
        this.cost = cost;
        this.mazeX  = x;
        this.mazeY  = y;
        this.reachable = reachable;
    }

    public int getCost() {
        return cost;
    }

    public int getMazeX() {
        return mazeX;
    }

    public int getMazeY() {
        return mazeY;
    }

    public int getRealX() {
        return realX;
    }

    public int getRealY() {
        return realY;
    }

    public void setPre(DijstraPosition pre) {
        this.pre = pre;
    }

    public DijstraPosition getPre() {
        return pre;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }

    public boolean checkReachable() {
        return reachable;
    }

    public boolean checkPre() {
        return pre == null;
    }

    public boolean checkNextPre() {
        return pre.pre == null;
    }

    public int getPreMazeX() {
        return pre.mazeX;
    }

    public int getPreMazeY() {
        return pre.mazeY;
    }

    public boolean checkArrive(int x, int y){
        return mazeX == x && mazeY == y;
    }

    public Location getRealLocation(){
        return new Location(realX, realY);
    }
    
}
