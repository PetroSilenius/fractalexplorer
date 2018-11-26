package fi.utu.fractalexplorer.javafx;

import fi.utu.fractalexplorer.renderers.PixelRenderer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;

/**
 * You need to consider if this needs to be changed in order to support all rendering modes.
 */
public class FXPixelRenderer extends Canvas implements PixelRenderer {
    private final int w, h;
    private final WritableImage buffer;
    private final int[] data;
    private final FXPalette palette;

    public FXPixelRenderer(int w, int h, int maxIterations, int vectorSize) {
        this.w = w;
        this.h = h;
        buffer = new WritableImage(w, h);
        data = new int[w * h + vectorSize];
        this.palette = new FXPalette(maxIterations);
    }

    /**
     * The fractal renderers connect to JavaFX image buffers via this callback.
     */
    @Override
    public void setPixel(int idx, int i) {
        data[idx] = palette.getScaledColor(i);
    }

    public void draw(GraphicsContext c) {
        buffer.getPixelWriter().setPixels(0, 0, w, h, PixelFormat.getIntArgbPreInstance(), data, 0, w);
        c.drawImage(buffer, 0.0, 0.0);
    }
}
