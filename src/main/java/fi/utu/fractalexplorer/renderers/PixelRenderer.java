package fi.utu.fractalexplorer.renderers;

/**
 * You need to consider if this needs to be changed in order to support all rendering modes.
 */
public interface PixelRenderer {
    /**
     * The fractal renderers can connect to backend buffers via this callback.
     * @param idx pixel index
     * @param i iteration count for the pixel
     */
    void setPixel(int idx, int i);
}