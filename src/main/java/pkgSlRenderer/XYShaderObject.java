package pkgSlRenderer;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;

public class XYShaderObject {
    private static final int OGL_VEC4_SIZE = 4;
    private int shaderProgram;

    public void loadMatrix4f(String strMatrixName, Matrix4f my_mat4) {
        int var_location = glGetUniformLocation(shaderProgram, strMatrixName);
        final int OGL_MATRIX_SIZE = 16;
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(OGL_MATRIX_SIZE);
        my_mat4.get(matrixBuffer);
        glUniformMatrix4fv(var_location, false, matrixBuffer);
    } // public void loadMatrix4f(...)
    public void loadVector4f(String strVec4Name, Vector4f my_vec4) {
        int var_location = glGetUniformLocation(shaderProgram, strVec4Name);
        FloatBuffer vec4Buffer = BufferUtils.createFloatBuffer(OGL_VEC4_SIZE);
        my_vec4.get(vec4Buffer);
        glUniform4fv(var_location, vec4Buffer);
    } // public void loadVec4f(...)
    public void loadInt(String strIntName, int value) {
        // Get the location of the uniform variable by name
        int var_location = glGetUniformLocation(shaderProgram, strIntName);

        // Set the value of the uniform variable in the shader
        glUniform1i(var_location, value);
    }
    public void compile_shader() {
        shaderProgram = glCreateProgram();

        //int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        String vertexShaderSource = loadShaderFromFile("assets/shaders/vs_texture_1.glsl");
        int vertexShader = compileShader(GL_VERTEX_SHADER, vertexShaderSource);
        glAttachShader(shaderProgram, vertexShader);

        //int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        String fragmentShaderSource = loadShaderFromFile("assets/shaders/fs_texture_1.glsl");
        int fragmentShader = compileShader(GL_FRAGMENT_SHADER, fragmentShaderSource);
        glAttachShader(shaderProgram, fragmentShader);

        glLinkProgram(shaderProgram);
    }
    private int compileShader(int shaderType, String shaderSource) {
        int shader = glCreateShader(shaderType);
        glShaderSource(shader, shaderSource);
        glCompileShader(shader);
        return shader;
    }
    public void set_shader_program() {
        glUseProgram(shaderProgram);
    }
    private String loadShaderFromFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException("Error loading shader file: " + filePath, e);
        }
    }
    public void checkShaderCompile(int shaderID) {
        if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.out.println("Shader compile failed!");
            System.out.println(glGetShaderInfoLog(shaderID));
            System.out.println(glGetShaderInfoLog(shaderID, 1024));
        }
    }

    public void checkProgramLink(int programID) {
        if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
            System.out.println("Program linking failed!");
            System.out.println(glGetProgramInfoLog(programID));
        }
    }
}
