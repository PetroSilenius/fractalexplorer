package fi.utu.fractalexplorer.javafx;

import javafx.scene.canvas.GraphicsContext;
import java.util.concurrent.BlockingQueue;

//* Tässä tarkoituksena oli suorittaa piirto ottamalla jonosta GraphicsContext-olioita. Ajattelemattomuuden seurauksena
 /* päivitys jäi kuitenkin irti redraw() metodista ja tämän seurauksena kenties myös käyttöliittymäsäikeestä.
  **/

public class FXMandelbrotCanvasConsumer extends FXMandelbrotCanvas implements Runnable{
    BlockingQueue<GraphicsContext> queue;

    public FXMandelbrotCanvasConsumer(BlockingQueue<GraphicsContext> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try{
            backendBuffer.draw(queue.take());
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
