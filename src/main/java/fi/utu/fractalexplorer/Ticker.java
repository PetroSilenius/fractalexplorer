package fi.utu.fractalexplorer;

import fi.utu.fractalexplorer.util.Scheduled;

/**
 * Class Ticker updates the view periodically.
 * @see Scheduled
 */
class Ticker implements Scheduled {
    public int tickDuration() {
        return 50;
    }

    final MandelbrotCanvas canvas;

    public Ticker(MandelbrotCanvas canvas) {
        this.canvas = canvas;
    }

    // run this at program startup
    public void initialize() {
        System.out.println("I have " + Runtime.getRuntime().availableProcessors() + " processors/cores.");
    }

    public void tick() {
        // we can benchmark the performance here
        long start = System.currentTimeMillis();
        canvas.redraw();
        long stop = System.currentTimeMillis();
        System.out.println("Rendering with " + canvas.rendererName() + " took: " + (stop - start) + " ms.");
    }
}