#version 330 core

layout (location = 0) in uint data;

// Block uv coordinates from the texture atlas
out vec2 v_uv;

// For the camera
uniform mat4 view;
uniform mat4 projection;

// Chunk position
uniform vec3 chunk_pos;

// For the sprite atlas
uniform ivec2 sprites_size;

void main() {
    // Unpack the face position
    ivec3 block_pos = ivec3(
        data       & 31u,
        data >> 5  & 31u,
        data >> 10 & 31u
    );

    gl_Position = projection * view * vec4(chunk_pos + block_pos, 1.0f);

    // Unpack the sprite texture coordinate and convert it into UV coordinates
    vec2 uv = vec2(
        (data >> 15u & 31u) / 16.0f,
        (data >> 20u & 31u) / 16.0f
    );

    v_uv = uv;
}
