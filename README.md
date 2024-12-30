# README
A Java implementation of Minecraft.

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

TODO
-----
* AABB
    * What happens if the player is between chunks? I believe it shouldn't matter
* Menu screen with imgu
* Map for ? (blocks mined)
* Sort for highscores (comparable and comparator interface)
* Map generation with perlin noise
* Block highlight
