package fi.utu.fractalexplorer;

import fi.utu.fractalexplorer.renderers.MandelbrotRenderer;
import fi.utu.fractalexplorer.renderers.PixelRenderer;
import fi.utu.fractalexplorer.renderers.RendererFactory;
import fi.utu.fractalexplorer.renderers.RendererType;
import fi.utu.fractalexplorer.util.Viewport;

/**
 * Basic platform agnostic functionality for drawing a 'canvas'. Keeps track
 * of viewport parameters and renderers and provides a generic way to 'redraw()'
 * in a platform specific manner.
 *
 * You need to consider if this needs to be changed in order to support all rendering modes.
 */
public abstract class MandelbrotCanvas {
    protected int maxIterations;
    protected MandelbrotRenderer renderer;
    protected RendererType rendererType;
    protected Viewport viewPort = new Viewport();

    abstract protected PixelRenderer generateBackend(int w, int h, int vectorSize);

    protected void refreshRenderer() {
        if (renderer != null)
            buildRenderer(renderer.renderWidth(), renderer.renderHeight());
    }

    protected void buildRenderer(int w, int h) {
        try {
            PixelRenderer backendRenderer = generateBackend(w, h, 8);
            renderer = RendererFactory.createRenderer(rendererType, w, h, maxIterations, backendRenderer);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
    public void setRendererType(RendererType r) {
        rendererType = r;
        refreshRenderer();
    }

    public String rendererName() {
        return renderer.getClass().getSimpleName();
    }

    public void addDetail() {
        maxIterations *= 2;
        refreshRenderer();
    }

    public void removeDetail() {
        if (maxIterations > 3) maxIterations /= 2;
        else maxIterations = 3;
        refreshRenderer();
    }

    /**
     * Platform specific redraw routine. Implement this for your platform.
     * Should redraw the whole fractal.
     */
    public abstract void redraw();
}
