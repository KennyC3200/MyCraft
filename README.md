# MyCraft
A Minecraft clone made for fun in Java using OpenGL.

Controls
-----
* `WASD` to move
* `Space` to fly up
* `L-Shift` to fly down
* `t` to see the wireframe
* `Escape` to toggle the gui

Libraries Used
-----
* [LWJGL3](https://github.com/LWJGL/lwjgl3) for graphics rendering
* [JOML](https://github.com/JOML-CI/JOML) for maths
* [ImGui Java](https://github.com/SpaiR/imgui-java) for the GUI
    * Used [this repository](https://github.com/Trolobezka/mwe-imgui-java) as reference
* [FastNoiseLite](https://github.com/Auburn/FastNoiseLite) for world generation

TODO
-----
* Optimize rendering
    * Pass two GL_UNSIGNED_INT into the vertex shader to represent the face coordinates
    * Shader will receive them as a `uvec2` in the vertex shader
    * Pack the data into a single int (64-bits)
    * Need to use GL_UNSIGNED_BYTE instead of GL_FLOAT for the vertices, since they are 0-16
* Instanced rendering
    * Have only 1-2 draw calls instead of multiple thousand
    * https://learnopengl.com/Advanced-OpenGL/Instancing
* Implement a tick system to load the chunks progressively
    * 20 ticks/sec, or 1 tick/0.05 sec
    * Load the chunks at the tick rate
* Optimize meshing
    * Maybe the old algorithm was faster? IDK
* Chunks generating as you move around
* AABB
    * What happens if the player is between chunks? I believe it shouldn't matter
* Menu screen with imgui
* Map for ? (blocks mined)
* Sort for highscores (comparable and comparator interface)
* Block highlight