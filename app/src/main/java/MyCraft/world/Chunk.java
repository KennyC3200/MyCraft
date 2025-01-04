package MyCraft.world;

import org.joml.Vector3i;

public class Chunk {

    public static Vector3i size;
    public static int volume;

    public static FastNoiseLite noise;

    private Vector3i position;

    private ChunkMesh mesh;
    private boolean meshed;
    private Block[] blocks;

    private Chunk[] adjacents;

    /* Initialize a chunk given a position and block fill */
    public Chunk(Vector3i position, int groundLevelY) {
        this.position = position;

        mesh = new ChunkMesh();
        meshed = false;

        blocks = new Block[volume];
        for (int x = 0; x < size.x; x++) {
            for (int z = 0; z < size.z; z++) {
                float noiseYThreshold = groundLevelY + noise.GetNoise(position.x + x, position.z + z) * groundLevelY * 0.2f;
                for (int y = 0; y < size.y; y++) {
                    if (position.y + y < noiseYThreshold) {
                        blocks[posToIdx(x, y, z)] = new Block(Block.GRASS);
                    } else {
                        blocks[posToIdx(x, y, z)] = new Block(Block.AIR);
                    }
                }
            }
        }

        adjacents = new Chunk[6];
    }

    /* Init the chunk class */
    public static void init() {
        ChunkMesh.init();

        size = new Vector3i(16, 16, 16);
        volume = size.x * size.y * size.z;

        noise = new FastNoiseLite();
        noise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        noise.SetFrequency(0.01f);
        noise.SetFractalType(FastNoiseLite.FractalType.FBm);
        noise.SetFractalOctaves(10);
    }

    /* Return the index given a block position inside the chunk */
    public static int posToIdx(int x, int y, int z) {
        return (x * size.y * size.z) + (z * size.y) + (y);
    }

    /* Set adjacent chunk */
    public void setAdjacent(int direction, Chunk chunk) {
        adjacents[direction] = chunk;
    }

    /* Make the CURRENT CHUNK and ADJACENT CHUNKS to mesh */
    public void setDirty() {
        meshed = false;
        for (int i = 0; i < 6; i++) {
            if (adjacents[i] != null) {
                adjacents[i].meshed = false;
            }
        }
    }

    /* Render the current chunk */
    public void render() {
        if (!meshed) {
            mesh.mesh(blocks, position, adjacents);
            meshed = true;
        }

        mesh.render();
    }

    /* Get a block given position */
    public Block getBlock(int x, int y, int z) {
        return blocks[posToIdx(x, y, z)];
    }

}
