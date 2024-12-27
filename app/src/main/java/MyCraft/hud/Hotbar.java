package MyCraft.hud;

import MyCraft.util.*;
import MyCraft.gfx.*;
import MyCraft.world.*;
import MyCraft.player.*;

import org.joml.Vector2f;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL33C.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Hotbar extends HudComponent {

    private Player player;

    private SpriteAtlas atlas;

    private static int MESH_COUNT = 0;
    private static final int ITEM_MESH = MESH_COUNT++;
    private static final int FRAME_MESH = MESH_COUNT++;

    public Hotbar(Player player) {
        this.player = player;

        toggled = true;
        atlas = new SpriteAtlas("./src/main/resources/images/blocks.png", "tex", new Vector2f(22, 22));

        vertices = new FloatBuffer[MESH_COUNT];
        indices = new IntBuffer[MESH_COUNT];
    }

    /* Mesh the hotbar */
    @Override
    public void mesh() {
        for (int i = 0; i < player.getHotbarSize(); i++) {
            Vector2f unit = window.getPixelUnit();

            // 80 x 80 window pixel units
            Vector2f size = new Vector2f(80.0f * unit.x, 80.0f * unit.y);

            // The hotbar frame/item is defined by two points, since it is a square
            Vector2f p1 = new Vector2f(-size.x * (player.getHotbarSize() / 2.0f - i), -1.0f + 0.05f);
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
    }

    /* Render the hotbar */
    @Override
    public void render() {
        if (!toggled) {
            return;
        }
        
        shader.uniformTexture2D(atlas, 0);

        for (int i = 0; i < MESH_COUNT; i++) {
            ibo.buffer(indices[i]);
            vbo.buffer(vertices[i]);

            vao.attribPointer(vbo, 0, 2, GL_FLOAT, 4 * Float.BYTES, 0 * Float.BYTES);
            vao.attribPointer(vbo, 1, 2, GL_FLOAT, 4 * Float.BYTES, 2 * Float.BYTES);

            vao.bind();
            ibo.bind();

            glDrawElements(GL_TRIANGLES, indices[i].remaining(), GL_UNSIGNED_INT, 0);
        }
    }

    /* Mesh a item in the hotbar */
    private void meshItem(Vector2f p1, Vector2f p2, Integer block) {
        Vector2f uvMin = BlockMesh.get(block).getFace(Direction.NORTH).uvMin;
        Vector2f uvMax = BlockMesh.get(block).getFace(Direction.NORTH).uvMax;

        float[] verticesArray = {
            p1.x, p2.y, uvMin.x, 1 - uvMin.y,
            p1.x, p1.y, uvMin.x, 1 - uvMax.y,
            p2.x, p1.y, uvMax.x, 1 - uvMax.y,
            p2.x, p2.y, uvMax.x, 1 - uvMin.y
        };

        int[] indicesArray = {
            0, 1, 3,
            1, 2, 3,
        };

        vertices[ITEM_MESH] = BufferUtils.createFloatBuffer(verticesArray.length);
        vertices[ITEM_MESH].put(verticesArray);
        vertices[ITEM_MESH].flip();

        indices[ITEM_MESH] = BufferUtils.createIntBuffer(indicesArray.length);
        indices[ITEM_MESH].put(indicesArray);
        indices[ITEM_MESH].flip();
    }

    /* Mesh the frame of a item in the hotbar */
    private void meshFrame(Vector2f p1, Vector2f p2, boolean active) {
        int uOffset = 0;
        if (toggled) {
            uOffset = 1;
        }

        Vector2f uv1 = atlas.spriteUV(1 + uOffset, 0);
        Vector2f uv2 = atlas.spriteUV(1 + uOffset, 1);
        Vector2f uv3 = atlas.spriteUV(0, 1);
        Vector2f uv4 = atlas.spriteUV(0, 0);

        float[] verticesArray = {
            p1.x, p2.y, uv1.x, uv1.y,
            p1.x, p1.y, uv2.x, uv2.y,
            p2.x, p1.y, uv3.x, uv3.y,
            p2.x, p2.y, uv4.x, uv4.y
        };

        int[] indicesArray = {
            0, 1, 3,
            1, 2, 3,
        };

        vertices[FRAME_MESH] = BufferUtils.createFloatBuffer(verticesArray.length);
        vertices[FRAME_MESH].put(verticesArray);
        vertices[FRAME_MESH].flip();

        indices[FRAME_MESH] = BufferUtils.createIntBuffer(indicesArray.length);
        indices[FRAME_MESH].put(indicesArray);
        indices[FRAME_MESH].flip();
    }
}
