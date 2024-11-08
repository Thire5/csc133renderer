package pkgDriver;
import pkgSlPolygonRenderer.CHslPolygonRenderer;

import static pkgDriver.CHslSpot.*;
import pkgSlUtils.CHslWindowManager;
public class CHcsc133Driver {
    public static final int rows = 100;
    public static final int cols = 100;
    public static void main(String[] my_args) {
        CHslPolygonRenderer my_re = new CHslPolygonRenderer();
        CHslWindowManager.get().initGLFWWindow(WIN_WIDTH, WIN_HEIGHT, "CSUS CSC133");
        my_re.initOpenGL(CHslWindowManager.get());
        my_re.renderGameOfLife(rows, cols);
    } // public static void main(String[] my_args)
} // public class csc133Driver(...)