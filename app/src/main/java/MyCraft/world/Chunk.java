package MyCraft.world;

import org.lwjgl.*;

import org.joml.Vector3i;

public class Chunk {

    private Vector3i position;

    private ChunkMesh mesh;
    private boolean meshed;

    public Chunk(Vector3i position) {
        this.position = position;

        mesh = new ChunkMesh(position);
        meshed = false;
    }

    /* Init the chunk class */
    public static void init() {
        ChunkMesh.init();
    }

    /* Render the current chunk */
    public void render() {
        if (!meshed) {
            mesh.mesh();
            meshed = true;
        }

        mesh.render();
    }

}
