package dungeonmania.helpers;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import dungeonmania.Entity;
import dungeonmania.staticEntities.Boulder;
import dungeonmania.staticEntities.Door;
import dungeonmania.staticEntities.Portal;
import dungeonmania.staticEntities.SwampTile;
import dungeonmania.staticEntities.Wall;

public class DijstraAlgorithm {
    private static int MaxValue = 100000;
    private Location destination;
    private Location next;
    private Location source;
    private Location upOffset;
    private Location downOffset;
    private int mazeSourceX;
    private int mazeSourceY;
    private int mazeDestinationX;
    private int mazeDestinationY;
    private int hight;
    private int width;

    private Collection<Entity> entities;
    private DijstraPosition[][] maze;
    private HashMap<DijstraPosition, Integer> dist = new HashMap<>();
    private HashMap<DijstraPosition, Portal> portals =  new HashMap<>();

    public DijstraAlgorithm(Location player, DungeonMap dungeonMap, Location enemy) {
        destination = player;
        source = enemy;
        next = enemy;
        upOffset = dungeonMap.gMap().firstKey();
        downOffset = dungeonMap.gMap().lastKey();
        entities = dungeonMap.getAllEntities();
        initializationMaze();
    }

    public Location dijstra() {

        int[][] operate = { { -1, 0 },{ 1, 0 },{ 0, -1 }, { 0, 1 }};
        Queue<DijstraPosition> wait = new LinkedList<DijstraPosition>();

        wait.add(maze[mazeSourceX][mazeSourceY]);
        while (!wait.isEmpty()) {
            DijstraPosition cur = wait.poll();
            if (cur.checkArrive(mazeDestinationX, mazeDestinationY)) {
                outPut(mazeDestinationX, mazeDestinationY);
                break;
            }
            cur.setReachable(false);
            for (int i = 0; i < 4; i++) {
                int nextX = cur.getMazeX() + operate[i][0];
                int nextY = cur.getMazeY() + operate[i][1];
                if (nextX < width && nextY < hight && nextX >= 0 && nextY >= 0) {
                    int cost = maze[nextX][nextY].getCost() + cur.getCost();
                    DijstraPosition target = getTargetPortal(maze[nextX][nextY]);
                    if(target != null){
                        nextX = target.getMazeX() + operate[i][0];
                        nextY = target.getMazeY() + operate[i][1];
                    }
                    if (maze[nextX][nextY].checkReachable() && dist.get(cur) + cost < dist.get(maze[nextX][nextY])) {
                        dist.replace(maze[nextX][nextY], dist.get(cur) + cost);
                        maze[nextX][nextY].setPre(cur);
                        wait.add(maze[nextX][nextY]);
                    }
                }
            }
        }
        return next;
    }

    private void outPut(int x, int y) {
        if (maze[x][y].checkPre()) {
            System.out.println(source.getX() + " , " + source.getY());
            return;
        } else if (maze[x][y].checkNextPre()) {
            next = maze[x][y].getRealLocation();
        }
        outPut(maze[x][y].getPreMazeX(), maze[x][y].getPreMazeY());
        System.out.println(maze[x][y].getRealX() + " , " + maze[x][y].getRealY());
    }

    private void buildMaze(Location real, int cost, boolean reachable) {
        int x = real.getX() - upOffset.getX();
        int y = real.getY() - upOffset.getY();
        maze[x][y] = new DijstraPosition(real, cost, reachable, x, y);
    }

    private void initializationMaze() {
        checkBound();
        mazeSourceX = Math.abs(source.getX() - upOffset.getX());
        mazeSourceY = Math.abs(source.getY() - upOffset.getY());
        mazeDestinationX = Math.abs(destination.getX() - upOffset.getX());
        mazeDestinationY = Math.abs(destination.getY() - upOffset.getY());
        hight = Math.abs(downOffset.getY() - upOffset.getY()) + 10;
        width = Math.abs(downOffset.getX() - upOffset.getX()) + 10;

        maze = new DijstraPosition[width][hight];

        /* Initialize start and end points */
        maze[mazeSourceX][mazeSourceY] = new DijstraPosition(source, 1, false, mazeSourceX, mazeSourceY);
        maze[mazeDestinationX][mazeDestinationY] = new DijstraPosition(destination, 1, true, mazeDestinationX,mazeDestinationY);

        /* Initialize walls and boulders and door and SwampTile and Portal*/
        for (Entity entity : entities) {
            if (entity instanceof Wall || entity instanceof Boulder) {
                buildMaze(entity.getLocation(), MaxValue, false);
            }else if (entity instanceof Door) {
                Door d = (Door) entity;
                if (!d.isOpened()) {
                    buildMaze(entity.getLocation(), MaxValue, false);
                }

            }else if (entity instanceof SwampTile) {
                SwampTile s = (SwampTile) entity;
                buildMaze(entity.getLocation(), s.getMovementFactor()+1, true);
            }else if(entity instanceof Portal){
                Portal p = (Portal) entity;
                buildPortals(p);
            }
        }
        /* Initialize blank position */
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < hight; j++) {
                if (maze[i][j] == null) {
                    buildMaze(new Location(i + upOffset.getX(), j + upOffset.getY()), 1, true);
                }
            }
        }

        /* Initialize distance */
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < hight; j++) {
                dist.put(maze[i][j], MaxValue);
            }
        }
        dist.replace(maze[mazeSourceX][mazeSourceY], 0);
    }

    private void checkBound(){
        if(source.getX() - upOffset.getX() == 0 || destination.getX() - upOffset.getX() == 0 || destination.getY() - upOffset.getY() == 0){
            upOffset = new Location(upOffset.getX() - 10, upOffset.getY() - 10);
        }
    }

    private void buildPortals(Portal p) {
        int x = p.getLocation().getX() - upOffset.getX();
        int y = p.getLocation().getY() - upOffset.getY();
        maze[x][y] = new DijstraPosition(p.getLocation(), 1, true, x, y);
        portals.put(maze[x][y], p);
    }
    
    private DijstraPosition getTargetPortal(DijstraPosition p){
        if(! portals.containsKey(p)){
            return null;
        }else{
            for(DijstraPosition target : portals.keySet()){
                if(!p.equals(target) && portals.get(p).getColour().equals(portals.get(target).getColour())){
                    return target;
                }
            }
        }
        return null;
    }
}
