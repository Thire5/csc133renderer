package pkgDriver;
import pkgSlPolygonRenderer.slPolygonRenderer;
import pkgSlRenderer.slRenderEngine;
import static pkgDriver.slSpot.*;
import pkgSlUtils.slWindowManager;
public class csc133Driver {
    public static final int rows = 30;
    public static final int cols = 30;
    public static final int frameDelay = 100;
    public static void main(String[] my_args) {
        slPolygonRenderer my_re = new slPolygonRenderer();
        slWindowManager.get().initGLFWWindow(WIN_WIDTH, WIN_HEIGHT, "CSUS CSC133");
        my_re.initOpenGL(slWindowManager.get());
        my_re.render(frameDelay, rows, cols);
    } // public static void main(String[] my_args)
} // public class csc133Driver(...)