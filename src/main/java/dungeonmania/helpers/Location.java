package dungeonmania.helpers;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import dungeonmania.util.Position;

import java.util.ArrayList;

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
     * Get Location 
     * @return (x, y)
     */
    public int[] getLocation() {
        int[] response = {x, y};
        return response;
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
     * Return eight nearest positions related by current position
     * i.e. 
     * TopLeft      Top         TopRight
     * Left        (Current)    Right
     * BottomLeft   Bottom      BottomRight
     * @return
     */
    public List<Function<Location, Location>> getEightNearPosition() {
        List<Function<Location, Location>> functions = new ArrayList<>();
        functions.addAll(getFourNearPosition());
        functions.add(location -> Location.getTopLeft(location));
        functions.add(location -> Location.getTopRight(location));
        functions.add(location -> Location.getBottomLeft(location));
        functions.add(location -> Location.getBottomRight(location));
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
     * @param x
     * @param y
     * @return
     */
    public Location setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
    /**
     * Set Location 
     * @param location assume (x, y)
     * @return
     */
    public Location setLocation(int[] location) {
        this.x = location[Location.X];
        this.y = location[Location.Y];
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
     * Making a movement
     * @param x 
     * @param y
     * @return
     */
    public Location move(int x, int y) {
        this.x = this.x + x;
        this.y = this.y + y;
        return this;
    }
    /**
     * Return the distance from given location
     * @param location
     * @return
     */
    public double distance(Location location) {
        return Math.max(Math.abs(location.x - this.x), Math.abs(location.y - this.y));
        // return Math.sqrt(Math.pow(location.x - this.x, 2) + Math.pow(location.y - this.y, 2));
    }
    /**
     * Return the distance from given location
     * @param x
     * @param y
     * @return
     */
    public double distance(int x, int y) {
        return distance(Location.AsLocation(x, y));
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
    /**
     * Get the location of the top left of current location
     * @param location
     * @return
     */
    public Location getTopLeft() {
        return this.add(-1, -1);
    }
    /**
     * Get the location of the top right of current location
     * @param location
     * @return
     */
    public Location getTopRight() {
        return this.add(1, -1);
    }
    /**
     * Get the location of the botto, left of current location
     * @param location
     * @return
     */
    public Location getBottomLeft() {
        return this.add(-1, 1);
    }
    /**
     * Get the location of the bottom right of current location
     * @param location
     * @return
     */
    public Location getBottomRight() {
        return this.add(1, 1);
    }
    private int round(double num) {
        if (num >= -0.001 && num <= 0.001) {
            return 0;
        }
        return ((int) (num > 0 ? Math.ceil(num) : Math.floor(num)));
    }
    public Location getLocation(double degree, int radius) {
        double deltaX = Math.cos(Math.toRadians(degree)) * radius;
        double deltaY = Math.sin(Math.toRadians(degree)) * radius;
        // System.out.println(String.format("%f: %d + %d(%f), %d + %d(%f) = %s", degree, x, round(deltaX), deltaX, y, round(deltaY) * -1, deltaY, add(round(deltaX) , round(deltaY) * -1).toString()));
        return add(round(deltaX) , round(deltaY) * -1);
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
     * Get the location of the top left of given location
     * @param location
     * @return
     */
    public static Location getTopLeft(Location location) {
        return location.getTopLeft();
    }
    /**
     * Get the location of the top right of given location
     * @param location
     * @return
     */
    public static Location getTopRight(Location location) {
        return location.getTopRight();
    }
    /**
     * Get the location of the botto, left of given location
     * @param location
     * @return
     */
    public static Location getBottomLeft(Location location) {
        return location.getBottomLeft();
    }
    /**
     * Get the location of the bottom right of given location
     * @param location
     * @return
     */
    public static Location getBottomRight(Location location) {
        return location.getBottomRight();
    }
    /**
     * Convert a location to an array.
     * @param location 
     * @return assume (x, y)
     */
    public static int[] toInts (Location location) {
        int[] response = {location.x, location.y};
        return response; 
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
    public static Location AsLocation(String string) {
        String temp = string.subSequence(1, string.length() - 1).toString();
        String[] location = temp.replaceAll(" ", "").split(",");
        return new Location(Integer.parseInt(location[0]), Integer.parseInt(location[1]));
    }
    /**
     * Return a location
     * @param location
     * @return
     */
    public static Location AsLocation(int[] location) {
        return new Location(location[Location.X], location[Location.Y]);
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
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        return compare(this, o);
    }
    @Override
    public int hashCode() {
        return Integer.valueOf(x).hashCode() + Integer.valueOf(y).hashCode();
    }
}
