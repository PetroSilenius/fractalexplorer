package fi.utu.fractalexplorer.kernels;

/**
 * Interface MandelbrotFastKernel draws the mandelbrot set using SSE vector instructions.
 * Documented in MandelbrotKernel.
 *
 * This class is final. Modification is not necessary.
 */
public interface MandelbrotFastKernel extends MandelbrotSlowKernel {
    class VectorizedMandelbrot {
        private final int vectorSize() {
            return 4;
        }

        private final double zx[] = new double[vectorSize()], zy[] = new double[vectorSize()], xSqr[] = new double[vectorSize()], ySqr[] = new double[vectorSize()];

        private final int ret[] = new int[vectorSize()];
        private final double c_res[] = new double[vectorSize()];
        private final double c_ims[] = new double[vectorSize()];
        private final double c_res_preaff[] = new double[vectorSize()];
        private final double c_ims_preaff[] = new double[vectorSize()];

        public final int[] mandelbrot(double x, double y, double inc, double rot, int maxIterations) {

            for (int i = 0; i < vectorSize(); i++)
                c_res_preaff[i] = x + i * inc;
            for (int i = 0; i < vectorSize(); i++)
                c_ims_preaff[i] = y;


            final double s = Math.sin(rot);
            final double c = Math.cos(rot);

            for (int i = 0; i < vectorSize(); i++)
                c_res[i] = c_res_preaff[i] * c - c_ims_preaff[i] * s;
            for (int i = 0; i < vectorSize(); i++)
                c_ims[i] = c_res_preaff[i] * s + c_ims_preaff[i] * c;

            int iter = 0;
            for (int i = 0; i < vectorSize(); i++) ret[i] = -1;

            for (int i = 0; i < vectorSize(); i++) zx[i] = 0;
            for (int i = 0; i < vectorSize(); i++) zy[i] = 0;
            for (int i = 0; i < vectorSize(); i++) xSqr[i] = 0;
            for (int i = 0; i < vectorSize(); i++) ySqr[i] = 0;

            int done = 0;
            while (iter < maxIterations) {
                for (int i = 0; i < vectorSize(); i++)
                    if (xSqr[i] + ySqr[i] >= 4 && ret[i] == -1) {
                        ret[i] = iter;
                        done++;
                    }
                if (done == vectorSize()) break;

                for (int i = 0; i < vectorSize(); i++)
                    zy[i] = 2 * zx[i] * zy[i] + c_ims[i];
                for (int i = 0; i < vectorSize(); i++)
                    zx[i] = xSqr[i] - ySqr[i] + c_res[i];
                for (int i = 0; i < vectorSize(); i++)
                    xSqr[i] = zx[i] * zx[i];
                for (int i = 0; i < vectorSize(); i++)
                    ySqr[i] = zy[i] * zy[i];

                iter++;
            }

            for (int i = 0; i < vectorSize(); i++) if (ret[i] == -1 || ret[i] >= maxIterations) ret[i] = 0;

            return ret;
        }
    }

    @Override
    default int vectorSize() {
        return 4;
    }

    VectorizedMandelbrot vectorStorage();

    // perform actual vector processing
    @Override
    default int[] mandelbrot(double x, double y, double inc, double rot) {
        return vectorStorage().mandelbrot(x, y, inc, rot, getMaxIterations());
    }
}