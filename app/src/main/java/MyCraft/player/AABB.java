package MyCraft.player;

import org.joml.*;

public class AABB {

    private Vector3f min, max;

    /* Init the AABB */
    public AABB(Vector3f min, Vector3f max) {
        this.min = min;
        this.max = max;
    }

    /* Check the collision of two AABB */
    public boolean colliding(AABB aabb) {
        Vector3f other_min = aabb.getMin();
        Vector3f other_max = aabb.getMax();
        return (
            min.x < other_max.x && min.y < other_max.y && min.z < other_max.z &&
            other_min.x < max.x && other_min.y < max.y && other_min.z < max.z
        );
    }

    public Vector3f getMin() {
        return min;
    }

    public void setMin(float x, float y, float z) {
        min.x = x;
        min.y = y;
        min.z = z;
    }

    public Vector3f getMax() {
        return max;
    }

    public void setMax(float x, float y, float z) {
        max.x = x;
        max.y = y;
        max.z = z;
    }

    @Override
    public String toString() {
        return String.format(
            "AABB MIN: %.1f, %.1f, %.1f AABB MAX: %.1f, %.1f, %.1f",
            min.x, min.y, min.z, max.x, max.y, max.z
        );
    }

}
