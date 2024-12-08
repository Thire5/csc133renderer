package pkgSlRenderer;

import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import pkgDriver.CHSpot;
import pkgMinesweeper.CHMSBoard;
import pkgMouseReader.XYMouseListener;
import pkgSlUtils.CHWindowManager;

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
import static pkgDriver.CHSpot.*;

public class CHRenderEngine {
    Random myRand = new Random();
    private CHWindowManager my_wm = new CHWindowManager();
    private XYShaderObject my_shader;
    private final float opacity = 1.0f;
    private final float z = 0.0f;
    private final int VPT = 4;
    private final int FPP = 5;
    private final int positionStride = 3;
    private final int vertexStride = 5;
    private final float uMin = 0.0f;
    private final float uMax = 1.0f;
    private FloatBuffer myFB;
    private float[] my_v = new float[NUM_POLY_ROWS*NUM_POLY_COLS*FPP*VPT];

    private void fillVertexArray() {
        int index = 0;
        for (int row = 0; row < NUM_POLY_ROWS; row++) {
            for (int col = 0; col < NUM_POLY_COLS; col++) {
                float x = POLY_OFFSET + ((POLYGON_LENGTH + POLY_PADDING) * col);
                float y = POLY_OFFSET + ((POLYGON_LENGTH + POLY_PADDING) * row);

                my_v[index++] = x;
                my_v[index++] = y;
                my_v[index++] = z;
                my_v[index++] = uMin;
                my_v[index++] = uMin;

                my_v[index++] = x;
                my_v[index++] = y + POLYGON_LENGTH;
                my_v[index++] = z;
                my_v[index++] = uMin;
                my_v[index++] = uMax;

                my_v[index++] = x + POLYGON_LENGTH;
                my_v[index++] = y + POLYGON_LENGTH;
                my_v[index++] = z;
                my_v[index++] = uMax;
                my_v[index++] = uMax;

                my_v[index++] = x + POLYGON_LENGTH;
                my_v[index++] = y;
                my_v[index++] = z;
                my_v[index++] = uMax;
                my_v[index++] = uMin;
            }
        }
        myFB = BufferUtils.createFloatBuffer(my_v.length);
        myFB.put(my_v).flip();
    }
    public void initRender() {
        fillVertexArray();
        int vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        int vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, myFB, GL_STATIC_DRAW);
        int loc0 = 0, loc1 = 1;
        glVertexAttribPointer(loc0, positionStride, GL_FLOAT, false, vertexStride * Float.BYTES, 0);
        glVertexAttribPointer(loc1, 2, GL_FLOAT, false, vertexStride * Float.BYTES, positionStride * Float.BYTES);
        glEnableVertexAttribArray(0);

