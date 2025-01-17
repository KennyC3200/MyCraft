package MyCraft.util;

import org.joml.Vector3f;
import org.joml.Vector3i;

public class MathUtils {

    public static float clamp(float x, float min, float max) {
        return Math.max(min, Math.min(max, x));
    }

    public static Vector3f intBound(Vector3f s, Vector3f ds) {
        return new Vector3f(
            (float) (ds.x > 0 ? (Math.ceil(s.x) - s.x) : (s.x - Math.floor(s.x))) / Math.abs(ds.x),
            (float) (ds.y > 0 ? (Math.ceil(s.y) - s.y) : (s.y - Math.floor(s.y))) / Math.abs(ds.y),
            (float) (ds.z > 0 ? (Math.ceil(s.z) - s.z) : (s.z - Math.floor(s.z))) / Math.abs(ds.z)
        );
    }

    public static String Vector3iToStr(Vector3i vec) {
        return String.format("%d %d %d", vec.x, vec.y, vec.z);
    }

}
