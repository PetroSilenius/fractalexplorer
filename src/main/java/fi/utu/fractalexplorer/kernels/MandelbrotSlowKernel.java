package fi.utu.fractalexplorer.kernels;

/**
 * Interface MandelbrotSlowKernel draws the mandelbrot set using x86 scalar registers.
 * Documented in MandelbrotKernel.
 *
 * This class is final. Modification is not necessary.
 */
public interface MandelbrotSlowKernel extends MandelbrotKernel {
    @Override
    default int mandelbrot(double c_re, double c_im) {
        double zx = 0, zy = 0, xSqr = 0, ySqr = 0;
        int iter = 0;

        while (xSqr + ySqr < 4 && iter < getMaxIterations()) {
            zy = 2 * zx * zy + c_im;
            zx = xSqr - ySqr + c_re;
            xSqr = zx * zx;
            ySqr = zy * zy;
            iter++;
        }

        return iter < getMaxIterations() ? iter : 0;
    }

    @Override
    default int vectorSize() {
        return 1;
    }

    // this just emulates vector processing with a loop
    @Override
    default int[] mandelbrot(double x, double y, double inc, double rot) {
        int[] ret = new int[vectorSize()];

        final double s = Math.sin(rot);
        final double c = Math.cos(rot);


        for (int i = 0; i < vectorSize(); i++) {
            final double c_re = x + i;
            final double c_im = y;
            ret[i] = mandelbrot(c_re * c - c_im * s, c_re * s + c_im * c);
        }
        return ret;
    }
}