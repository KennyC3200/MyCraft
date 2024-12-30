package MyCraft.player;

import org.joml.*;

/* AABB stands for Axis-Aligned Bounding Box
 * Used for detecting collisions
 * */
public class AABB {

    private Vector3f center;
    private Vector3f min, max;

    /* Init the AABB */
    public AABB(Vector3f center, Vector3f min, Vector3f max) {
    }

    /* Check the collision of two AABB
     * Assign each block around the player an AABB
     * Check the AABB of the player and blocks for collisions
     * Keep in mind the player is 1.8 blocks tall
     * */
    public boolean colliding(AABB aabb) {
        return false;
    }

    /* Set the center of the AABB */
    public void setCenter(float x, float y, float z) {
        center.x = x;
        center.y = y;
        center.z = z;
    }

}
