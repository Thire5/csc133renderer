package pkgDriver;

import pkgSlRenderer.CHRenderEngine;
import pkgSlUtils.CHWindowManager;

import java.io.IOException;

import static pkgDriver.CHSpot.*;

public class CHcsc133Driver {
    public static void main(String[] my_args) throws IOException {
        CHRenderEngine renderEngine = new CHRenderEngine();
        CHWindowManager.get().initGLFWWindow(WIN_WIDTH, WIN_HEIGHT, WINDOW_TITLE);
        renderEngine.initOpenGL(CHWindowManager.get());
        renderEngine.renderBoard();
    } // public static void main(String[] my_args)
} // public class csc133Driver(...)