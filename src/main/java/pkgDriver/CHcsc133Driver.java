package pkgDriver;

import pkgSlRenderer.CHRenderEngine;
import pkgSlUtils.CHslWindowManager;

import java.io.IOException;

import static pkgDriver.CHSpot.*;

public class CHcsc133Driver {
    public static void main(String[] my_args) throws IOException {
        CHRenderEngine renderEngine = new CHRenderEngine();
        CHslWindowManager.get().initGLFWWindow(WIN_WIDTH, WIN_HEIGHT, WINDOW_TITLE);
        renderEngine.initOpenGL(CHslWindowManager.get());
        //renderEngine.initRender();
        renderEngine.renderBoard();
    } // public static void main(String[] my_args)
} // public class csc133Driver(...)