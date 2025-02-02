package MyCraft.gfx;

import static org.lwjgl.opengl.GL33C.*;

import org.joml.*;

public class SpriteAtlas extends Texture {

    /* spriteSize is the size of each sprite, spritesSize is how many sprites there are */
    private Vector2i spriteSize, spritesSize;
    private Vector2f spriteUnit, pixelUnit;

    /* Init the sprite atlas
     * @param path The path of the atlas
     * @param fragmentShaderName The uniform sampler2D name
     * @param spriteSize The size of each sprite
     * */
    public SpriteAtlas(String path, String fragmentShaderName, Vector2i spriteSize) {
        super(path, fragmentShaderName, GL_RGBA, GL_RGBA);

        this.spriteSize = spriteSize;
        this.spritesSize = new Vector2i(size.x / spriteSize.x, size.y / spriteSize.y);

        spriteUnit = new Vector2f((float) spriteSize.x / size.x, (float) spriteSize.y / size.y);
        pixelUnit = new Vector2f(1.0f / size.x, 1.0f / size.y);
    }

    /* Get the sprite uv coordinates given sprite coordinates */
    public Vector2f spriteUV(Vector2i spriteCoordinates) {
        return new Vector2f(
            spriteCoordinates.x * spriteUnit.x,
            spriteCoordinates.y * spriteUnit.y
        );
    }

    /* Get the sprite uv coordinates given sprite coordinates */
    public Vector2f spriteUV(int x, int y) {
        return new Vector2f(
            x * spriteUnit.x,
            y * spriteUnit.y
        );
    }

    public Vector2i getSpriteSize() {
        return spriteSize;
    }

    public Vector2i getSpritesSize() {
        return spritesSize;
    }

    public Vector2f getSpriteUnit() {
        return spriteUnit;
    }

    public Vector2f getPixelUnit() {
        return pixelUnit;
    }

}
