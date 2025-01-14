package MyCraft.gfx;

import static org.lwjgl.opengl.GL33C.*;

import java.io.*;
import java.nio.FloatBuffer;

public class Shader {

    private int handle;

    /* Init the shader
     * @param vertexShaderPath The vertex shader path
     * @param fragmentShaderPath The fragment shader path
     * */
    public Shader(String vertexShaderPath, String fragmentShaderPath) {
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

    /* Bind the uniform Matrix4f
     * @param name The name of the uniform matrix
     * @param mat4 The 4x4 float matrix as a float buffer
     * */
    public void uniformMatrix4f(String name, FloatBuffer mat4) {
        glUniformMatrix4fv(glGetUniformLocation(handle, name), false, mat4);
    }

    /* Bind the uniform vec3
     * @param name The name of the uniform vec3
     * @param vec The vec3 vector
     */
    public void uniformVec3(String name, float x, float y, float z) {
        glUniform3f(glGetUniformLocation(handle, name), x, y, z);
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
