package MyCraft.world;

import java.util.*;

public class BlockData {

    private static Map<String, BlockData> blocksMap;

    private String id;
    private BlockMesh mesh;

    public BlockData(String id, BlockMesh mesh) {
        this.id = id;
        this.mesh = mesh;
    }

    public static void init() {
    }

}
