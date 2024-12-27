package MyCraft.player;

import MyCraft.world.*;
import MyCraft.util.*;

import org.joml.Vector3f;
import org.joml.Vector3i;

public class Ray {

    public static class CastData {
        boolean hit;
        Vector3i position, out;

        public CastData(boolean hit, Vector3i position, Vector3i out) {
            this.hit = hit;
            this.position = position;
            this.out = out;
        }
    }

    public static CastData cast(World world, Vector3f position, Vector3f direction, float maxDistance) {
        Vector3i step;
        Vector3f d_length, d_delta;
        Vector3i _position, _out = new Vector3i();
        float radius;

        _position = new Vector3i(
            (int) Math.floor(position.x), 
            (int) Math.floor(position.y), 
            (int) Math.floor(position.z)
        );
        step = new Vector3i(
            direction.x < 0 ? -1 : 1,
            direction.y < 0 ? -1 : 1,
            direction.z < 0 ? -1 : 1
        );

        d_length = MathUtils.intBound(position, direction);
        d_delta = new Vector3f(
            step.x / direction.x,
            step.y / direction.y,
            step.z / direction.z
        );

        radius = maxDistance / direction.length();

        while (true) {
            if (d_length.x < d_length.y) {
                if (d_length.x < d_length.z) {
                    if (d_length.x > radius) {
                        break;
                    }

                    _position.x += step.x;
                    d_length.x += d_delta.x;
                    _out.x = -step.x;
                    _out.y = 0;
                    _out.z = 0;
                } else {
                    if (d_length.z > radius) {
                        break;
                    }

                    _position.z += step.z;
                    d_length.z += d_delta.z;
                    _out.x = 0;
                    _out.y = 0;
                    _out.z = -step.z;
                }
            } else {
                if (d_length.y < d_length.z) {
                    if (d_length.y > radius) {
                        break;
                    }

                    _position.y += step.y;
                    d_length.y += d_delta.y;
                    _out.x = 0;
                    _out.y = -step.y;
                    _out.z = 0;
                } else {
                    if (d_length.z > radius) {
                        break;
                    }

                    _position.z += step.z;
                    d_length.z += d_delta.z;
                    _out.x = 0;
                    _out.y = 0;
                    _out.z = -step.z;
                }
            }

            Block block = world.getBlock(_position);
            if (block == null) {
                continue;
            }
            if (block.getID() != Block.AIR) {
                return new CastData(true, _position, _out);
            }
        }
        return null;
    }

}
