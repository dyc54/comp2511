package dungeonmania.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.staticEntities.Boulder;
import dungeonmania.staticEntities.Door;
import dungeonmania.staticEntities.SwampTile;
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
    private Location destination;
    private Location next;
    private Location source;
    private Collection<Entity> entities;
    private DijstraPosition[][] maze;
    private TreeMap<Location, HashSet<Entity>> map;
    private int sourceX;
    private int sourceY;
    private int hight;
    private int width;

    private int destinationX ;
    private int destinationY;

    private HashMap<DijstraPosition, Integer> dist = new HashMap<>();

    public DijstraAlgorithm(Location player, DungeonMap dungeonMap, Location enemy){
        destination = player;
        source = enemy;
        map = dungeonMap.gMap();
        entities = dungeonMap.getAllEntities();
        sourceX  = getMazeX(source.getX());
        sourceY  = getMazeY(source.getY());
        destinationX  = getMazeX(destination.getX());
        destinationY  = getMazeX(destination.getY());
    }

    private int getMazeX(int x) {
       if(x - map.firstKey().getX() < 0){
         return Math.abs(x - map.firstKey().getX());
       }else{
        return x - map.firstKey().getX();
       }
    }

    private int getMazeY(int y) {
        if(y - map.firstKey().getY() < 0){
            return Math.abs(y - map.firstKey().getY());
          }else{
            return y - map.firstKey().getY();
           }
    }

    public void initializationMaze(){

        RandomMapGenerator HW = new RandomMapGenerator(map.firstKey().getX(), map.firstKey().getY(), map.lastKey().getX(), map.lastKey().getY());
        hight = HW.getHeight()+1;
        width = HW.getWidth()+1;
        maze = new DijstraPosition[width][hight];
        
        /* Initialize start and end points */

        maze[sourceX][sourceY] = new DijstraPosition(source.getX(), source.getY(), 1, false, sourceX, sourceY);
        maze[destinationX][destinationY] = new DijstraPosition(destination.getX(), destination.getY(), 1, true, destinationX, destinationY);

        /* Initialize walls and boulders  and door */
        for(Entity entity : entities){
            if(entity instanceof Wall || entity instanceof Boulder){
                int realx  = entity.getLocation().getX();
                int realy  = entity.getLocation().getY();
                int x  = realx - map.firstKey().getX();
                int y  = realy - map.firstKey().getY();
                maze[x][y] = new DijstraPosition(realx, realy ,MaxValue,false,x,y);
            }

            if(entity instanceof Door){
                Door d = (Door) entity;
                if(!d.isOpened()){
                    int realx  = entity.getLocation().getX();
                    int realy  = entity.getLocation().getY();
                    int x  = realx - map.firstKey().getX();
                    int y  = realy - map.firstKey().getY();
                    maze[x][y] = new DijstraPosition(realx, realy ,MaxValue,false,x,y);
                }

            }

            if(entity instanceof SwampTile){
                SwampTile s = (SwampTile) entity;
                int realx  = entity.getLocation().getX();
                int realy  = entity.getLocation().getY();
                int x  = realx - map.firstKey().getX();
                int y  = realy - map.firstKey().getY();
                maze[x][y] = new DijstraPosition(realx, realy,s.getMultiplyingFactor(),true,x,y);
            }
        }

        /* Initialize blank position */
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < hight; j++) {
                if(maze[i][j] == null){
                    int realx  = i +  map.firstKey().getX();
                    int realy  = j +  map.firstKey().getY();
                    maze[i][j] = new DijstraPosition(realx, realy,1,true,i,j);
                }
            }
        }

        /* Initialize distance */
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < hight; j++) {
                dist.put(maze[i][j], MaxValue);
            }
        }
        for (int i = 0; i < hight; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(String.format("%d ", maze[j][i].cost));
            }
            System.out.print("\n");
        }
        dist.replace( maze[sourceX][sourceY], 0);
    }

    public Location dijstra(){


        initializationMaze();
        int[][] operate = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };
        Queue<DijstraPosition> wait = new LinkedList<DijstraPosition>();
        
        wait.add(maze[sourceX][sourceY]);
        while (!wait.isEmpty()) {
            DijstraPosition cur = wait.poll();
            /* outPut(cur.x, cur.y); */
            if (cur.x == destinationX  && cur.y == destinationY) {
                outPut(destinationX, destinationY);
                break;
            }
            cur.reachable = false;
            for (int i = 0; i < 4; i++) {
                int nextX = cur.x + operate[i][0];
                int nextY = cur.y + operate[i][1];
                if(nextX < width && nextY < hight && nextX >= 0 && nextY >= 0){
                    int cost = maze[nextX][nextY].cost + cur.cost;
                    if (maze[nextX][nextY].reachable && dist.get(cur) + cost < dist.get(maze[nextX][nextY])) {
                        dist.replace(maze[nextX][nextY], dist.get(cur) + cost);
                        wait.add(maze[nextX][nextY]);
                        maze[nextX][nextY].pre = cur;
                    }
                }
            }
        }
        return next;
    }

    public void outPut(int x, int y) {
        if(maze[x][y].pre == null){
            return;
        }
        if (maze[x][y].pre.pre == null) {
            next = new Location(maze[x][y].realX, maze[x][y].realY);
        }
        outPut(maze[x][y].pre.x, maze[x][y].pre.y);
    }
    
}

class DijstraPosition {
    int x;
    int y;
    int realX;
    int realY;
    int cost;
    boolean reachable;
    DijstraPosition pre;

    public DijstraPosition(int realX, int realY,int cost,boolean reachable,int x,int y) {
        this.realX = realX;
        this.realY = realY;
        this.cost = cost;
        this.reachable = reachable;
        this.x  = x;
        this.y  = y;
        
    }
}
