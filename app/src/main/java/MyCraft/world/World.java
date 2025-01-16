package MyCraft.world;

import MyCraft.util.*;
import MyCraft.player.AABB;

import org.joml.Vector3i;
import org.joml.Vector3f;

public class World {

    private Chunk[] chunks;
    private int chunksCount;
    private Vector3i chunksSize;

    private Vector3i position;

    /* Initialize the world */
    public World () {
        Block.init();
        Chunk.init();

        chunksSize = new Vector3i(24, 10, 24);
        chunksCount = chunksSize.x * chunksSize.y * chunksSize.z;

        position = new Vector3i(0, 0, 0);

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
                    initChunk(x, y, z);
                }
            }
        }

        // Set the adjacent chunks
        for (int x = 0; x < chunksSize.x; x++) {
            for (int y = 0; y < chunksSize.y; y++) {
                for (int z = 0; z < chunksSize.z; z++) {
                    initChunkAdjacents(x, y, z);
                }
            }
        }
    }

    /* Init a chunk given an ARRAY position */
    public void initChunk(int x, int y, int z) {
        chunks[chunkIdx(x, y, z)] = new Chunk(
            new Vector3i(x * Chunk.size.x, y * Chunk.size.y, z * Chunk.size.z),
            chunksSize.y * Chunk.size.y / 2
        );
    }

    /* Init adjacents for a chunk given an ARRAY position */
    public void initChunkAdjacents(int x, int y, int z) {
        Chunk chunk = chunks[chunkIdx(x, y, z)];
        chunk.setAdjacent(Direction.NORTH, z - 1 >= 0           ? chunks[chunkIdx(x, y, z - 1)] : null);
        chunk.setAdjacent(Direction.SOUTH, z + 1 < chunksSize.z ? chunks[chunkIdx(x, y, z + 1)] : null);
        chunk.setAdjacent(Direction.EAST,  x + 1 < chunksSize.x ? chunks[chunkIdx(x + 1, y, z)] : null);
        chunk.setAdjacent(Direction.WEST,  x - 1 >= 0           ? chunks[chunkIdx(x - 1, y, z)] : null);
        chunk.setAdjacent(Direction.UP,    y + 1 < chunksSize.y ? chunks[chunkIdx(x, y + 1, z)] : null);
        chunk.setAdjacent(Direction.DOWN,  y - 1 >= 0           ? chunks[chunkIdx(x, y - 1, z)] : null);
    }

    /* Create new chunks in a new direction */
    public void createNewChunks(int direction) {
        if (direction == Direction.EAST) {
            System.out.println("EAST");
        }
    }

    public Vector3i getChunksSize() {
        return chunksSize;
    }

    /* Get a block, given the position
     * @return The block, otherwise null if out of bounds
     */
    public Block getBlock(int x, int y, int z) {
        int blockX = x - position.x;
        int blockY = y - position.y;
        int blockZ = z - position.z;

        if (
            blockX < 0 || blockX >= Chunk.size.x * chunksSize.x ||
            blockY < 0 || blockY >= Chunk.size.y * chunksSize.y ||
            blockZ < 0 || blockZ >= Chunk.size.z * chunksSize.z
        ) {
            return null;
        }

        return chunks[chunkIdx(
            blockX / Chunk.size.x,
            blockY / Chunk.size.y,
            blockZ / Chunk.size.z
        )].getBlock(
            blockX - (blockX / Chunk.size.x) * Chunk.size.x,
            blockY - (blockY / Chunk.size.y) * Chunk.size.y,
            blockZ - (blockZ / Chunk.size.z) * Chunk.size.z
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
