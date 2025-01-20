# MyCraft
A Minecraft clone made for fun in Java using OpenGL.

Controls
-----
* `WASD` to move
* `Space` to fly up
* `L-Shift` to fly down
* `t` to see the wireframe
* `Escape` to toggle/untoggle the cursor

Libraries Used
-----
* [LWJGL3](https://github.com/LWJGL/lwjgl3) for graphics rendering
* [JOML](https://github.com/JOML-CI/JOML) for maths
* [ImGui Java](https://github.com/SpaiR/imgui-java) for the GUI
    * Used [this repository](https://github.com/Trolobezka/mwe-imgui-java) as reference
* [FastNoiseLite](https://github.com/Auburn/FastNoiseLite) for world generation

TODO
-----
* Optimize loading new chunks
    * Optimize the loop for the blocks or whatever
    * For loading new chunks, you know that the rest of the blocks above the noiseYThreshold will be air so no need for perlin
* Implement a tick system to load the chunks progressively
    * 20 ticks/sec, or 1 tick/0.05 sec
    * Load the chunks at the tick rate
* AABB
    * What happens if the player is between chunks? I believe it shouldn't matter
* Block highlight
* Instanced rendering
    * Have only 1-2 draw calls instead of multiple thousand
    * [source](https://learnopengl.com/Advanced-OpenGL/Instancing)

BUG
-----
* There are some magical areas where the chunks are not being meshed nor are they being properly chosen for the raycast