        my_shader = new XYShaderObject();
        my_shader.compile_shader();
        my_shader.set_shader_program();
    }
    public void renderBoard() {
        CHCamera camera = new CHCamera();
        CHMSBoard board = new CHMSBoard();
        XYTextureObject[] rgTextureObject = new XYTextureObject[3];
        rgTextureObject[0] = new XYTextureObject("assets/images/base.PNG");
        rgTextureObject[1] = new XYTextureObject("assets/images/MineBomb_2.PNG");
        rgTextureObject[2] = new XYTextureObject("assets/images/ShiningDiamond_2.PNG");
        board.fill();
        Vector4f COLOR_FACTOR = new Vector4f(1.0f, 0.0f, 0.0f, opacity);
        initRender();
        glClearColor(0.0f, 0.0f, 0.0f, opacity);
        my_shader.loadMatrix4f("uProjMatrix", camera.getProjectionMatrix());
        my_shader.loadMatrix4f("uViewMatrix", camera.getViewMatrix());
        my_shader.loadVector4f("COLOR_FACTOR", COLOR_FACTOR);
        board.printBoard();
        board.printCellScores();

        while (!my_wm.isGlfwWindowClosed()) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            int NE = 0, MB = 1, GD = 2;
            rgTextureObject[NE].loadImageToTexture();
            for (int row = 0; row < NUM_POLY_ROWS; row++) {
                for (int col = 0; col < NUM_POLY_COLS; col++) {
                    CHSpot.CELL_STATUS status = board.getCellStatus(row, col);
                    if(status == CELL_STATUS.NOT_EXPOSED) {
                        COLOR_FACTOR = new Vector4f(0.0f, 0.0f, 1.0f, opacity);
                        my_shader.loadVector4f("COLOR_FACTOR", COLOR_FACTOR);
                        renderTile(row, col);
                    }
                }
            }
            rgTextureObject[GD].loadImageToTexture();
            for (int row = 0; row < NUM_POLY_ROWS; row++) {
                for (int col = 0; col < NUM_POLY_COLS; col++) {
                    CHSpot.CELL_STATUS status = board.getCellStatus(row, col);
                    CHSpot.CELL_TYPE type = board.getCellType(row, col);
                    if(status == CELL_STATUS.EXPOSED && type == CELL_TYPE.GOLD) {
                        COLOR_FACTOR = new Vector4f(0.0f, 1.0f, 0.0f, opacity);
                        my_shader.loadVector4f("COLOR_FACTOR", COLOR_FACTOR);
                        renderTile(row, col);
                    }
                }
            }
            rgTextureObject[MB].loadImageToTexture();
            for (int row = 0; row < NUM_POLY_ROWS; row++) {
                for (int col = 0; col < NUM_POLY_COLS; col++) {
                    CHSpot.CELL_STATUS status = board.getCellStatus(row, col);
                    CHSpot.CELL_TYPE type = board.getCellType(row, col);
                    if(status == CELL_STATUS.EXPOSED && type == CELL_TYPE.MINE) {
                        COLOR_FACTOR = new Vector4f(1.0f, 0.0f, 0.0f, opacity);
                        my_shader.loadVector4f("COLOR_FACTOR", COLOR_FACTOR);
                        renderTile(row, col);
                    }
                }
            }
            if(XYMouseListener.mouseButtonDown(0)) {
                float mouseX = (int) XYMouseListener.getX();
                float mouseY = (int) XYMouseListener.getY();
                int row = (int) (WIN_HEIGHT - POLY_OFFSET - mouseY)/(POLYGON_LENGTH + POLY_PADDING);
                int col = (int) (mouseX - POLY_OFFSET)/(POLYGON_LENGTH + POLY_PADDING);
                if(row >= 0 && col >= 0) {
                    board.gameStep(row, col);
                }
                XYMouseListener.mouseButtonDownReset(0);
                if(board.isGameActive()) {
                    board.printBoard();
                    board.printCellScores();
                    System.out.println("mouse click at " + row + ", " + col);
                    System.out.println("score: " + board.getCurrentScore());
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
    public void initOpenGL(CHWindowManager window) throws IOException {
        my_wm = window;
        my_wm.updateContextToThis();

        GL.createCapabilities();
        float CC_RED = 0.0f, CC_GREEN = 0.0f, CC_BLUE = 0.0f, CC_ALPHA = 1.0f;
        glClearColor(CC_RED, CC_GREEN, CC_BLUE, CC_ALPHA);
        createShaderProgram("assets/shaders/vs_texture_1.glsl", "assets/shaders/fs_texture_1.glsl");
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
    }

    private int compileShader(String filePath, int shaderType) throws IOException {
        String shaderSource = new String(Files.readAllBytes(Path.of(filePath)));
        int shader = glCreateShader(shaderType);
        glShaderSource(shader, shaderSource);
        glCompileShader(shader);
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException("Shader compilation failed: " + glGetShaderInfoLog(shader));
        }
        return shader;
    }
    private void createShaderProgram(String vertexShaderPath, String fragmentShaderPath) throws IOException {
        int vertexShader = compileShader(vertexShaderPath, GL_VERTEX_SHADER);
        int fragmentShader = compileShader(fragmentShaderPath, GL_FRAGMENT_SHADER);

        int program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException("Shader program linking failed: " + glGetProgramInfoLog(program));
        }
    }
}
