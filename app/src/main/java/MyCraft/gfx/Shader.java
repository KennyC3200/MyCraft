package MyCraft.gfx;

import MyCraft.gfx.Texture;

import static org.lwjgl.opengl.GL33.*;

import java.util.*;
import java.io.*;

public class Shader {

    private int handle;

    /* Init the shader 
     * @param vertexShaderPath The vertex shader path
     * @param fragmentShaderPath The fragment shader path
     * */
    public void init(String vertexShaderPath, String fragmentShaderPath) {
        handle = glCreateProgram();

        int vertexShaderHandle = compile(vertexShaderPath, GL_VERTEX_SHADER);
        int fragmentShaderHandle = compile(fragmentShaderPath, GL_FRAGMENT_SHADER);

        glAttachShader(handle, vertexShaderHandle);
        glAttachShader(handle, fragmentShaderHandle);
        glLinkProgram(handle);
        glValidateProgram(handle);

        int result = glGetProgrami(handle, GL_LINK_STATUS);
        if (result == GL_FALSE) {
            String msg = glGetProgramInfoLog(handle);
            System.out.printf("Error linking program: %s\n", msg);
        }

        glDeleteShader(vertexShaderHandle);
        glDeleteShader(fragmentShaderHandle);
    }

    /* Destroy the shader */
    public void destroy() {
        glDeleteProgram(handle);
    }

    /* Use the shader */
    public void bind() {
        glUseProgram(handle);
    }

    /* Bind a uniform texture
     * @param tex The texture to bind
     * @param unit The texture unit
     * */
    public void uniformTexture2D(Texture tex, int unit) {
        glActiveTexture(GL_TEXTURE0 + unit);
        tex.bind();
        glUniform1i(glGetUniformLocation(handle, tex.getFragmentShaderName()), unit);
    }

    /* Bind a uniform texture
     * @param tex The texture to bind
     * @param fragmentShaderName The fragment shader name
     * @param unit The unit
     * */
    public void uniformTexture2D(Texture tex, String fragmentShaderName, int unit) {
        glActiveTexture(GL_TEXTURE0 + unit);
        tex.bind();
        glUniform1i(glGetUniformLocation(handle, fragmentShaderName), unit);
    }

    /* Compile a shader program
     * @param path The path of the shader
     * @param type The type of the shader
     * */
    private int compile(String path, int type) {
        String textSrc = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                textSrc += line + '\n';
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("Shader file not found: " + path);
        } catch (IOException e) {}

        int handle = glCreateShader(type);
        glShaderSource(handle, textSrc);
        glCompileShader(handle);

        int result = glGetShaderi(handle, GL_COMPILE_STATUS);
        if (result == GL_FALSE) {
            String msg = glGetShaderInfoLog(handle);
            System.out.printf("Error compiling shader %s: %s\n", path, msg);
        }

        return handle;
    }

}
