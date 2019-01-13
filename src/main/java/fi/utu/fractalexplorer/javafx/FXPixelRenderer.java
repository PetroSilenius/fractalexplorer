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
    protected final int width, height;
    private final WritableImage buffer;
    private final int[] data;
    private final FXPalette palette;

    /**
     * Initialize the JavaFX image renderer.
     *
     * @param width Image width.
     * @param height Image height.
     * @param maxIterations Maximum number of iterations (used to calculate the color values).
     * @param vectorSize Vector size (used to add some padding to avoid index out of bounds exceptions).
     */
    public FXPixelRenderer(int width, int height, int maxIterations, int vectorSize) {
        this.width = width;
        this.height = height;
        buffer = new WritableImage(width, height);
        data = new int[width * height + vectorSize];
        this.palette = new FXPalette(maxIterations);
    }

    /**
     * The fractal renderers connect to JavaFX image buffers via this callback.
     */
    @Override
    public void setPixel(int pixelIdx, int colorValue) {
        data[pixelIdx] = palette.getScaledColor(colorValue);
    }

    protected void prepareDraw(WritableImage buffer) {
        buffer.getPixelWriter().setPixels(
                0, 0,
                width, height,
                PixelFormat.getIntArgbPreInstance(), data, 0, width);
    }

    public void draw(GraphicsContext c) {
        prepareDraw(buffer);
        c.drawImage(buffer, 0.0, 0.0);
    }
}
