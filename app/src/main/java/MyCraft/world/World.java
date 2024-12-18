package MyCraft.world;

import org.joml.Vector3i;

public class World {

    private Chunk[] chunks;
    private int chunksCount;
    private Vector3i chunksSize;

    /* Initialize the world */
    public void init() {
        initChunks();
    }

    /* Destroy the world */
    public void destroy() {
        destroyChunks();
    }

    /* Render the world */
    public void render() {
        /* Render the chunks */
        ChunkMesh.getShader().bind();
        for (Chunk chunk : chunks) {
            chunk.render();
        }
    }

    /* Initialize the chunks */
    private void initChunks() {
        Chunk.init();

        chunksSize = new Vector3i(16, 3, 16);
        chunksCount = chunksSize.x * chunksSize.y * chunksSize.z;

        /* TODO: Testing */
        chunks = new Chunk[chunksCount];
        for (int i = 0; i < chunksCount; i++) {
            chunks[i] = new Chunk(new Vector3i(0, 0, 0));
        }
    }

    /* Destroy the chunks */
    private void destroyChunks() {
        // TODO: Loop through each chunk and destroy it
    }

}
