package fi.utu.fractalexplorer.javafx;

import javafx.scene.canvas.GraphicsContext;
import java.util.concurrent.BlockingQueue;

//* Ajatuksena oli tuottaa GraphicsContext olioita jonoon käytettäväksi. Tällöin kuluttaja voisi niitä käyttämällä
 /* piirtää fraktaaleja kys. jonon datan pohjalta
  **/

public class FXMandelbrotCanvasProducer extends FXMandelbrotCanvas implements Runnable{
    protected FXPixelRenderer backendBuffer;

    BlockingQueue<GraphicsContext> queue;

    public FXMandelbrotCanvasProducer(BlockingQueue<GraphicsContext> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
            try {
                storeFractal();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    private void storeFractal() throws InterruptedException {
        renderer.drawSet(viewPort);
        queue.put(peer.getGraphicsContext2D());
    }
}
