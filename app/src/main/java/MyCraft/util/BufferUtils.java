package MyCraft.util;

import java.util.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtils {

    /* Create a FloatBuffer from ArrayList<Float>
     * NOTE: This method already flips the buffer
     * */
    public static FloatBuffer listToFloatBuffer(ArrayList<Float> list) {
        FloatBuffer buffer;

        float[] array = new float[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }

        buffer = org.lwjgl.BufferUtils.createFloatBuffer(array.length);
        buffer.put(array);
        buffer.flip();

        return buffer;
    }

    /* Create a IntBuffer from ArrayList<Integer>
     * NOTE: This method already flips the buffer
     * */
    public static IntBuffer listToIntBuffer(ArrayList<Integer> list) {
        IntBuffer buffer;

        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }

        buffer = org.lwjgl.BufferUtils.createIntBuffer(array.length);
        buffer.put(array);
        buffer.flip();

        return buffer;
    }

}
