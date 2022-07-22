package dungeonmania.helpers;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.ArrayList;

import dungeonmania.util.Position;


public class Location implements Comparator<Location>, Comparable<Location>{
    private int x;
    private int y;
    public final static int X = 0;
    public final static  int Y = 1;
    private Location add(int x, int y) {
        return new Location(this.x + x, this.y + y);
    }
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location() {
        this(0, 0);
    }
    /**
     * Get x
     * @return
     */
    public int getX() {
        return x;
    }
    /**
     * get Y
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Return four nearest positions related by current position
     *  i.e.
     *          Top
     * Left     (Current)     Right
     *          Bottom
     * @return
     */
    public List<Function<Location, Location>> getFourNearPosition() {
        List<Function<Location, Location>> functions = new ArrayList<>();
        functions.add(location -> Location.getUp(location));
        functions.add(location -> Location.getDown(location));
        functions.add(location -> Location.getLeft(location));
        functions.add(location -> Location.getRight(location));
        return functions;
    }
    /**
     * Set X
     * @param x
     * @return
     */
    public Location setX(int x) {
        this.x = x;
        return this;
    }
    /**
     * Set Y
     * @param y
     * @return
     */
    public Location setY(int y) {
        this.y = y;
        return this;
    }

    /**
     * Set Location
     * @param location
     * @return
     */
    public Location setLocation(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        return this;
    }
    /**
     * Return the distance from given location
     * @param location
     * @return
     */
    public int distance(Location location) {
        return Math.max(Math.abs(location.x - this.x), Math.abs(location.y - this.y));
        // return Math.sqrt(Math.pow(location.x - this.x, 2) + Math.pow(location.y - this.y, 2));
    }

    /**
     * Get the location of the top of current location
     * @param location
     * @return
     */
    public Location getUp() {
        return this.add(0, -1);
    }
    /**
     * Get the location of the bottom of current location
     * @param location
     * @return
     */
    public Location getDown() {
        return this.add(0, 1);
    }
    /**
     * Get the location of the left of current location
     * @param location
     * @return
     */
    public Location getLeft() {
        return this.add(-1, 0);
    }
    /**
     * Get the location of the right of current location
     * @param location
     * @return
     */
    public Location getRight() {
        return this.add(1, 0);
    }

    private int round(double num) {
        if (num >= -0.001 && num <= 0.001) {
            return 0;
        }
        return ((int) (num > 0 ? Math.ceil(num) : Math.floor(num)));
    }
    public Location getLocation(double degree, int radius) {
        double deltaX = Math.cos(Math.toRadians(degree)) ;
        double deltaY = Math.sin(Math.toRadians(degree)) ;
        return add(round(deltaX) * radius, round(deltaY) * -1 *  radius);
    }
    
    public Location getLocation(int deltaX, int deltaY) {
        return add(deltaX, deltaY);
    }

    public Location diff(Location location) {
        return new Location(location.getX() - x, location.getY() - y);
    }


    public Location clone() {
        return new Location(x, y);
    }
    public Location getLocation(Position p) {
        return new Location(x + p.getX(), y + p.getY());
    }
    public static Location random(Location location, int min, int max) {
        Random random = new Random(location.hashCode());
        int x = location.getX() + random.nextInt(max - min + 1) + min;
        int y = location.getY() + random.nextInt(max - min + 1) + min;
        return Location.AsLocation(x, y);
    }
    /**
     * Get the location of the top of given location
     * @param location
     * @return
     */
    public static Location getUp(Location location) {
        return location.getUp();
    }
    /**
     * Get the location of the bottom of given location
     * @param location
     * @return
     */
    public static Location getDown(Location location) {
        return location.getDown();
    }
    /**
     * Get the location of the left of given location
     * @param location
     * @return
     */
    public static Location getLeft(Location location) {
        return location.getLeft();
    }
    /**
     * Get the location of the right of given location
     * @param location
     * @return
     */
    public static Location getRight(Location location) {
        return location.getRight();
    }

    /**
     * Return a Location
     * @param x
     * @param y
     * @return
     */
    public static Location AsLocation(int x, int y) {
        return new Location(x, y);
    }
    public static Position getMoveDir(Location from, Location to) {
        Location temp = from.diff(to);
        return new Position(temp.getX(), temp.getY());
    }
    @Override
    public String toString() {
        return String.format("<%d, %d>", x, y);
    }

    @Override
    public int compare(Location o1, Location o2) {
        Location a = (Location) o1;
        Location b = (Location) o2;
        if (a.x != b.x) {
            return a.x - b.x;
        } else {
            return a.y - b.y;
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Location)) {
            return false;
        }
        Location location = (Location) obj;
        return location.x == x && location.y == y;
    }
    
    @Override
    public int compareTo(Location o) {
        return compare(this, o);
    }
    @Override
    public int hashCode() {
        // TODO: CHANGE THIS IF ANY VALUE IS OVERFLOW 
        return ((31 * x + 13 * y) ^ 3  % (7919 * 1637) - (19 * y + 21 - x ^ 4) ^ 2) * (x - y) ^ 2 + 1;
    }
}
