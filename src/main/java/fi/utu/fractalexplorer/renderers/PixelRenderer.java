package fi.utu.fractalexplorer.renderers;

/**
 * You need to consider if this needs to be changed in order to support all rendering modes.
 */
public interface PixelRenderer {
    void setPixel(int idx, int i);
}