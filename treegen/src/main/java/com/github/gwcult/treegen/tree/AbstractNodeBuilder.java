package com.github.gwcult.treegen.tree;

public abstract class AbstractNodeBuilder<V, N extends Node> {
    protected Node parent = null;
    protected String name = "";

    public AbstractNodeBuilder<V, N> asChild(Node parent, String name) {
        this.parent = parent;
        this.name = name;
        return this;
    }

    public NodeList.Builder<V, N> asList() {
        return new NodeList.Builder<>(this);
    }

    public abstract N build(V value);
}
