package fi.utu.fractalexplorer.javafx;

import javafx.scene.Node;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A special non-generic ArrayList derivative for JavaFX nodes. This
 * is quite useful when creating sequences of nodes, e.g. rows of buttons.
 *
 * This class is final. Modification is not necessary.
 */
public class FXNodeList extends ArrayList<Node> {
    public FXNodeList(Node... ns) {
        addAll(Arrays.asList(ns));
    }

    public FXNodeList(List<Node> list) {
        super(list);
    }

    public FXNodeList(Stream<Node> stream) {
        super(stream.collect(Collectors.toList()));
    }

    public FXNodeList cat(Node node) {
        FXNodeList tmp = new FXNodeList(this);
        tmp.add(node);
        return tmp;
    }

    public FXNodeList cat(Node... ns) {
        FXNodeList tmp = new FXNodeList(this);
        Collections.addAll(tmp, ns);
        return tmp;
    }

    public FXNodeList cat(List<Node> other) {
        FXNodeList tmp = new FXNodeList(this);
        tmp.addAll(other);
        return tmp;
    }
}
