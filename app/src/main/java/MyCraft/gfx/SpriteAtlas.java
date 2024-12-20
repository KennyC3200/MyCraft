package MyCraft.gfx;

import static org.lwjgl.opengl.GL33.*;

import org.joml.*;

public class SpriteAtlas {

    private Texture texture;
    private Vector2i size;
    private Vector2i spriteSize;
    private Vector2f spriteUnit, pixelUnit;

    /* Init the sprite atlas 
     * @param path The path of the atlas
     * @param fragmentShaderName The uniform sampler2D name 
     * @param spriteSize The size of each sprite
     * */
    public SpriteAtlas(String path, String fragmentShaderName, Vector2i spriteSize) {
        this.spriteSize = spriteSize;

        texture = new Texture();
        texture.init(path, fragmentShaderName, GL_RGBA, GL_RGBA);

        size = texture.getSize();
        spriteUnit = new Vector2f(spriteSize.x / size.x, spriteSize.y / size.y);
        pixelUnit = new Vector2f(1.0f / size.x, 1.0f / size.y);
    }

    /* Get the sprite uv coordinates given sprite coordinates */
    public Vector2f spriteUV(Vector2i spriteCoordinates) {
        return new Vector2f(
            spriteCoordinates.x * spriteUnit.x,
            spriteCoordinates.y * spriteUnit.y
        );
    }

    public Vector2i getSpriteSize() {
        return spriteSize;
    }

    public Vector2f getSpriteUnit() {
        return spriteUnit;
    }

    public Vector2f getPixelUnit() {
        return pixelUnit;
    }

}
