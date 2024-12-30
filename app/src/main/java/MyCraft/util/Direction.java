package MyCraft.util;

import org.joml.Vector3i;

public class Direction {

    public static final int NORTH = 0;
    public static final int SOUTH = 1;
    public static final int EAST = 2;
    public static final int WEST = 3;
    public static final int UP = 4;
    public static final int DOWN = 5;
    public static final int ZERO = 6;

    public static final Vector3i[] VECTOR = {
        new Vector3i(0, 0, -1), // NORTH
        new Vector3i(0, 0, 1),  // SOUTH
        new Vector3i(1, 0, 0),  // EAST
        new Vector3i(-1, 0, 0), // WEST
        new Vector3i(0, 1, 0),  // UP
        new Vector3i(0, -1, 0), // DOWN
        new Vector3i(0, 0, 0)   // ZERO
    };

}
