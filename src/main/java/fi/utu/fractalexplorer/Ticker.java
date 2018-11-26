package fi.utu.fractalexplorer;

import fi.utu.fractalexplorer.util.Scheduled;

/**
 * Class Ticker updates the view periodically.
 * @see Scheduled
 */
class Ticker implements Scheduled {
    final MandelbrotCanvas canvas;

    public Ticker(MandelbrotCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public int tickDuration() {
        return 50;
    }

    @Override
    public void initialize() {
        System.out.println("I have " + Runtime.getRuntime().availableProcessors() + " processors/cores.");
    }

    @Override
    public void tick() {
        // we can benchmark the performance here. the results show up in the console panel
        long start = System.currentTimeMillis();
        canvas.redraw();
        long stop = System.currentTimeMillis();
        System.out.println("Rendering with " + canvas.rendererName() + " took: " + (stop - start) + " ms.");
    }
}