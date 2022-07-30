package dungeonmania.helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class RandomMapGenerator implements Iterable<Location> {
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int height;
    private int width;
    private boolean[][] map;
    public RandomMapGenerator(int xStart, int yStart, int xEnd, int yEnd) {
        startX = xStart;
        startY = yStart;
        endX = xEnd;
        endY = yEnd;
        height = Math.abs(endY - startY) + 1;
        width = Math.abs(endX - startX) + 1;
        this.map = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = false;
            }
        }
    }

    public Location getStartLocation() {
        return new Location(startX, startY);
    }

    public Location getEndLocation() {
        return new Location(endX, endY);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private boolean isOutBound(Location location) {
        return isOutBound(location.getX(), location.getY());
    }
    
    private boolean isOutBound(int x, int y) {
        return  !(0 <= x && x < width && 0 <= y && y < height);
    }

    private boolean isWall(Location location) {
        if (isOutBound(location)) {
            return false;
        }
        return !map[location.getY()][location.getX()];
    }

    private void makeWall(Location location) {
        makeWall(location.getX(),location.getY());
    } 

    private void makeWall(int x, int y) {
        map[y][x] = true;
    } 

    private Location randomChoice(ArrayList<Location> list) {
        if (list.size() == 0) {
            return null;
        } 
        Random index = new Random();
        Location next = list.get(index.nextInt(100000) % (list.size()));
        return next;
    }

    private Location addPossibleSpace(int x, int y, ArrayList<Location> array) {
        HashSet<Location> possible = new HashSet<>(array);
        for (int i = 0; i < 4; i++) {
            Location curr = new Location(x, y).getLocation(90.0 * i, 2);
            if (isWall(curr)) {
                possible.add(curr);
            }
        }
        array.clear();
        array.addAll(possible);
        return null;
    }

    private void connect(Location curr, Location next) {
        Location dir = curr.diff(next);
        makeWall(dir.getX() / 2 + curr.getX(), dir.getY() / 2 + curr.getY());
    }

    private void connect(Location curr) {
        ArrayList<Location> buffer = new ArrayList<>();
        makeWall(curr);
        for (int i = 0; i < 4; i++) {
            Location temp = curr.getLocation(90.0 * i, 2);
            if (!isWall(temp) && !isOutBound(temp)) {
                buffer.add(temp);
            }
        }
        Location next = randomChoice(buffer);
        connect(curr, next);
    }

    public RandomMapGenerator start() {
        ArrayList<Location> bufferArray = new ArrayList<>();
        makeWall(0, 0);
        addPossibleSpace(0, 0, bufferArray);
        doGenerate(bufferArray);
        if (!(map[height - 1][width - 1])) {
            map[height - 1][width - 1] = true;
            map[height - 1][width - 2] = true;
        }
        return this;
    }

    private void doGenerate(ArrayList<Location> bufferArray) {
        Location next = randomChoice(bufferArray);
        if (next != null) {
            connect(next);
            addPossibleSpace(next.getX(), next.getY(), bufferArray);
            bufferArray.remove(next);
            doGenerate(bufferArray);    
        }

    }
    
    @Override
    public Iterator<Location> iterator() {
        ArrayList<Location> locations = new ArrayList<>();
        for (int i = -1; i < height + 1; i++) {
            for (int j = -1; j < width + 1; j++) {
                if (isOutBound(j, i)) {
                    locations.add(new Location(j + startY, i + startX));
                } else if (!map[i][j]) {
                    locations.add(new Location(j + startY, i + startX));
                }
            }
        }
        return locations.iterator();
    }
    
}
