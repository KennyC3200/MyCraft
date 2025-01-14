#version 330 core

layout (location = 0) in vec3 block_pos;
layout (location = 1) in vec2 uv;

// Block uv coordinates from the texture atlas
out vec2 v_uv;

// For the camera
uniform mat4 view;
uniform mat4 projection;

// Chunk position
uniform vec3 chunk_pos;

// For the sprite atlas
uniform vec2 atlas_size;
uniform vec2 sprite_size;

void main() {
    gl_Position = projection * view * vec4(chunk_pos + block_pos, 1.0f);
    v_uv = uv;
}
