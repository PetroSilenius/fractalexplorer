package fi.utu.fractalexplorer.renderers;

import fi.utu.fractalexplorer.util.Viewport;

/**
 * You need to implement this!
 */
public interface MandelbrotDynamicParallelRenderer extends MandelbrotRenderer {
    default void drawSet(Viewport vp) { /* TODO: implement this renderer here! */ }
}