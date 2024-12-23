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

    public Chunk(Vector3i position) {
        this.position = position;

        mesh = new ChunkMesh(position);
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

    /* Render the current chunk */
    public void render() {
        if (!meshed) {
            mesh.mesh(blocks);
            meshed = true;
        }

        mesh.render();
    }

    /* Return the index given a position */
    public static int posToIdx(int x, int y, int z) {
        return (x * size.x * size.y) + (z * size.y) + (y);
    }

}
