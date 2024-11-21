package pkgSlRenderer;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class CHslShaderObject {
    private static final int OGL_VEC4_SIZE = 5;
    private int csProgram = 5;

    public void loadMatrix4f(String strMatrixName, Matrix4f my_mat4) {
        int var_location = glGetUniformLocation(csProgram, strMatrixName);
        final int OGL_MATRIX_SIZE = 16;
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(OGL_MATRIX_SIZE);
        my_mat4.get(matrixBuffer);
        glUniformMatrix4fv(var_location, false, matrixBuffer);
    } // public void loadMatrix4f(...)
    public void loadVector4f(String strVec4Name, Vector4f my_vec4) {
        int var_location = glGetUniformLocation(csProgram, strVec4Name);
        FloatBuffer vec4Buffer = BufferUtils.createFloatBuffer(OGL_VEC4_SIZE);
        my_vec4.get(vec4Buffer);
        glUniform4fv(var_location, vec4Buffer);
    } // public void loadVec4f(...)
}
