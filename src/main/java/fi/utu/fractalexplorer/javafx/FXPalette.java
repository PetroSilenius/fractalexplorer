package fi.utu.fractalexplorer.javafx;

/**
 * Color palette for drawing e.g. fractals.
 *
 * This class is final. Modification is not necessary.
 */
public class FXPalette {
    // the size of the palette
    public final int size = 7 * 256;
    public final int scaleMax;

    private final int[] colors;
    private int idx = 0;

    public FXPalette(int scaleMax) {
        this.colors = new int[size];
        this.scaleMax = scaleMax;

        for (int c = 0; c < 255; c++) addPaletteEntry(c, 0, 0);
        for (int c = 0; c < 255; c++) addPaletteEntry(255, c, 0);
        for (int c = 0; c < 255; c++) addPaletteEntry(255 - c, 255, 0);
        for (int c = 0; c < 255; c++) addPaletteEntry(0, 255, c);
        for (int c = 0; c < 255; c++) addPaletteEntry(0, 255 - c, 255);
        for (int c = 0; c < 255; c++) addPaletteEntry(c, 0, 255);
        for (int c = 0; c < 255; c++) addPaletteEntry(255, c, 255);
    }

    private void addPaletteEntry(int r, int g, int b) {
        colors[idx++] = (255 << 24) + (r << 16) + (g << 8) + b;
    }

    /**
     * Return a color for iteration number n.
     *
     * @.pre 0 <= n < size
     * @.post color from the palette for the index
     */
    public int getColor(int n) {
        return colors[n];
    }

    /**
     * Return a scaled color for a value of n.
     *
     * @.pre 0 <= n < scaleMax
     * @.post scaled color from the palette
     */
    public int getScaledColor(int n) {
        return getColor(n * size / scaleMax);
    }
}