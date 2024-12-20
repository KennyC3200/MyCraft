package MyCraft.world;

import MyCraft.util.*;
import MyCraft.gfx.*;

import java.util.*;

import org.joml.*;

public class BlockData {

    public static final Integer BLOCK_GRASS = 0;
    public static final Integer BLOCK_DIRT = 1;
    public static final Integer BLOCK_STONE = 2;

    private static HashMap<Integer, BlockData> intToBlockData;
    private static SpriteAtlas atlas;

    private long id;
    private BlockMesh mesh;

    public BlockData(long id, BlockMesh mesh) {
        this.id = id;
        this.mesh = mesh;
    }

    /* Init the necessary components */
    public static void init() {
        intToBlockData = new HashMap<Integer, BlockData>();
        atlas = new SpriteAtlas("./src/main/res/images/blocks.png", "blocks", new Vector2f(16, 16));

        // Init the different blocks
        BlockMesh blockGrassMesh = new BlockMesh();
        blockGrassMesh.addFace(Direction.NORTH, atlas.spriteUV(new Vector2i(1, 0)), atlas.spriteUV(new Vector2i(2, 1)));
        blockGrassMesh.addFace(Direction.SOUTH, atlas.spriteUV(new Vector2i(1, 0)), atlas.spriteUV(new Vector2i(2, 1)));
        blockGrassMesh.addFace(Direction.EAST, atlas.spriteUV(new Vector2i(1, 0)), atlas.spriteUV(new Vector2i(2, 1)));
        blockGrassMesh.addFace(Direction.WEST, atlas.spriteUV(new Vector2i(1, 0)), atlas.spriteUV(new Vector2i(2, 1)));
        blockGrassMesh.addFace(Direction.UP, atlas.spriteUV(new Vector2i(0, 0)), atlas.spriteUV(new Vector2i(1, 1)));
        blockGrassMesh.addFace(Direction.DOWN, atlas.spriteUV(new Vector2i(2, 0)), atlas.spriteUV(new Vector2i(3, 1)));
        intToBlockData.put(BLOCK_GRASS, new BlockData(BLOCK_GRASS, blockGrassMesh));

        BlockMesh blockDirtMesh = new BlockMesh();
        blockDirtMesh.addFace(Direction.NORTH, atlas.spriteUV(new Vector2i(2, 0)), atlas.spriteUV(new Vector2i(3, 1)));
        blockDirtMesh.addFace(Direction.SOUTH, atlas.spriteUV(new Vector2i(2, 0)), atlas.spriteUV(new Vector2i(3, 1)));
        blockDirtMesh.addFace(Direction.EAST,  atlas.spriteUV(new Vector2i(2, 0)), atlas.spriteUV(new Vector2i(3, 1)));
        blockDirtMesh.addFace(Direction.WEST, atlas.spriteUV(new Vector2i(2, 0)), atlas.spriteUV(new Vector2i(3, 1)));
        blockDirtMesh.addFace(Direction.UP, atlas.spriteUV(new Vector2i(2, 0)), atlas.spriteUV(new Vector2i(3, 1)));
        blockDirtMesh.addFace(Direction.DOWN, atlas.spriteUV(new Vector2i(2, 0)), atlas.spriteUV(new Vector2i(3, 1)));
        intToBlockData.put(BLOCK_DIRT, new BlockData(BLOCK_DIRT, blockDirtMesh));

        BlockMesh blockStoneMesh = new BlockMesh();
        blockStoneMesh.addFace(Direction.NORTH, atlas.spriteUV(new Vector2i(3, 0)), atlas.spriteUV(new Vector2i(4, 1)));
        blockStoneMesh.addFace(Direction.SOUTH, atlas.spriteUV(new Vector2i(3, 0)), atlas.spriteUV(new Vector2i(4, 1)));
        blockStoneMesh.addFace(Direction.EAST, atlas.spriteUV(new Vector2i(3, 0)), atlas.spriteUV(new Vector2i(4, 1)));
        blockStoneMesh.addFace(Direction.WEST, atlas.spriteUV(new Vector2i(3, 0)), atlas.spriteUV(new Vector2i(4, 1)));
        blockStoneMesh.addFace(Direction.UP, atlas.spriteUV(new Vector2i(3, 0)), atlas.spriteUV(new Vector2i(4, 1)));
        blockStoneMesh.addFace(Direction.DOWN, atlas.spriteUV(new Vector2i(3, 0)), atlas.spriteUV(new Vector2i(4, 1)));
        intToBlockData.put(BLOCK_STONE, new BlockData(BLOCK_STONE, blockStoneMesh));
    }

    /* Get the sprite atlas */
    public static SpriteAtlas getAtlas() {
        return atlas;
    }

    /* Get the block data given a block */
    public static BlockData get(Integer block) {
        return intToBlockData.get(block);
    }

    /* Get the block mesh */
    public BlockMesh getMesh() {
        return mesh;
    }

}
