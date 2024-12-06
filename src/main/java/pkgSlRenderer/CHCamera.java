package pkgSlRenderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static pkgDriver.CHSpot.*;

class CHCamera {
    private Matrix4f projectionMatrix, viewMatrix;
    public Vector3f defaultLookFrom;
    public Vector3f defaultLookAt;
    public Vector3f defaultUpVector;

    private final float screen_left = 0.0f, screen_right = (float)WIN_WIDTH,
            screen_bottom = 0.0f, screen_top = (float)WIN_HEIGHT;
    private final float zNear = 0.0f, zFar = 100.0f;  // these are NOT pixels!

    public CHCamera() {
        defaultLookFrom = new Vector3f(0.0f, 0.0f, 100.0f);
        defaultLookAt = new Vector3f(0.0f, 0.0f, -1.0f);
        defaultUpVector = new Vector3f(0.0f, 1.0f, 0.0f);

        projectionMatrix = new Matrix4f();
        projectionMatrix.identity();
        projectionMatrix.ortho(screen_left, screen_right,
                screen_bottom, screen_top, zNear, zFar);

        viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.lookAt(defaultLookFrom, defaultLookAt.add(defaultLookFrom), defaultUpVector);
    }  //  public SlCamera(...)

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}

