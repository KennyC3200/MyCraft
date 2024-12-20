package MyCraft.world;

import MyCraft.util.*;
import MyCraft.gfx.*;

import org.lwjgl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

import org.joml.*;

import java.util.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class ChunkMesh {

    public static Vector3i size;
    public static int volume;

    private static Shader shader;

    private Vector3i position;
    private VAO vao;
    private VBO vbo;
    private VBO ibo;

    private FloatBuffer vertices;
    private IntBuffer indices;

    /* Create the mesh, given a position
     * Initialize the VAO, VBO, and IBO
     * */
    public ChunkMesh(Vector3i position) {
        this.position = position;

        vao = new VAO();
        vao.init();

        vbo = new VBO();
        vbo.init(GL_ARRAY_BUFFER, GL_DYNAMIC_DRAW);

        ibo = new VBO();
        ibo.init(GL_ELEMENT_ARRAY_BUFFER, GL_DYNAMIC_DRAW);
    }

    /* Init the necessary components */
    public static void init() {
        size = new Vector3i(16, 16, 16);
        volume = size.x * size.y * size.z;

        shader = new Shader();
        shader.init("./src/main/res/shaders/chunk.vs", "./src/main/res/shaders/chunk.fs");
    }

    /* Destroy the chunk mesh */
    public void destroy() {
        vao.destroy();
        vbo.destroy();
        ibo.destroy();
    }

    /* Mesh the chunk */
    public void mesh() {
        // Mesh a block
        ArrayList<Float> verticesList = new ArrayList<Float>();
        ArrayList<Integer> indicesList = new ArrayList<Integer>();
        BlockData
            .get(BlockData.BLOCK_GRASS)
            .getMesh()
            .meshFace(Direction.NORTH, new Vector3f(0, 0, 0), verticesList, indicesList);

        // Copy the vertices from the verticesList to the vertices buffer
        float[] verticesArray = new float[verticesList.size()];
        for (int i = 0; i < verticesList.size(); i++) {
            verticesArray[i] = verticesList.get(i);
        }

        vertices = BufferUtils.createFloatBuffer(verticesList.size());
        vertices.put(verticesArray);
        vertices.flip();

        // Copy the idnices from the indicesList to the indicesBuffer
        int[] indicesArray = new int[indicesList.size()];
        for (int i = 0; i < indicesList.size(); i++) {
            indicesArray[i] = indicesList.get(i);
        }

        indices = BufferUtils.createIntBuffer(indicesList.size());
        indices.put(indicesArray);
        indices.flip();

        // float[] verticesArr = {
        //     -0.5f,  0.5f, 0.0f,  // top left
        //      0.5f,  0.5f, 0.0f,  // top right
        //      0.5f, -0.5f, 0.0f,  // bottom right
        //     -0.5f, -0.5f, 0.0f,  // bottom left
        // };
        //
        // vertices = BufferUtils.createFloatBuffer(12);
        // vertices.put(verticesArr);
        // vertices.flip();
        //
        // int[] indicesArr = {
        //     0, 1, 2,  // first triangle
        //     2, 3, 0,  // second triangle
        // };
        //
        // indices = BufferUtils.createIntBuffer(6);
        // indices.put(indicesArr);
        // indices.flip();
    }

    public void render() {
        // ibo.buffer(indices);
        // vbo.buffer(vertices);
        
        // TODO: Remember to have a attribPointer for the uv coordinates
        // vao.attribPointer(vbo, 0, 3, GL_FLOAT, 3 * Float.BYTES, 0);
       
        // vao.bind();
        // ibo.bind();
      
        // glDrawElements(GL_TRIANGLES, indices.remaining(), GL_UNSIGNED_INT, 0);
    }

    public static Vector3i getSize() {
        return new Vector3i(size);
    }

    public static int getVolume() {
        return volume;
    }

    public static Shader getShader() {
        return shader;
    }

}
