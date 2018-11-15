package fi.utu.fractalexplorer.util;

import java.io.Serializable;

/**
 * A Viewport represents the configuration for generating a view of the fractal.
 *
 * This class is final. Modification is not necessary.
 */
public class Viewport implements Serializable {
    // (x,y) location of the viewport
    public final double dx, dy;

    // zoom level and rotation angle of the viewport (affine transform) performed elsewhere
    public final double zoom, rot;

    // default view
    public Viewport() {
        this(0, 0, 1, 0);
    }

    public Viewport(double dx, double dy, double zoom, double rot) {
        this.dx = dx;
        this.dy = dy;
        this.zoom = zoom;
        this.rot = rot;
    }

    // perform zoom
    public Viewport zoom(double scale) {
        return new Viewport(dx, dy, zoom * scale, rot);
    }

    // perform rotation
    public Viewport rotate(double r) {
        return new Viewport(dx, dy, zoom, rot + r);
    }

    // perform translation (moving along the x/y axes)
    public Viewport translate(double x, double y) {
        return new Viewport(dx + x * zoom, dy + y * zoom, zoom, rot);
    }
}
