package fi.utu.fractalexplorer.javafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

import java.util.concurrent.LinkedBlockingQueue;

public class AsyncFXPixelRenderer extends FXPixelRenderer {
    private final LinkedBlockingQueue<WritableImage> imageQueue;

    public AsyncFXPixelRenderer(int width, int height, int maxIterations, int vectorSize, LinkedBlockingQueue<WritableImage> imageQueue) {
        super(width, height, maxIterations, vectorSize);
        this.imageQueue = imageQueue;
    }

    @Override
    public void draw(GraphicsContext c) {
        WritableImage buffer = new WritableImage(width, height);
        prepareDraw(buffer);
        try {
            imageQueue.put(buffer);
        } catch (InterruptedException e) {

        }
    }
}
