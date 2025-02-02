package MyCraft.world;

import MyCraft.util.*;
import MyCraft.gfx.*;

import org.joml.*;

import java.util.*;

public class BlockMesh {

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

    /* Face struct */
    public class Face {

        // UV coordinats
        public Vector2f uvMin, uvMax;
        public float[] uvCoordinates;

        // Sprite coordinates on the texture atlas
        public Vector2i[] spriteCoordinates;

        public Face() {
            uvCoordinates = new float[2 * 4];
            spriteCoordinates = new Vector2i[4];
        }

    }

    private Face[] faces;

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

        // The sprite atlas is also 16 x 16 = 256 blocks in total
        atlas = new SpriteAtlas("./src/main/resources/images/blocks.png", "blocks", new Vector2i(16, 16));

        // Init the different blocks
        BlockMesh blockGrassMesh = new BlockMesh();
        blockGrassMesh.addFace(Direction.NORTH, new Vector2i(1, 0), new Vector2i(2, 1));
        blockGrassMesh.addFace(Direction.SOUTH, new Vector2i(1, 0), new Vector2i(2, 1));
        blockGrassMesh.addFace(Direction.EAST,  new Vector2i(1, 0), new Vector2i(2, 1));
        blockGrassMesh.addFace(Direction.WEST,  new Vector2i(1, 0), new Vector2i(2, 1));
        blockGrassMesh.addFace(Direction.UP,    new Vector2i(0, 0), new Vector2i(1, 1));
        blockGrassMesh.addFace(Direction.DOWN,  new Vector2i(2, 0), new Vector2i(3, 1));
        blockToMesh.put(Block.GRASS, blockGrassMesh);

        BlockMesh blockDirtMesh = new BlockMesh();
        blockDirtMesh.addFace(Direction.NORTH, new Vector2i(2, 0), new Vector2i(3, 1));
        blockDirtMesh.addFace(Direction.SOUTH, new Vector2i(2, 0), new Vector2i(3, 1));
        blockDirtMesh.addFace(Direction.EAST,  new Vector2i(2, 0), new Vector2i(3, 1));
        blockDirtMesh.addFace(Direction.WEST,  new Vector2i(2, 0), new Vector2i(3, 1));
        blockDirtMesh.addFace(Direction.UP,    new Vector2i(2, 0), new Vector2i(3, 1));
        blockDirtMesh.addFace(Direction.DOWN,  new Vector2i(2, 0), new Vector2i(3, 1));
        blockToMesh.put(Block.DIRT, blockDirtMesh);

        BlockMesh blockStoneMesh = new BlockMesh();
        blockStoneMesh.addFace(Direction.NORTH, new Vector2i(3, 0), new Vector2i(4, 1));
        blockStoneMesh.addFace(Direction.SOUTH, new Vector2i(3, 0), new Vector2i(4, 1));
        blockStoneMesh.addFace(Direction.EAST,  new Vector2i(3, 0), new Vector2i(4, 1));
        blockStoneMesh.addFace(Direction.WEST,  new Vector2i(3, 0), new Vector2i(4, 1));
        blockStoneMesh.addFace(Direction.UP,    new Vector2i(3, 0), new Vector2i(4, 1));
        blockStoneMesh.addFace(Direction.DOWN,  new Vector2i(3, 0), new Vector2i(4, 1));
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

    private void addFace(int direction, Vector2i spriteCoordinateMin, Vector2i spriteCoordinateMax) {
        Vector2i spritesSize = atlas.getSpritesSize();

        faces[direction].spriteCoordinates[0] = new Vector2i(spriteCoordinateMin.x, spritesSize.y - spriteCoordinateMax.y);
        faces[direction].spriteCoordinates[1] = new Vector2i(spriteCoordinateMax.x, spritesSize.y - spriteCoordinateMax.y);
        faces[direction].spriteCoordinates[2] = new Vector2i(spriteCoordinateMin.x, spritesSize.y - spriteCoordinateMin.y);
        faces[direction].spriteCoordinates[3] = new Vector2i(spriteCoordinateMax.x, spritesSize.y - spriteCoordinateMin.y);

        Vector2f uvMin = atlas.spriteUV(spriteCoordinateMin);
        Vector2f uvMax = atlas.spriteUV(spriteCoordinateMax);

        faces[direction].uvMin = uvMin;
        faces[direction].uvMax = uvMax;

        float[] uvCoordinates = {
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
        ArrayList<Integer> vertices,
        ArrayList<Integer> indices
    )
    {
        // Add the indices for the face
        int indices_offset = vertices.size();
        for (int i = 0; i < 6; i++) {
            indices.add(indices_offset + INDICES[i]);
        }

        // Add the vertices for the face
        // Data packing:
        // - Each vertex coordinate (x, y, z) is stored in 5 bits, since they range from [0, 16]
        // - Each sprite coordinate (x, y) is stored in 5 bits, since they range from [0, 16]
        for (int i = 0; i < 4; i++) {
            int data = 0x0;

            // Vertex coordinates
            int idx_offset = (3 * 4) * direction + (i * 3);
            data |= (VERTICES[idx_offset + 0] << (0 * 5)) + (position.x << (0 * 5)); // x-coordinate
            data |= (VERTICES[idx_offset + 1] << (1 * 5)) + (position.y << (1 * 5)); // y-coordinate
            data |= (VERTICES[idx_offset + 2] << (2 * 5)) + (position.z << (2 * 5)); // z-coordinate

            // Sprite atlas coordinates
            data |= faces[direction].spriteCoordinates[i].x << ((3 * 5) + (0 * 5)); // x-coordinate
            data |= faces[direction].spriteCoordinates[i].y << ((3 * 5) + (1 * 5)); // y-coordinate

            vertices.add(data);
        }
    }

    public Face getFace(int direction) {
        return faces[direction];
    }

}
