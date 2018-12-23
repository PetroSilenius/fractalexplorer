package fi.utu.fractalexplorer.renderers;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


import fi.utu.fractalexplorer.javafx.FXMandelbrotCanvasConsumer;
import fi.utu.fractalexplorer.javafx.FXMandelbrotCanvasProducer;
import fi.utu.fractalexplorer.kernels.MandelbrotFastKernel;
import javafx.scene.canvas.GraphicsContext;

//* Tämän luokan tarkoituksena on aloittaa tuottaja ja kuluttajasäikeiden toiminta.
 /* Ongelmallista lienee, että ne ovat varsinaisesta Canvaksesta erillisiä olioita. Renderer itsessään on kopio
 /* DynThreadRendereristä.
  **/
public class ThreadedWorkQueueRenderer extends RenderBase implements MandelbrotFastKernel, MandelbrotDynamicParallelRenderer {
    boolean running = false;

    public ThreadedWorkQueueRenderer(int w, int h, int maxIterations, PixelRenderer p) {
    super(w, h, maxIterations, p);
    }

    public void main() {
        running = true;
        //Luodaan 3-paikkainen BlockingQueue
        BlockingQueue<GraphicsContext> queue = new ArrayBlockingQueue<>(3);
        FXMandelbrotCanvasProducer producer = new FXMandelbrotCanvasProducer(queue);
        FXMandelbrotCanvasConsumer consumer = new FXMandelbrotCanvasConsumer(queue);
        //aloitetaan fraktaalien tuotto
        new Thread(producer).start();
        //aloitetaan fraktaalien kulutus (peräkkäinen piirto)
        new Thread(consumer).start();
        //System.out.println("Producer and Consumer has been started");
    }

    public boolean isRunning() {        //luotiin, mikäli tarvetta ilmenisi
        if (running == true) {
            return true;
        }
        return false;
    }
    public void status(boolean b){
        this.running = b;
    }
}

