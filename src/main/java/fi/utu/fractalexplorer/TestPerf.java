package fi.utu.fractalexplorer;

import fi.utu.fractalexplorer.renderers.BenchmarkRuns;
import fi.utu.fractalexplorer.renderers.DummyPixelRenderer;
import fi.utu.fractalexplorer.renderers.RendererType;

import java.util.ArrayList;

/**
 * This class performs some rendering tests into memory buffers
 * in order to determine proper functionality of the different renderers.
 */
public class TestPerf {
    public static void main(String[] args) {
        int w = 2000, h = 2000;
        String errors = "";

        int p = Runtime.getRuntime().availableProcessors();
        System.out.println("I have " + p + " processors/cores.");

        if (RendererType.values().length != 5) throw new Error("Wrong number of rendering methods!");

        DummyPixelRenderer baseline = new DummyPixelRenderer(w, h, 8);
        baseline.benchmark(RendererType.Slow, 50, 1);

        ArrayList<BenchmarkRuns> runs = new ArrayList<>();

        for(RendererType t: RendererType.values()) {
            try {
                DummyPixelRenderer d = new DummyPixelRenderer(w, w, 8);
                BenchmarkRuns run = d.benchmark(t, 50, 8);
                for (int i=0;i<w*h;i++)
                    if (baseline.data[i] != d.data[i]) { errors += "Rendering of "+t+" differs in position ("+(i%w)+","+(i/w)+")!\n"; break; }
                runs.add(run);
                System.out.println(t + " done ["+run+"].");

            }
            catch(Exception e) {
                errors += e.getMessage()+"\n";
            }
        }

        if (runs.get(0).renderTime() < runs.get(1).renderTime()/2) errors += "The Vector version is somehow too slow!\n";
        if (runs.size()<3 || runs.get(2).renderTime() < runs.get(1).renderTime()/p) errors += "The method "+RendererType.values()[2]+" is unimplemented!\n";
        if (runs.size()<4 || runs.get(3).renderTime() < runs.get(1).renderTime()/p) errors += "The method "+RendererType.values()[3]+" is unimplemented!\n";
        if (runs.size()<5 || runs.get(4).renderTime() < runs.get(1).renderTime()/p) errors += "The method "+RendererType.values()[4]+" is unimplemented!\n";

        if (!errors.equals("")) {
            System.err.println(errors);
            System.exit(1);
        }
    }
}
