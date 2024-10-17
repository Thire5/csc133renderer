package pkgDriver;
import pkgSlPolygonRenderer.CHslPolygonRenderer;

import static pkgDriver.CHslSpot.*;
import pkgSlUtils.CHslWindowManager;
public class CHcsc133Driver {
    public static final int rows = 30;
    public static final int cols = 30;
    public static final int frameDelay = 1000;
    public static final int faces = 3;
    public static final float radius = .03f;
    //note: I've added a fourth overload that takes frameDelay, rows, columns, and faces as arguments to display only specified polygons
    public static void main(String[] my_args) {
        CHslPolygonRenderer my_re = new CHslPolygonRenderer();
        CHslWindowManager.get().initGLFWWindow(WIN_WIDTH, WIN_HEIGHT, "CSUS CSC133");
        my_re.initOpenGL(CHslWindowManager.get());
        my_re.render(frameDelay, radius);
    } // public static void main(String[] my_args)
} // public class csc133Driver(...)