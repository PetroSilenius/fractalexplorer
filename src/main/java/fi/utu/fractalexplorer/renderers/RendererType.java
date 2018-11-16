package fi.utu.fractalexplorer.renderers;

import java.io.Serializable;

/**
 * Represents the different rendering modes.
 *
 * This class is final. Modification is not necessary.
 */
public enum RendererType implements Serializable {
    Slow("Scalar"),
    Vector("Vector"),
    StaticThreaded("Threaded (static sched)"),
    DynamicThreaded("Threaded (dyn. sched)"),
    ThreadedWorkQueue("Threaded + work queues");

    RendererType(String desc) {
        description = desc;
    }

    public final String description;
}