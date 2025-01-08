package MyCraft.world;

import MyCraft.util.*;
import MyCraft.player.AABB;

import org.joml.Vector3i;
import org.joml.Vector3f;

public class World {

    private Chunk[] chunks;
    private int chunksCount;
    private Vector3i chunksSize;

    /* Initialize the world */
    public World () {
        Block.init();
        Chunk.init();

        chunksSize = new Vector3i(2, 2, 2);
        chunksCount = chunksSize.x * chunksSize.y * chunksSize.z;

        initChunks();
    }

    /* Render the world */
    public void render() {
        for (Chunk chunk : chunks) {
            chunk.render();
        }
    }

    /* Initialize the chunks */
    private void initChunks() {
        chunks = new Chunk[chunksCount];

        // Create the chunks
        for (int x = 0; x < chunksSize.x; x++) {
            for (int y = 0; y < chunksSize.y; y++) {
                for (int z = 0; z < chunksSize.z; z++) {
                    chunks[chunkIdx(x, y, z)] = new Chunk(
                        new Vector3i(x * Chunk.size.x, y * Chunk.size.y, z * Chunk.size.z),
                        chunksSize.y * Chunk.size.y / 2
                    );
                }
            }
        }

        // Set the adjacent chunks
        for (int x = 0; x < chunksSize.x; x++) {
            for (int y = 0; y < chunksSize.y; y++) {
                for (int z = 0; z < chunksSize.z; z++) {
                    Chunk chunk = chunks[chunkIdx(x, y, z)];
                    chunk.setAdjacent(Direction.NORTH, z - 1 >= 0           ? chunks[chunkIdx(x, y, z - 1)] : null);
                    chunk.setAdjacent(Direction.SOUTH, z + 1 < chunksSize.z ? chunks[chunkIdx(x, y, z + 1)] : null);
                    chunk.setAdjacent(Direction.EAST,  x + 1 < chunksSize.x ? chunks[chunkIdx(x + 1, y, z)] : null);
                    chunk.setAdjacent(Direction.WEST,  x - 1 >= 0           ? chunks[chunkIdx(x - 1, y, z)] : null);
                    chunk.setAdjacent(Direction.UP,    y + 1 < chunksSize.y ? chunks[chunkIdx(x, y + 1, z)] : null);
                    chunk.setAdjacent(Direction.DOWN,  y - 1 >= 0           ? chunks[chunkIdx(x, y - 1, z)] : null);
                }
            }
        }
    }

    public Vector3i getChunksSize() {
        return chunksSize;
    }

    /* Get a block, given the BLOCK position */
    public Block getBlock(int x, int y, int z) {
        if (
            x < 0 || x >= Chunk.size.x * chunksSize.x ||
            y < 0 || y >= Chunk.size.y * chunksSize.y ||
            z < 0 || z >= Chunk.size.z * chunksSize.z
        ) {
            return null;
        }

        return chunks[chunkIdx(
            x / Chunk.size.x, 
            y / Chunk.size.y, 
            z / Chunk.size.z
        )].getBlock(
            x - (x / Chunk.size.x) * Chunk.size.x, 
            y - (y / Chunk.size.y) * Chunk.size.y, 
            z - (z / Chunk.size.z) * Chunk.size.z 
        );
    }

    /* Get a block, given the BLOCK position */
    public Block getBlock(Vector3i position) {
        return getBlock(position.x, position.y, position.z);
    }

    /* Get the chunk index given a CHUNK position */
    private int chunkIdx(int x, int y, int z) {
        return (x * chunksSize.y * chunksSize.z) + (z * chunksSize.y) + (y);
    }

    /* Get a chunk given a PLAYER position */
    public Chunk getChunk(int x, int y, int z) {
        return chunks[chunkIdx(x / Chunk.size.x, y / Chunk.size.y, z / Chunk.size.z)];
    }

    /* Get a chunk given a PLAYER position */
    public Chunk getChunk(Vector3i position) {
        return getChunk(position.x, position.y, position.z);
    }

    /* Returns AABBs surrounding the player */
    public AABB[] getBlockAABBs(AABB playerAABB) {
        Vector3f min = playerAABB.getMin();
        Vector3f max = playerAABB.getMax();

        // Keep in mind that height = 1.8f
        // Hence, the player AABB needs to be checked against 10 surrounding blocks
        AABB aabbs[] = new AABB[10];
        for (int i = 0; i < 10; i++) {
        }

        return aabbs;
    }

}
