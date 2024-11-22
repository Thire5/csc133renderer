package pkgSlRenderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static pkgDriver.CHslSpot.*;

public class CHslCamera {
    private Vector3f lf;
    private Vector3f la;
    private Vector3f up;

    public Matrix4f getProjectionMatrix() {
        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.identity();
        float screen_top = (float) WIN_HEIGHT;
        float screen_bottom = 0.0f;
        float screen_left = 0.0f;
        float screen_right = (float) WIN_WIDTH;
        projectionMatrix.ortho(screen_left, screen_right, screen_bottom, screen_top, Z_NEAR, Z_FAR);
        return projectionMatrix;
    }
    public Matrix4f getViewMatrix() {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.lookAt(lf, la.add(lf), up);
        return viewMatrix;
    }
    public void create() {
        lf = new Vector3f(0f, 0f, 100f);
        la = new Vector3f(0f, 0f, -1.0f);
        up = new Vector3f(0f, 1.0f, 0f);
    }
}
