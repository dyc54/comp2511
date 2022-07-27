package dungeonmania.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.staticEntities.Boulder;
import dungeonmania.staticEntities.Wall;

 /* function Dijkstras(grid, source):
    let dist be a Map<Position, Double>
    let prev be a Map<Position, Position>

    for each Position p in grid:
        dist[p] := infinity
        previous[p] := null
    dist[source] := 0

    let queue be a Queue<Position> of every position in grid
    while queue is not empty:
        u := next node in queue with the smallest dist
        for each cardinal neighbour v of u:
            if dist[u] + cost(u, v) < dist[v]:
                dist[v] := dist[u] + cost(u, v)
                previous[v] := u
    return previou */

public class DijstraAlgorithm {

    private static int MaxValue = 100000;
    private int vertex;
    private Location destination;
    private Location next;
    private Location source;
    private Collection<Entity> entities;
    private DijstraPosition[][] maze;

    private HashMap<DijstraPosition, Integer> dist = new HashMap<>();

    public DijstraAlgorithm(Player player, DungeonMap dungeonMap, int vertex, Entity enemy){
        destination = player.getLocation();
        source = enemy.getLocation();
        this.vertex = vertex;
        maze = new DijstraPosition[vertex][vertex];
        entities = dungeonMap.getAllEntities();
    }

    public void initializationMaze(){
        
        /* Initialize start and end points */
        maze[source.getX()][source.getY()] = new DijstraPosition(source.getX(),source.getY(),1,false);
        maze[destination.getX()][destination.getY()] = new DijstraPosition(destination.getX(),destination.getY(),1,true);

        /* Initialize walls and boulders  and door */
        for(Entity entity : entities){
            if(entity instanceof Wall || entity instanceof Boulder){
                int x  = entity.getLocation().getX();
                int y  = entity.getLocation().getY();
                maze[x][y] = new DijstraPosition(x, y,MaxValue,false);
            }
        }

        /* Initialize blank position */
        for (int i = 0; i < vertex; i++) {
            for (int j = 0; j < vertex; j++) {
                if(maze[i][j] == null){
                    maze[i][j] = new DijstraPosition(i, j,1,true);
                }
            }
        }

        /* Initialize distance */
        for (int i = 0; i < vertex; i++) {
            for (int j = 0; j < vertex; j++) {
                dist.put(maze[i][j], MaxValue);
            }
        }
        dist.replace( maze[source.getX()][source.getY()], 0);
    }

    public Location dijstra(){


        initializationMaze();
        int[][] operate = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };
        Queue<DijstraPosition> wait = new LinkedList<DijstraPosition>();
        
        wait.add(maze[source.getX()][source.getY()]);
        while (!wait.isEmpty()) {
            DijstraPosition cur = wait.poll();
            /* outPut(cur.x, cur.y); */
            if (cur.x == destination.getX() && cur.y == destination.getY()) {
                outPut(destination.getX(), destination.getY());
                break;
            }
            cur.reachable = false;
            for (int i = 0; i < 4; i++) {
                int nextX = cur.x + operate[i][0];
                int nextY = cur.y + operate[i][1];
                int cost = maze[nextX][nextY].cost + cur.cost;
                if (maze[nextX][nextY].reachable && dist.get(cur) + cost < dist.get(maze[nextX][nextY])) {
                    dist.replace(maze[nextX][nextY], dist.get(cur) + cost);
                    wait.add(maze[nextX][nextY]);
                    maze[nextX][nextY].pre = cur;
                }
            }
        }

        return next;
    }

    public void outPut(int x, int y) {
        if (maze[x][y].pre.pre == null) {
            System.out.println(maze[x][y].x);
            System.out.println(maze[x][y].y);
            next = new Location(x, y);
        }
        outPut(maze[x][y].pre.x, maze[x][y].pre.y);
        System.out.println("(" + x + ", " + y + ")");
    }
    
}

class DijstraPosition {
    int x;
    int y;
    int cost;
    boolean reachable;
    DijstraPosition pre;

    public DijstraPosition(int x, int y,int cost,boolean reachable) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.reachable = reachable;
    }
}
