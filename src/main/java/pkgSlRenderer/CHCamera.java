package pkgSlRenderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static pkgDriver.CHSpot.*;

public class CHCamera {
    private Vector3f lf = new Vector3f(0f, 0f, 100f);
    private Vector3f la = new Vector3f(0f, 0f, -1.0f);
    private Vector3f up = new Vector3f(0f, 1.0f, 0f);

    public Matrix4f getProjectionMatrix() {
        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.identity();
        projectionMatrix.ortho(FRUSTUM_LEFT, FRUSTUM_RIGHT, FRUSTUM_BOTTOM, FRUSTUM_TOP, Z_NEAR, Z_FAR);
        return projectionMatrix;
    }
    public Matrix4f getViewMatrix() {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.lookAt(lf, la.add(lf), up);
        return viewMatrix;
    }
}
