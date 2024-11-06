package pkgDriver;
import pkgPingPongArray.CHslPingPongArray;
import pkgSlPolygonRenderer.CHslPolygonRenderer;

import static pkgDriver.CHslSpot.*;
import pkgSlUtils.CHslWindowManager;
public class CHcsc133Driver {
    public static final int rows = 10;
    public static final int cols = 10;
    public static final int frameDelay = 1000;
    public static final int faces = 3;
    public static final float radius = .05f;
    //note: I've added a fourth overload that takes frameDelay, rows, columns, and faces as arguments to display only specified polygons
    public static void main(String[] my_args) {
        CHslPolygonRenderer my_re = new CHslPolygonRenderer();
        CHslPingPongArray my_game = new CHslPingPongArray();
        CHslWindowManager.get().initGLFWWindow(WIN_WIDTH, WIN_HEIGHT, "CSUS CSC133");
        my_re.initOpenGL(CHslWindowManager.get());
        my_game.gameOfLife(rows, cols);
    } // public static void main(String[] my_args)
} // public class csc133Driver(...)