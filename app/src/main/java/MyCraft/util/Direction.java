package MyCraft.util;

import org.joml.Vector3i;

public class Direction {

    public static final int NORTH = 0;
    public static final int SOUTH = 1;
    public static final int EAST = 2;
    public static final int WEST = 3;
    public static final int UP = 4;
    public static final int DOWN = 5;

    public static final Vector3i[] VECTOR = {
        new Vector3i(0, 0, -1),
        new Vector3i(0, 0, 1),
        new Vector3i(1, 0, 0),
        new Vector3i(-1, 0, 0),
        new Vector3i(0, 1, 0),
        new Vector3i(0, -1, 0)
    };

}
