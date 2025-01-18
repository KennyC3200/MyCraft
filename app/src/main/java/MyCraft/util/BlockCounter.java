package MyCraft.util;

import java.util.*;

public class BlockCounter implements Comparable<Object>, Comparator<BlockCounter> {

    private int cnt = 0;

    /* Increment the count */
    public void increment() {
        cnt++;
    }

    @Override
    public int compare(BlockCounter b1, BlockCounter b2) {
        return (b2.cnt - b1.cnt);
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof BlockCounter) {
            BlockCounter b = (BlockCounter) o;
            return (b.cnt - cnt);
        }
        return 0;
    }

    @Override
    public String toString() {
        return ("" + cnt);
    }

}
