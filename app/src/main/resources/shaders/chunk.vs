#version 330 core

layout (location = 0) in vec3 block_pos;
layout (location = 1) in vec2 uv;

out vec2 v_uv;

uniform mat4 view;
uniform mat4 projection;

uniform vec3 chunk_pos;

void main() {
    gl_Position = projection * view * vec4(chunk_pos + block_pos, 1.0f);
    v_uv = uv;
}
