package fi.utu.fractalexplorer.renderers;

/**
 * You need to consider if this needs to be changed in order to support all rendering modes.
 */
public interface PixelRenderer {
    /**
     * The fractal renderers can connect to backend buffers via this callback.
     * @param pixelIdx pixel index
     * @param colorValue color value of the pixel
     */
    void setPixel(int pixelIdx, int colorValue);
}