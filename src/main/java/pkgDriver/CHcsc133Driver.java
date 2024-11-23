package pkgDriver;

import pkgSlRenderer.CHslRenderEngine;
import pkgSlUtils.CHslWindowManager;

import java.io.IOException;

import static pkgDriver.CHslSpot.*;

public class CHcsc133Driver {
    public static void main(String[] my_args) throws IOException {
        CHslRenderEngine renderEngine = new CHslRenderEngine();
        CHslWindowManager.get().initGLFWWindow(WIN_WIDTH, WIN_HEIGHT, WINDOW_TITLE);
        renderEngine.initOpenGL(CHslWindowManager.get());
        //renderEngine.initRender();
        renderEngine.renderBoard();
    } // public static void main(String[] my_args)
} // public class csc133Driver(...)