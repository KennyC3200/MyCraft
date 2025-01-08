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
    * Pack the data into a single int (32-bits)
    * Pack and unpack the data, each vertex should be 0-16 (4 bits); a face is (4 * 6) / 8 = 3 bytes
    * Pass a chunkPos vec3 into the vertex shader
    * Need to use GL_UNSIGNED_BYTE instead of GL_FLOAT for the vertices, since they are 0-16
* Optimize meshing
    * Maybe the old algorithm was faster? IDK
* Chunks generating as you move around
* AABB
    * What happens if the player is between chunks? I believe it shouldn't matter
* Menu screen with imgui
* Map for ? (blocks mined)
* Sort for highscores (comparable and comparator interface)
* Block highlight