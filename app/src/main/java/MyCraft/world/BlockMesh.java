package MyCraft.world;

import MyCraft.util.*;
import MyCraft.gfx.*;

import org.joml.*;

import java.util.*;

public class BlockMesh {

    /* Vertices for the cube */
    private final float[] VERTICES = {
        // NORTH (-z)
        0, 0, 0,
        1, 0, 0,
        0, 1, 0,
        1, 1, 0,

        // SOUTH (+z)
        0, 0, 1,
        1, 0, 1,
        0, 1, 1,
        1, 1, 1,

        // EAST (+x)
        1, 0, 1,
        1, 0, 0,
        1, 1, 1,
        1, 1, 0,

        // WEST (-x)
        0, 0, 1,
        0, 0, 0,
        0, 1, 1,
        0, 1, 0,

        // UP (+y)
        0, 1, 1,
        1, 1, 1,
        0, 1, 0,
        1, 1, 0,

        // DOWN (-y)
        0, 0, 1,
        1, 0, 1,
        0, 0, 0,
        1, 0, 0,
    };

    /* Indices for the cube */
    private static final int[] INDICES = {2,  0,  1,  2,  3,  1};

    /* Face struct */
    private class Face {

        public Vector2f uvMin, uvMax;
        public float[] uvCoordinates;

        public Face() {
            uvCoordinates = new float[2 * 4];
        }

    }

    private Face[] faces;

    public BlockMesh() {
        // Initialize the faces
        faces = new Face[6];
        for (int i = 0; i < faces.length; i++) {
            faces[i] = new Face();
        }
    }

    /* Add a face to the mesh 
     * @param direction The direction (see Direction.java)
     * @param uvMin the uv min coordinate
     * @param uvMax the uv max coordinate
     * */
    public void addFace(int direction, Vector2f uvMin, Vector2f uvMax) {
        faces[direction].uvMin = uvMin;
        faces[direction].uvMax = uvMax;

        float[] uvCoordinates = {
            uvMin.x, 1 - uvMax.y,
            uvMax.x, 1 - uvMax.y,
            uvMin.x, 1 - uvMin.y,
            uvMax.x, 1 - uvMin.y,
        };

        /* Set the uv coordinates for the face */
        for (int i = 0; i < 2 * 4; i++) {
            faces[direction].uvCoordinates[i] = uvCoordinates[i];
        }
    }

    /* Mesh a face */
    public void meshFace(
        int direction, 
        Vector3f position, 
        ArrayList<Float> vertices, 
        ArrayList<Integer> indices) 
    {
        // Add the indices
        for (int i = 0; i < 6; i++) {
            indices.add(vertices.size() / 6 + INDICES[i]);
        }

        // Add the vertices
        for (int i = 0; i < 4; i++) {
            // Add the position coordinates
            vertices.add(VERTICES[3 * 4 * direction + i * 3 + 0] + position.x);
            vertices.add(VERTICES[3 * 4 * direction + i * 3 + 1] + position.y);
            vertices.add(VERTICES[3 * 4 * direction + i * 3 + 3] + position.z);

            // Add the uv coordinates
            vertices.add(faces[direction].uvCoordinates[i * 2]);
            vertices.add(faces[direction].uvCoordinates[i * 2 + 1]);
        }
    }

}
