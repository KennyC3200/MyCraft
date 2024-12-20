package MyCraft.world;

import MyCraft.gfx.*;

import java.util.*;

import org.joml.*;

public class BlockData {

    public static final Integer BLOCK_GRASS = 0;
    public static final Integer BLOCK_DIRT = 1;
    public static final Integer BLOCK_STONE = 2;

    private static Map<Integer, Block> stringToBlock;
    private static SpriteAtlas atlas;

    private long id;
    private BlockMesh mesh;

    public BlockData(long id, BlockMesh mesh) {
        this.id = id;
        this.mesh = mesh;
    }

    /* Init the necessary components */
    public static void init() {
        stringToBlock = new HashMap<Integer, Block>();
        atlas = new SpriteAtlas("./src/main/res/images/blocks.png", "blocks", new Vector2i(16, 16));

        /* Init the different blocks */
        BlockMesh mesh = new BlockMesh();
    }

    public long getID() {
        return this.id;
    }

}
