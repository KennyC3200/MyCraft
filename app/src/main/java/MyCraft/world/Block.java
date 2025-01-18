package MyCraft.world;

/* Use the BlockData class to get the BlockMesh with the id
 * Add the mesh from the BlockMesh into the vertex buffer array for instance rendering
 * */
public class Block {

    public static final Integer NONE = -1;
    public static final Integer AIR = 0;
    public static final Integer FIRST = 1;
    public static final Integer GRASS = 1;
    public static final Integer DIRT = 2;
    public static final Integer STONE = 3;
    public static final Integer LAST = 3;

    /* Bit fields (64-bits)
     * 16 bits block id
     * */
    private long data;

    /* Create a new block
     * @param id The id of the block
     * */
    public Block(int id) {
        data = 0;
        setID(id);
    }

    /* Init the necessary components */
    public static void init() {
        BlockMesh.init();
    }

    /* Set the current id 
     * The id should be a 16 bit block id
     * */
    public void setID(int id) {
        data = (data & 0xffffffffffff0000L) + id;
    }

    public int getID() {
        return (int) (data & 0xffff);
    }

}
