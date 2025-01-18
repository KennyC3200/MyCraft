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
    private int groundLevelY;

    /* Initialize the world */
    public World () {
        Block.init();
        Chunk.init();
    }

    /* Start the world */
    public void start(Vector3i chunksSize) {
        this.chunksSize = chunksSize;
        chunksCount = chunksSize.x * chunksSize.y * chunksSize.z;

        position = new Vector3i(0, 0, 0);
        groundLevelY = chunksSize.y * Chunk.size.y / 2;

        initChunks();
    }

    /* Render the world */
    public void render() {
        for (int i = 0; i < chunksCount; i++) {
            chunks[i].render();
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
            groundLevelY);
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
        // EAST DIRECTION (+X)
        if (direction == Direction.EAST) {
            Chunk tmp[] = new Chunk[chunksSize.y * chunksSize.z];
            for (int y = 0; y < chunksSize.y; y++) {
                for (int z = 0; z < chunksSize.z; z++) {
                    Chunk chunk = chunks[chunkIdx(0, y, z)];
                    chunk.generate(
                        new Vector3i(
                            position.x + chunksSize.x * Chunk.size.x, 
                            position.y + y * Chunk.size.y, 
                            position.z + z * Chunk.size.z),
                        groundLevelY);
                    chunk.setDirty();
                    tmp[z * chunksSize.y + y] = chunk;
                }
            }

            for (int x = 0; x < chunksSize.x - 1; x++) {
                for (int y = 0; y < chunksSize.y; y++) {
                    for (int z = 0; z < chunksSize.z; z++) {
                        chunks[chunkIdx(x, y, z)] = chunks[chunkIdx(x + 1, y, z)];
                    }
                }
            }

            for (int y = 0; y < chunksSize.y; y++) {
                for (int z = 0; z < chunksSize.z; z++) {
                    chunks[chunkIdx(chunksSize.x - 1, y, z)] = tmp[z * chunksSize.y + y];
                }
            }

            for (int y = 0; y < chunksSize.y; y++) {
                for (int z = 0; z < chunksSize.z; z++) {
                    initChunkAdjacents(chunksSize.x - 1, y, z);
                    initChunkAdjacents(chunksSize.x - 2, y, z);
                    chunks[chunkIdx(chunksSize.x - 1, y, z)].setDirty();
                    chunks[chunkIdx(chunksSize.x - 2, y, z)].setDirty();
                }
            }

            position.x += Chunk.size.x;
        }

        // WEST DIRECTION (-X)
        if (direction == Direction.WEST) {
            Chunk tmp[] = new Chunk[chunksSize.y * chunksSize.z];
            for (int y = 0; y < chunksSize.y; y++) {
                for (int z = 0; z < chunksSize.z; z++) {
                    Chunk chunk = chunks[chunkIdx(chunksSize.x - 1, y, z)];
                    chunk.generate(
                        new Vector3i(
                            position.x + 0 * Chunk.size.x, 
                            position.y + y * Chunk.size.y, 
                            position.z + z * Chunk.size.z),
                        groundLevelY);
                    chunk.setDirty();
                    tmp[z * chunksSize.y + y] = chunk;
                }
            }

            for (int x = chunksSize.x - 1; x > 0; x--) {
                for (int y = 0; y < chunksSize.y; y++) {
                    for (int z = 0; z < chunksSize.z; z++) {
                        chunks[chunkIdx(x, y, z)] = chunks[chunkIdx(x - 1, y, z)];
                    }
                }
            }

            for (int y = 0; y < chunksSize.y; y++) {
                for (int z = 0; z < chunksSize.z; z++) {
                    chunks[chunkIdx(0, y, z)] = tmp[z * chunksSize.y + y];
                }
            }

            for (int y = 0; y < chunksSize.y; y++) {
                for (int z = 0; z < chunksSize.z; z++) {
                    initChunkAdjacents(0, y, z);
                    initChunkAdjacents(1, y, z);
                    chunks[chunkIdx(0, y, z)].setDirty();
                    chunks[chunkIdx(1, y, z)].setDirty();
                }
            }

            position.x -= Chunk.size.x;
        }

        // SOUTH DIRECTION (+Z)
        if (direction == Direction.SOUTH) {
            Chunk tmp[] = new Chunk[chunksSize.y * chunksSize.x];
            for (int x = 0; x < chunksSize.x; x++) {
                for (int y = 0; y < chunksSize.y; y++) {
                    Chunk chunk = chunks[chunkIdx(x, y, 0)];
                    chunk.generate(
                        new Vector3i(
                            position.x + x * Chunk.size.x,
                            position.y + y * Chunk.size.y,
                            position.z + chunksSize.z * Chunk.size.z), 
                        groundLevelY);
                    chunk.setDirty();
                    tmp[x * chunksSize.y + y] = chunk;
                }
            }

            for (int x = 0; x < chunksSize.x; x++) {
                for (int y = 0; y < chunksSize.y; y++) {
                    for (int z = 0; z < chunksSize.z - 1; z++) {
                        chunks[chunkIdx(x, y, z)] = chunks[chunkIdx(x, y, z + 1)];
                    }
                }
            }

            for (int x = 0; x < chunksSize.x; x++) {
                for (int y = 0; y < chunksSize.y; y++) {
                    chunks[chunkIdx(x, y, chunksSize.z - 1)] = tmp[x * chunksSize.y + y];
                }
            }

            for (int x = 0; x < chunksSize.x; x++) {
                for (int y = 0; y < chunksSize.y; y++) {
                    initChunkAdjacents(x, y, chunksSize.z - 1);
                    initChunkAdjacents(x, y, chunksSize.z - 2);
                    chunks[chunkIdx(x, y, chunksSize.z - 1)].setDirty();
                    chunks[chunkIdx(x, y, chunksSize.z - 2)].setDirty();
                }
            }

            position.z += Chunk.size.z;
        }

        // NORTH DIRECTION (-Z)
        if (direction == Direction.NORTH) {
            Chunk tmp[] = new Chunk[chunksSize.y * chunksSize.x];
            for (int x = 0; x < chunksSize.x; x++) {
                for (int y = 0; y < chunksSize.y; y++) {
                    Chunk chunk = chunks[chunkIdx(x, y, chunksSize.z - 1)];
                    chunk.generate(
                        new Vector3i(
                            position.x + x * Chunk.size.x,
                            position.y + y * Chunk.size.y,
                            position.z + 0 * Chunk.size.z), 
                        groundLevelY);
                    chunk.setDirty();
                    tmp[x * chunksSize.y + y] = chunk;
                }
            }

            for (int x = 0; x < chunksSize.x; x++) {
                for (int y = 0; y < chunksSize.y; y++) {
                    for (int z = chunksSize.z - 1; z > 0; z--) {
                        chunks[chunkIdx(x, y, z)] = chunks[chunkIdx(x, y, z - 1)];
                    }
                }
            }

            for (int x = 0; x < chunksSize.x; x++) {
                for (int y = 0; y < chunksSize.y; y++) {
                    chunks[chunkIdx(x, y, 0)] = tmp[x * chunksSize.y + y];
                }
            }

            for (int x = 0; x < chunksSize.x; x++) {
                for (int y = 0; y < chunksSize.y; y++) {
                    initChunkAdjacents(x, y, 0);
                    initChunkAdjacents(x, y, 1);
                    chunks[chunkIdx(x, y, 0)].setDirty();
                    chunks[chunkIdx(x, y, 1)].setDirty();
                }
            }

            position.z -= Chunk.size.z;
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

        // Get the chunk that the block is in
        Chunk chunk = getChunk(x, y, z);

        // Need to round negative numbers down
        return chunk.getBlock(
            blockX - (blockX / Chunk.size.x) * Chunk.size.x,
            blockY - (blockY / Chunk.size.y) * Chunk.size.y,
            blockZ - (blockZ / Chunk.size.z) * Chunk.size.z
        );
    }

    /* Get a block, given the position */
    public Block getBlock(Vector3i position) {
        return getBlock(position.x, position.y, position.z);
    }

    /* Get the chunk index given a CHUNK ARRAY position */
    private int chunkIdx(int x, int y, int z) {
        return (x * chunksSize.y * chunksSize.z) + (z * chunksSize.y) + (y);
    }

    /* Get a chunk given a PLAYER position */
    public Chunk getChunk(int x, int y, int z) {
        int blockX = x - position.x;
        int blockY = y - position.y;
        int blockZ = z - position.z;

        // Check bounds
        if (
            blockX < 0 || blockX >= Chunk.size.x * chunksSize.x ||
            blockY < 0 || blockY >= Chunk.size.y * chunksSize.y ||
            blockZ < 0 || blockZ >= Chunk.size.z * chunksSize.z
        ) 
        {
            return null;
        }

        // Need to round negative numbers down
        return chunks[chunkIdx(
            blockX / Chunk.size.x + (x < 0 ? -1 : 0),
            blockY / Chunk.size.y + (y < 0 ? -1 : 0),
            blockZ / Chunk.size.z + (z < 0 ? -1 : 0)
        )];
    }

    /* Get a chunk given a PLAYER position */
    public Chunk getChunk(Vector3i position) {
        return getChunk(position.x, position.y, position.z);
    }

    public Vector3i getPosition() {
        return position;
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
