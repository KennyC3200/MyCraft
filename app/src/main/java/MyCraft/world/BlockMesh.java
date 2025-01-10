package MyCraft.world;

import MyCraft.util.*;
import MyCraft.gfx.*;

import org.joml.*;

import static org.lwjgl.opengl.GL11C.GL_UNSIGNED_SHORT;

import java.util.*;

public class BlockMesh {

    /* Face struct */
    public class Face {

        public Vector2f uvMin, uvMax;
        public float[] uvCoordinates;

        public Face() {
            uvCoordinates = new float[2 * 4];
        }

    }

    private Face[] faces;

    /* Vertices for the cube */
    private static final int[] VERTICES = {
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

    private static HashMap<Integer, BlockMesh> blockToMesh;
    private static SpriteAtlas atlas;

    public BlockMesh() {
        // Initialize the faces
        faces = new Face[6];
        for (int i = 0; i < faces.length; i++) {
            faces[i] = new Face();
        }
    }

    /* Init the block mesh */
    public static void init() {
        blockToMesh = new HashMap<Integer, BlockMesh>();
        atlas = new SpriteAtlas("./src/main/resources/images/blocks.png", "blocks", new Vector2f(16, 16));

        // Init the different blocks
        BlockMesh blockGrassMesh = new BlockMesh();
        blockGrassMesh.addFace(Direction.NORTH, atlas.spriteUV(new Vector2i(1, 0)), atlas.spriteUV(new Vector2i(2, 1)));
        blockGrassMesh.addFace(Direction.SOUTH, atlas.spriteUV(new Vector2i(1, 0)), atlas.spriteUV(new Vector2i(2, 1)));
        blockGrassMesh.addFace(Direction.EAST, atlas.spriteUV(new Vector2i(1, 0)), atlas.spriteUV(new Vector2i(2, 1)));
        blockGrassMesh.addFace(Direction.WEST, atlas.spriteUV(new Vector2i(1, 0)), atlas.spriteUV(new Vector2i(2, 1)));
        blockGrassMesh.addFace(Direction.UP, atlas.spriteUV(new Vector2i(0, 0)), atlas.spriteUV(new Vector2i(1, 1)));
        blockGrassMesh.addFace(Direction.DOWN, atlas.spriteUV(new Vector2i(2, 0)), atlas.spriteUV(new Vector2i(3, 1)));
        blockToMesh.put(Block.GRASS, blockGrassMesh);

        BlockMesh blockDirtMesh = new BlockMesh();
        blockDirtMesh.addFace(Direction.NORTH, atlas.spriteUV(new Vector2i(2, 0)), atlas.spriteUV(new Vector2i(3, 1)));
        blockDirtMesh.addFace(Direction.SOUTH, atlas.spriteUV(new Vector2i(2, 0)), atlas.spriteUV(new Vector2i(3, 1)));
        blockDirtMesh.addFace(Direction.EAST,  atlas.spriteUV(new Vector2i(2, 0)), atlas.spriteUV(new Vector2i(3, 1)));
        blockDirtMesh.addFace(Direction.WEST, atlas.spriteUV(new Vector2i(2, 0)), atlas.spriteUV(new Vector2i(3, 1)));
        blockDirtMesh.addFace(Direction.UP, atlas.spriteUV(new Vector2i(2, 0)), atlas.spriteUV(new Vector2i(3, 1)));
        blockDirtMesh.addFace(Direction.DOWN, atlas.spriteUV(new Vector2i(2, 0)), atlas.spriteUV(new Vector2i(3, 1)));
        blockToMesh.put(Block.DIRT, blockDirtMesh);

        BlockMesh blockStoneMesh = new BlockMesh();
        blockStoneMesh.addFace(Direction.NORTH, atlas.spriteUV(new Vector2i(3, 0)), atlas.spriteUV(new Vector2i(4, 1)));
        blockStoneMesh.addFace(Direction.SOUTH, atlas.spriteUV(new Vector2i(3, 0)), atlas.spriteUV(new Vector2i(4, 1)));
        blockStoneMesh.addFace(Direction.EAST, atlas.spriteUV(new Vector2i(3, 0)), atlas.spriteUV(new Vector2i(4, 1)));
        blockStoneMesh.addFace(Direction.WEST, atlas.spriteUV(new Vector2i(3, 0)), atlas.spriteUV(new Vector2i(4, 1)));
        blockStoneMesh.addFace(Direction.UP, atlas.spriteUV(new Vector2i(3, 0)), atlas.spriteUV(new Vector2i(4, 1)));
        blockStoneMesh.addFace(Direction.DOWN, atlas.spriteUV(new Vector2i(3, 0)), atlas.spriteUV(new Vector2i(4, 1)));
        blockToMesh.put(Block.STONE, blockStoneMesh);
    }

    /* Get a block mesh */
    public static BlockMesh get(Integer block) {
        return blockToMesh.get(block);
    }

    /* Get the sprite atlas */
    public static SpriteAtlas getAtlas() {
        return atlas;
    }

    /* Add a face to the mesh (only called from init method)
     * @param direction The direction (see Direction.java)
     * @param uvMin the uv min coordinate
     * @param uvMax the uv max coordinate
     * */
    private void addFace(int direction, Vector2f uvMin, Vector2f uvMax) {
        faces[direction].uvMin = uvMin;
        faces[direction].uvMax = uvMax;

        float[] uvCoordinates= {
            uvMin.x, 1 - uvMax.y,
            uvMax.x, 1 - uvMax.y,
            uvMin.x, 1 - uvMin.y,
            uvMax.x, 1 - uvMin.y,
        };

        for (int i = 0; i < 2 * 4; i++) {
            faces[direction].uvCoordinates[i] = uvCoordinates[i];
        }
    }

    /* Mesh a face */
    public void meshFace(
        int direction, 
        Vector3i position, 
        ArrayList<Float> vertices, 
        ArrayList<Integer> indices) 
    {
        // Add the indices for the face
        for (int i = 0; i < 6; i++) {
            indices.add(vertices.size() / 5 + INDICES[i]);
        }

        // Add the vertices for the face
        for (int i = 0; i < 4; i++) {
            // Add the position coordinates
            vertices.add((float) VERTICES[(3 * 4) * direction + i * 3 + 0] + position.x);
            vertices.add((float) VERTICES[(3 * 4) * direction + i * 3 + 1] + position.y);
            vertices.add((float) VERTICES[(3 * 4) * direction + i * 3 + 2] + position.z);

            // Add the uv coordinates
            vertices.add(faces[direction].uvCoordinates[i * 2 + 0]);
            vertices.add(faces[direction].uvCoordinates[i * 2 + 1]);
        }
    }

    // TODO: Remove static
    public static void meshFace(
        int direction,
        Vector3i position,
        ArrayList<Integer> vertices
        // TODO: ArrayList<Float> uvVertices,
        // TODO: ArrayList<Integer> indices
    ) 
    {
        // Add the indices for the face
        // for (int i = 0; i < 6; i++) {
        //     indices.add(vertices.size() / 4 + INDICES[i]);
        // }

        // Add the vertices for the face
        int t1 = 0x0;

        // Third vertex
        t1 |= (VERTICES[(3 * 4) * direction + 0] << (27 - 0 * 5)) + (position.x << (27 - 0 * 5));
        t1 |= (VERTICES[(3 * 4) * direction + 1] << (27 - 1 * 5)) + (position.y << (27 - 1 * 5));
        t1 |= (VERTICES[(3 * 4) * direction + 2] << (27 - 2 * 5)) + (position.z << (27 - 2 * 5));

        // Fourth vertex
        t1 |= (VERTICES[(3 * 4) * direction + 3] << (27 - 3 * 5)) + (position.x << (27 - 3 * 5));
        t1 |= (VERTICES[(3 * 4) * direction + 4] << (27 - 4 * 5)) + (position.y << (27 - 4 * 5));
        t1 |= (VERTICES[(3 * 4) * direction + 5] << (27 - 5 * 5)) + (position.z << (27 - 5 * 5));

        vertices.add(t1);

        int t2 = 0x0;

        // Third vertex
        t2 |= (VERTICES[(3 * 4) * direction + 0 + (3 * 2)] << (27 - 0 * 5)) + (position.x << (27 - 0 * 5));
        t2 |= (VERTICES[(3 * 4) * direction + 1 + (3 * 2)] << (27 - 1 * 5)) + (position.y << (27 - 1 * 5));
        t2 |= (VERTICES[(3 * 4) * direction + 2 + (3 * 2)] << (27 - 2 * 5)) + (position.z << (27 - 2 * 5));

        // Fourth vertex
        t2 |= (VERTICES[(3 * 4) * direction + 3 + (3 * 2)] << (27 - 3 * 5)) + (position.x << (27 - 3 * 5));
        t2 |= (VERTICES[(3 * 4) * direction + 4 + (3 * 2)] << (27 - 4 * 5)) + (position.y << (27 - 4 * 5));
        t2 |= (VERTICES[(3 * 4) * direction + 5 + (3 * 2)] << (27 - 5 * 5)) + (position.z << (27 - 5 * 5));

        vertices.add(t2);
    }

    public Face getFace(int direction) {
        return faces[direction];
    }

}
