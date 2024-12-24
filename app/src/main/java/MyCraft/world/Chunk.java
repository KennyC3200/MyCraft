package MyCraft.world;

import org.lwjgl.*;

import org.joml.Vector3i;

import java.util.*;

public class Chunk {

    public static Vector3i size;
    public static int volume;

    private Vector3i position;

    private ChunkMesh mesh;
    private boolean meshed;
    private Block[] blocks;

    /* Initialize a chunk given a position */
    public Chunk(Vector3i position) {
        this.position = position;

        mesh = new ChunkMesh();
        meshed = false;

        blocks = new Block[volume];
        Arrays.fill(blocks, new Block(Block.GRASS));
    }

    /* Init the chunk class */
    public static void init() {
        ChunkMesh.init();

        size = new Vector3i(16, 16, 16);
        volume = size.x * size.y * size.z;
    }

    /* Return the index given a position */
    public static int posToIdx(int x, int y, int z) {
        return (x * size.x * size.y) + (z * size.y) + (y);
    }

    /* Render the current chunk */
    public void render() {
        if (!meshed) {
            mesh.mesh(blocks, position);
            meshed = true;
        }

        mesh.render();
    }

    /* Get a block given position */
    public Block getBlock(int x, int y, int z) {
        return blocks[posToIdx(x, y, z)];
    }

}
