package fi.utu.fractalexplorer.renderers;

import fi.utu.fractalexplorer.util.Viewport;

/**
 * You need to consider if this needs to be changed in order to support all rendering modes.
 */
public interface MandelbrotSequentialRenderer extends MandelbrotRenderer {
    default void drawSet(Viewport vp) {
        drawTile(0, 0, renderWidth(), renderHeight(), vp);
    }
}
