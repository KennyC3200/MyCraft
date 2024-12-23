package MyCraft.world;

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

    /* Destroy the world */
    public void destroy() {
        destroyChunks();
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

        chunksSize = new Vector3i(8, 1, 8);
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
                        )
                    );
                }
            }
        }
    }

    /* Destroy the chunks */
    private void destroyChunks() {
        // TODO: Loop through each chunk and destroy it
    }

    /* Get the chunk index */
    private int chunkIdx(int x, int y, int z) {
        return (x * chunksSize.y * chunksSize.z) + (z * chunksSize.y) + (y);
    }

}
