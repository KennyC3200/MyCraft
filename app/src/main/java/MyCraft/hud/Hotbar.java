package MyCraft.hud;

import MyCraft.util.*;
import MyCraft.gfx.*;
import MyCraft.world.*;
import MyCraft.player.*;

import org.joml.Vector2f;

import static org.lwjgl.opengl.GL33C.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import java.util.ArrayList;

public class Hotbar extends HudComponent {

    private Player player;

    private SpriteAtlas atlas;

    private static int MESH_COUNT = 0;
    private static final int ITEM_MESH = MESH_COUNT++;
    private static final int FRAME_MESH = MESH_COUNT++;

    private ArrayList<Float>[] verticesList;
    private ArrayList<Integer>[] indicesList;

    @SuppressWarnings("unchecked")
    public Hotbar(Player player) {
        this.player = player;

        toggled = true;
        atlas = new SpriteAtlas("./src/main/resources/images/hotbar.png", "tex", new Vector2f(22, 22));

        vertices = new FloatBuffer[MESH_COUNT];
        indices = new IntBuffer[MESH_COUNT];

        verticesList = new ArrayList[MESH_COUNT];
        indicesList = new ArrayList[MESH_COUNT];
    }

    /* Mesh the hotbar */
    @Override
    public void mesh() {
        for (int mesh = 0; mesh < MESH_COUNT; mesh++) {
            verticesList[mesh] = new ArrayList<Float>();
            indicesList[mesh] = new ArrayList<Integer>();
        }

        for (int i = 0; i < Player.hotbar_size; i++) {
            Vector2f unit = window.getPixelUnit();

            // 80 x 80 window pixel units
            Vector2f size = new Vector2f(80.0f * unit.x, 80.0f * unit.y);

            // The hotbar frame/item is defined by two points, since it is a square
            Vector2f p1 = new Vector2f(-size.x * (Player.hotbar_size / 2.0f - i), -1.0f + 0.05f);
            Vector2f p2 = new Vector2f(p1.x + size.x, p1.y + size.y);

            if (player.getHotbarItem(i) != Block.NONE) {
                meshItem(
                    new Vector2f(p1.x + 12.0f * unit.x, p1.y + 12.0f * unit.y),
                    new Vector2f(p2.x - 12.0f * unit.x, p2.y - 12.0f * unit.y),
                    player.getHotbarItem(i)
                );
            }
            meshFrame(p1, p2, i == player.getCurrentHotbarIdx());
        }

        // Create the float buffers for both meshes
        for (int mesh = 0; mesh < MESH_COUNT; mesh++) {
            vertices[mesh] = MyCraft.util.BufferUtils.listToFloatBuffer(verticesList[mesh]);
            indices[mesh] = MyCraft.util.BufferUtils.listToIntBuffer(indicesList[mesh]);
        }
    }

    /* Render the hotbar */
    @Override
    public void render() {
        if (!toggled) {
            return;
        }
        
        for (int mesh = 0; mesh < MESH_COUNT; mesh++) {
            if (indicesList[mesh].size() == 0) {
                continue;
            }

            if (mesh == ITEM_MESH) {
                shader.uniformTexture2D(BlockMesh.getAtlas(), 0);
            } else if (mesh == FRAME_MESH) {
                shader.uniformTexture2D(atlas, 0);
            }
            
            ibo.buffer(indices[mesh]);
            vbo.buffer(vertices[mesh]);

            vao.attribPointer(vbo, 0, 2, GL_FLOAT, 4 * Float.BYTES, 0 * Float.BYTES);
            vao.attribPointer(vbo, 1, 2, GL_FLOAT, 4 * Float.BYTES, 2 * Float.BYTES);

            vao.bind();
            ibo.bind();

            glDrawElements(GL_TRIANGLES, indices[mesh].remaining(), GL_UNSIGNED_INT, 0);
        }
    }

    /* Mesh the frame of a item in the hotbar */
    private void meshFrame(Vector2f p1, Vector2f p2, boolean active) {
        int uOffset = active ? 1 : 0;

        // IMPORTANT: Calculate the vertex offset for the indices since more vertices have been added
        int vertexOffset = verticesList[FRAME_MESH].size() / 4;
        int[] indicesArray = {
            vertexOffset + 0, vertexOffset + 1, vertexOffset + 3,
            vertexOffset + 1, vertexOffset + 2, vertexOffset + 3
        };

        for (int i = 0; i < indicesArray.length; i++) {
            indicesList[FRAME_MESH].add(indicesArray[i]);
        }

        Vector2f uv1 = atlas.spriteUV(1 + uOffset, 0);
        Vector2f uv2 = atlas.spriteUV(1 + uOffset, 1);
        Vector2f uv3 = atlas.spriteUV(uOffset, 1);
        Vector2f uv4 = atlas.spriteUV(uOffset, 0);

        float[] verticesArray = {
            p1.x, p2.y, uv1.x, uv1.y,
            p1.x, p1.y, uv2.x, uv2.y,
            p2.x, p1.y, uv3.x, uv3.y,
            p2.x, p2.y, uv4.x, uv4.y
        };

        for (int i = 0; i < verticesArray.length; i++) {
            verticesList[FRAME_MESH].add(verticesArray[i]);
        }
    }

    /* Mesh a item in the hotbar */
    private void meshItem(Vector2f p1, Vector2f p2, Integer block) {
        Vector2f uvMin = BlockMesh.get(block).getFace(Direction.NORTH).uvMin;
        Vector2f uvMax = BlockMesh.get(block).getFace(Direction.NORTH).uvMax;

        // IMPORTANT: Calculate the vertex offset for the indices since more vertices have been added
        int vertexOffset = verticesList[ITEM_MESH].size() / 4;
        int[] indicesArray = {
            vertexOffset + 0, vertexOffset + 1, vertexOffset + 3,
            vertexOffset + 1, vertexOffset + 2, vertexOffset + 3
        };

        for (int i = 0; i < indicesArray.length; i++) {
            indicesList[ITEM_MESH].add(indicesArray[i]);
        }

        float[] verticesArray = {
            p1.x, p2.y, uvMin.x, 1 - uvMin.y,
            p1.x, p1.y, uvMin.x, 1 - uvMax.y,
            p2.x, p1.y, uvMax.x, 1 - uvMax.y,
            p2.x, p2.y, uvMax.x, 1 - uvMin.y
        };

        for (int i = 0; i < verticesArray.length; i++) {
            verticesList[ITEM_MESH].add(verticesArray[i]);
        }
    }
}
