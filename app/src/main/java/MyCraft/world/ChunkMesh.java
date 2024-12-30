package MyCraft.world;

import MyCraft.util.*;
import MyCraft.gfx.*;

import static org.lwjgl.opengl.GL33C.*;

import org.joml.Vector3i;

import java.util.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class ChunkMesh {

    public static Shader shader;

    private VAO vao;
    private VBO vbo;
    private VBO ibo;

    private FloatBuffer vertices;
    private IntBuffer indices;

    /* Create the mesh, given a position
     * Initialize the VAO, VBO, and IBO
     * */
    public ChunkMesh() {
        vao = new VAO();
        vbo = new VBO(GL_ARRAY_BUFFER, GL_DYNAMIC_DRAW);
        ibo = new VBO(GL_ELEMENT_ARRAY_BUFFER, GL_DYNAMIC_DRAW);
    }

    /* Init the necessary components */
    public static void init() {
        shader = new Shader("./src/main/resources/shaders/chunk.vs", "./src/main/resources/shaders/chunk.fs");
    }

    /* Destroy the chunk mesh */
    public void destroy() {
        vao.destroy();
        vbo.destroy();
        ibo.destroy();
    }

    /* Mesh the chunk */
    public void mesh(Block[] blocks, Vector3i position, Chunk[] adjacentChunks) {
        ArrayList<Float> verticesList = new ArrayList<Float>();
        ArrayList<Integer> indicesList = new ArrayList<Integer>();

        for (int x = 0; x < Chunk.size.x; x++) {
            for (int y = 0; y < Chunk.size.y; y++) {
                for (int z = 0; z < Chunk.size.z; z++) {
                    int blockID = blocks[Chunk.posToIdx(x, y, z)].getID();
                    if (blockID == Block.AIR) {
                        continue;
                    }

                    Vector3i blockPosition = new Vector3i(position.x + x, position.y + y, position.z + z);

                    for (int i = 0; i < Direction.VECTOR.length; i++) {
                        int adjacentBlockX = x + Direction.VECTOR[i].x;
                        int adjacentBlockY = y + Direction.VECTOR[i].y;
                        int adjacentBlockZ = z + Direction.VECTOR[i].z;

                        if (
                            adjacentBlockX < 0 || adjacentBlockX == Chunk.size.x ||
                            adjacentBlockY < 0 || adjacentBlockY == Chunk.size.y ||
                            adjacentBlockZ < 0 || adjacentBlockZ == Chunk.size.z
                        )
                        {
                            /* adjacentChunks[i] is the adjacent chunk in question
                             * Can do (adjacentBlockX + Chunk.size.x) % Chunk.size.x
                             * Since: (-1 + 16) % 16 = 15 and 16 % 16 = 0, giving the correct adjacent block index
                             * Note: -1 % 16 != 15 in Java, thus have to do (-1 + 16) % 16
                             * Note: the first if statement joined by the && is to ensure that the UP block face
                             *       is rendered at the block sky limit
                             */
                            if (
                                i == Direction.UP && adjacentChunks[i] == null ||
                                adjacentChunks[i] != null &&
                                adjacentChunks[i].getBlock(
                                    (adjacentBlockX + Chunk.size.x) % Chunk.size.x,
                                    (adjacentBlockY + Chunk.size.y) % Chunk.size.y,
                                    (adjacentBlockZ + Chunk.size.z) % Chunk.size.z
                                ).getID() == Block.AIR
                            ) {
                                BlockMesh.get(blockID).meshFace(i, blockPosition, verticesList, indicesList);
                            }
                            continue;
                        }

                        if (blocks[Chunk.posToIdx(adjacentBlockX, adjacentBlockY, adjacentBlockZ)].getID() == Block.AIR) {
                            BlockMesh.get(blockID).meshFace(i, blockPosition, verticesList, indicesList);
                        }
                    }
                }
            }
        }

        // Copy the vertices from the verticesList to the vertices buffer
        vertices = MyCraft.util.BufferUtils.listToFloatBuffer(verticesList);

        // Copy the indices from the indicesList to the indicesBuffer
        indices = MyCraft.util.BufferUtils.listToIntBuffer(indicesList);
    }

    public void render() {
        ibo.buffer(indices);
        vbo.buffer(vertices);
        
        // Have attribPointer for the coordinates and uv coordinates
        vao.attribPointer(vbo, 0, 3, GL_FLOAT, 5 * Float.BYTES, 0 * Float.BYTES);
        vao.attribPointer(vbo, 1, 2, GL_FLOAT, 5 * Float.BYTES, 3 * Float.BYTES);
       
        vao.bind();
        ibo.bind();
      
        glDrawElements(GL_TRIANGLES, indices.remaining(), GL_UNSIGNED_INT, 0);
    }

}
