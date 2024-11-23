package pkgSlRenderer;

import org.joml.Vector2i;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import pkgSlUtils.CHslWindowManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static pkgDriver.CHslSpot.*;

public class CHslRenderEngine {
    Random myRand = new Random();
    private CHslWindowManager my_wm = new CHslWindowManager();
    private CHslShaderObject my_shader;
    private final float opacity = 1.0f;
    private final float z = 0.0f;
    private final int VPT = 4;
    private final int FPP = 5;
    private final int positionStride = 3;
    private final int vertexStride = 3;
    private final float uMin = 0.0f;
    private final float uMax = 1.0f;
    private FloatBuffer myFB;
    private float[] my_v = new float[NUM_POLY_ROWS*NUM_POLY_COLS*FPP];
    private void fill_vertex_array() {
        for (int row = 0; row < NUM_POLY_ROWS; row++) {
            for (int col = 0; col < NUM_POLY_COLS; col++) {
                my_v[((row*col+col) * VPT)] = (POLY_OFFSET+(POLYGON_LENGTH*POLY_PADDING*col));
                my_v[((row*col+col) * VPT)+1] = (POLY_OFFSET+POLYGON_LENGTH+(POLYGON_LENGTH*POLY_PADDING*row));
                my_v[((row*col+col) * VPT)+2] = z;
                my_v[((row*col+col) * VPT)+3] = uMin;
                my_v[((row*col+col) * VPT)+4] = uMax;
                my_v[((row*col+col) * VPT)+5] = (POLY_OFFSET+(POLYGON_LENGTH*POLY_PADDING*col));
                my_v[((row*col+col) * VPT)+6] = (POLY_OFFSET+(POLYGON_LENGTH*POLY_PADDING*row));
                my_v[((row*col+col) * VPT)+7] = z;
                my_v[((row*col+col) * VPT)+8] = uMin;
                my_v[((row*col+col) * VPT)+9] = uMax;
                my_v[((row*col+col) * VPT)+10] = (POLY_OFFSET+POLYGON_LENGTH+(POLYGON_LENGTH*POLY_PADDING*col));
                my_v[((row*col+col) * VPT)+11] = (POLY_OFFSET+(POLYGON_LENGTH*POLY_PADDING*row));
                my_v[((row*col+col) * VPT)+12] = z;
                my_v[((row*col+col) * VPT)+13] = uMin;
                my_v[((row*col+col) * VPT)+14] = uMax;
                my_v[((row*col+col) * VPT)+15] = (POLY_OFFSET+POLYGON_LENGTH+(POLYGON_LENGTH*POLY_PADDING*col));
                my_v[((row*col+col) * VPT)+16] = (POLY_OFFSET+POLYGON_LENGTH+(POLYGON_LENGTH*POLY_PADDING*row));
                my_v[((row*col+col) * VPT)+17] = z;
                my_v[((row*col+col) * VPT)+18] = uMin;
                my_v[((row*col+col) * VPT)+19] = uMax;
            }
        }
        myFB = BufferUtils.createFloatBuffer(my_v.length);
        myFB.put(my_v).flip();
    }
    public void initRender() {
        fill_vertex_array();
        int vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
        int vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, myFB, GL_STATIC_DRAW);
        int loc0 = 0;
        int loc1 = 1;
        glVertexAttribPointer(loc0, positionStride, GL_FLOAT, false, vertexStride, 0);
        glVertexAttribPointer(loc1, positionStride, GL_FLOAT, false, vertexStride, 12);
        glEnableVertexAttribArray(loc0);
        my_shader = new CHslShaderObject();
        my_shader.compile_shader();
        my_shader.set_shader_program();
    }
    public void renderBoard() {
        CHslCamera camera = new CHslCamera();
        float green = 1.0f;
        Vector4f COLOR_FACTOR = new Vector4f(0.2f, 0.9f, 0.8f, 1.0f);
        Vector2i rcVec = new Vector2i(-1, -1);
        while(!my_wm.isGlfwWindowClosed()) {
            glfwPollEvents();
            glClearColor(0.0f, 0.0f, 0.3f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);
            my_shader.loadMatrix4f("uProjMatrix", camera.getProjectionMatrix());
            my_shader.loadMatrix4f("uViewMatrix", camera.getViewMatrix());
            for(int row = 0; row < NUM_POLY_ROWS; row++) {
                for(int col = 0; col < NUM_POLY_COLS; col++) {
                    my_shader.loadVector4f("COLOR_FACTOR", COLOR_FACTOR);
                    renderTile(row, col);
                }
            }
            my_wm.swapBuffers();
        }
    }
    private int getVAVIndex(int row, int col) {
        return (row * NUM_POLY_COLS + col) * VPT;
    }
    private void renderTile(int row, int col) {
        int va_offset = getVAVIndex(row, col); // vertex array offset of tile
        int[] rgVertexIndices = new int[] {va_offset, va_offset+1, va_offset+2,
                va_offset, va_offset+2, va_offset+3};
        IntBuffer VertexIndicesBuffer = BufferUtils.createIntBuffer(rgVertexIndices.length);
        VertexIndicesBuffer.put(rgVertexIndices).flip();
        int eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, VertexIndicesBuffer, GL_STATIC_DRAW);
        glDrawElements(GL_TRIANGLES, rgVertexIndices.length, GL_UNSIGNED_INT, 0);
    }
    public void initOpenGL(CHslWindowManager window) throws IOException {
        my_wm = window;
        my_wm.updateContextToThis();

        GL.createCapabilities();
        float CC_RED = 0.0f, CC_GREEN = 0.0f, CC_BLUE = 1.0f, CC_ALPHA = 1.0f;
        glClearColor(CC_RED, CC_GREEN, CC_BLUE, CC_ALPHA);
        String vsString = Files.readString(Path.of("fs_1.glsl"));
        String fsString = Files.readString(Path.of("vs_1.glsl"));
        int vso = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vso, vsString);
        glCompileShader(vso);
        int fso = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fso, fsString);
        glCompileShader(fso);
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
    }

    private String file_read(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            StringBuilder sb = new StringBuilder();
            String ls = System.lineSeparator();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(ls);
            }
            return sb.toString();
        }
    }
}
