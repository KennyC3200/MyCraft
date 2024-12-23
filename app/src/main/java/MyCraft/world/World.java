package MyCraft.world;

import MyCraft.util.*;

import org.joml.Vector3i;

public class World {

    private Chunk[] chunks;
    private int chunksCount;
    private Vector3i chunksSize;

    /* Initialize the world */
    public World () {
        initBlocks();
        initChunks();
    }

    /* Render the world */
    public void render() {
        // Render the chunks
        ChunkMesh.shader.bind();
        ChunkMesh.shader.uniformTexture2D(BlockMesh.getAtlas().getTexture(), 0);
        for (Chunk chunk : chunks) {
            chunk.render();
        }
    }

    /* Initialize the blocks */
    private void initBlocks() {
        Block.init();
    }

    /* Initialize the chunks */
    private void initChunks() {
        Chunk.init();

        chunksSize = new Vector3i(16, 2, 16);
        chunksCount = chunksSize.x * chunksSize.y * chunksSize.z;

        // Create the chunks
        chunks = new Chunk[chunksCount];
        for (int x = 0; x < chunksSize.x; x++) {
            for (int y = 0; y < chunksSize.y; y++) {
                for (int z = 0; z < chunksSize.z; z++) {
                    chunks[chunkIdx(x, y, z)] = new Chunk(
                        new Vector3i(
                            x * Chunk.size.x, 
                            y * Chunk.size.y, 
                            z * Chunk.size.z
                        ),
                        y < chunksSize.y / 2 ? Block.GRASS : Block.AIR
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

    /* Get the chunk index */
    private int chunkIdx(int x, int y, int z) {
        return (x * chunksSize.y * chunksSize.z) + (z * chunksSize.y) + (y);
    }

    public Vector3i getChunksSize() {
        return new Vector3i(chunksSize);
    }

}
